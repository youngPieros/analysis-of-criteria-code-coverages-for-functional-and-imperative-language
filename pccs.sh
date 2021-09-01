project=""
run_java_haskell=true
run_java=false
run_haskell=false
combine_java_report_with_haskell=false
haskell_reports=(hpc_index.html hpc_index_alt.html hpc_index_exp.html hpc_index_fun.html)

function help() {
  echo "project code coverage script:"
  echo "  this command compiles and runs java and haskell files and gets code coverage for projects"
  echo
  echo "Options:"
  echo  "  -p or --project:                 specifies name of project"
  echo  "  -j or --java:                    compile java folder and use it to run test cases"
  echo  "  -h or --haskell:                 compile haskell folder and use it to run test cases"
  echo  "  -c or --combine_reports:         combine every java report with haskell report"
  echo
  echo "example: "
  echo "  pccs loghmeh"
}

while test $# -gt 0; do
  case "$1" in
    --help)
      help
      exit 0
      ;;
    -p|--project)
      shift
      export project=$1
      ;;
    -h|--haskell)
      export run_haskell=true
      export run_java_haskell=false
      ;;
    -j|--java)
      export run_java=true
      export run_java_haskell=false
      ;;
    -c|--combine_reports)
      export combine_java_report_with_haskell=true
      ;;
    *)
      echo "Invalid command. Try --help for more information."
      exit 1
      ;;
  esac
  shift
done

# check existing directory
if [[ -z $project ]]; then
  echo "Invalid command: Script needs a project directory name"
  echo "Try --help for more information."
  exit 1
fi
if [[ ! -d ./big-projects/$project ]]; then
  echo "project directory does not exist."
  echo "Try --help for more information."
  exit 1
fi

# create main directory and log file and clean last jobs
if [[ -d ./bin/big-projects/$project ]]; then
  rm -rf ./bin/big-projects/$project/*
else
  mkdir -p ./bin/big-projects/$project
fi
mkdir -p ./bin/big-projects/$project/report
touch ./bin/big-projects/$project/log


main_dir="./bin/big-projects/$project"

# run and copy tess generator
if [[ ! -d ./big-projects/$project/testgenerator ]]; then
  echo "test generator folder does not exist in big-projects/$project"
  exit 1
else
  mkdir -p ./bin/big-projects/$project/testgenerator
  cp ./big-projects/$project/testgenerator/* ./bin/big-projects/$project/testgenerator
  echo "test generator ran successfully"
fi


# compile and run java files
if [[ $run_java = true || $run_java_haskell = true ]]; then
  if [[ ! -d ./big-projects/$project/java ]]; then
    echo "java folder does not exist in big-projects/$project"
    exit 1
  fi
  cp -r ./big-projects/$project/java ./bin/big-projects/$project/
  cd $main_dir
  java_projects=$(ls java)
  for java_project in ${java_projects[@]}; do
    if [[ -d java/$java_project ]]; then
      java_execution_successfully=true
      java_compile_successfully=true
      cp ../../../lib/* java/$java_project/
      cd java/$java_project
      touch output
      touch log

      mvn -Dmaven.test.skip=true package >> log || java_compile_successfully=false
      if [[ $java_compile_successfully = false ]]; then
        echo "# Error: failed in java compilation. check $main_dir/java/$java_project/log"
        exit 1
      fi

      cd target
      jar_file=$project-1.0-jar-with-dependencies.jar
      if [[ -e $jar_file ]]; then
        unzip -qq $jar_file -d ./out
        cd ..
      else
        echo "please check artifactId in $java_project/pom.xml file. it should be same as project name folder(in this case it should be $project)"
        exit 1
      fi
      java -javaagent:./jacocoagent.jar -cp ./target/out Main < ../../testgenerator/tests > output 2>>log || java_execution_successfully=false
      if [[ $java_execution_successfully = false ]]; then
        echo "# Error: failed in execute java program. check $main_dir/java/$java_project/log"
        exit 1
      fi

      (java -jar ./jacococli.jar report ./jacoco.exec --html ../../report/$java_project --sourcefiles ./src/main/java/ --classfiles ./target/classes) |& cat >> ../../log
      echo "$java_project was compiled and ran successfully and report is generated"
      cd ../..
    fi
  done
  cd ../../..
fi

if [[ $run_haskell = true || $run_java_haskell = true ]]; then
  if [[ ! -d ./big-projects/$project/haskell ]]; then
    echo "haskell folder does not exist in big-projects/$project"
    exit 1
  fi
  haskell_compile_successfully=false
  haskell_execution_successfully=false
  cp -r ./big-projects/$project/haskell ./bin/big-projects/$project/
  cd $main_dir/haskell
  ghc -fhpc Main &>../log && haskell_compile_successfully=true
  if [[ $haskell_compile_successfully = false ]]; then
    echo "# Error: failed in haskell compilation. check $main_dir/log"
    exit 1
  fi
  touch haskell_output
  (cat ../testgenerator/tests | ./Main > ./haskell_output 2>../log) && haskell_execution_successfully=true
  if [[ $haskell_execution_successfully = false ]]; then
    echo "# Error: failed in execute haskell program. check $main_dir/log"
    exit 1
  fi
  mkdir -p ../report/haskell
  temp_out=$(hpc markup Main)
  mkdir -p ../report/haskell
  cp *.html ../report/haskell
  cd ../report/haskell
  cp ../../../../../resources/haskell/haskell_html_report_generator.py ./
  for h_report in ${haskell_reports[@]}; do
    python3 haskell_html_report_generator.py $h_report
  done
  rm haskell_html_report_generator.py
  echo "haskell was compiled and ran successfully and report is generated"
  cd ../../../../..
fi

if [[ $combine_java_report_with_haskell = true && ($run_java = true && $run_haskell = true || $run_java_haskell = true) ]]; then
  cd $main_dir/report
  projects=$(ls)
  for p in ${projects[@]}; do
    if [[ ! $p = haskell ]]; then
      cp haskell/*.html ./$p
      cd $p
      for h_report in ${haskell_reports[@]}; do
        cat index.html $h_report > temp
        cat temp > $h_report
        rm temp
      done
      cp hpc_index.html index.html
      firefox index.html &
      cd ..
    fi
  done
fi
