#!/usr/bin/env sh

main_folder=''
destination='./bin'

java_files=()
java_files_number=0
haskell_files=()
run_java=false
java_compile_successfully=true
java_execution_successfully=true
run_haskell=false
haskell_compile_successfully=false
run_java_haskell=true
haskell_execution_successfully=false
ignore_run_testcases=false
test_generator_execution_successfully=true
is_batch_testcases=false
testcases=()
haskell_reports=(hpc_index.html hpc_index_alt.html hpc_index_exp.html hpc_index_fun.html)



function help() {
  echo "code coverage script:"
  echo "  bccs command compiles and runs java and haskell files and gets code coverage"
  echo
  echo "Options:"
  echo  "  -j or --java:                    compile java folder and use it to run test cases."
  echo  "  -h or --haskell:                 compile haskell folder and use it to run test cases."
  echo  "  -i or --ignore-testcase:         does not run output files with test case."
  echo  "  -bt or --batch-testcases         use batch testcases to test"
  echo
  echo "example: "
  echo "  bccs mergesort -h -bt :: this command run all phases of ccs for haskell code with new generated test cases"
}

while test $# -gt 0; do
  case "$1" in
    --help)
      help
      exit 0
      ;;
    -h|--haskell)
      export run_haskell=true
      export run_java_haskell=false
      ;;
    -j|--java)
      export run_java=true
      export run_java_haskell=false
      ;;
    -i|--ignore-testcase)
      export ignore_run_testcases=true
      ;;
    -bt|--batch-testcases)
      export is_batch_testcases=true
      ;;
    *)
      if [[ -z $main_folder ]]; then
        export main_folder=$1
      else
        echo "Invalid command. Try --help for more information."
        exit 1
      fi
      ;;
  esac
  shift
done



# check existing directory
if [[ -z $main_folder ]]; then
  echo "Invalid command: Script needs a folder name"
  echo "Try --help for more information."
  exit 1
fi
if [[ ! -d ./repository/$main_folder ]]; then
  echo "Main folder does not exist."
  echo "Try --help for more information."
  exit 1
fi



# create main directory and log file and clean last jobs
if [[ -d ./bin/$main_folder ]]; then
  rm -rf ./bin/$main_folder/*
else
  mkdir -p ./bin/$main_folder
fi
touch ./bin/$main_folder/log



# compile java
if [[ $run_java = true || $run_java_haskell = true ]]; then
  if [[ ! -d ./repository/$main_folder/java ]]; then
    echo "java folder does not exist in repository/$main_folder"
    exit 1
  fi
  mkdir -p ./bin/$main_folder/java/$main_folder/java
  cp lib/* ./bin/$main_folder/
  cp ./repository/$main_folder/java/* ./bin/$main_folder/java/$main_folder/java
  cd ./bin/$main_folder
  mkdir -p ./bin/java
  cp -r ./java/* ./bin/java/
  java_files=$(ls ./bin/java/$main_folder/java/)
  mkdir -p ./bin/classes
  for file in ${java_files[@]}; do
    java_files_number=$(expr $java_files_number + 1)
    javac -d ./bin/classes ./bin/java/$main_folder/java/$file &>java_compile_log || java_compile_successfully=false
    if [[ $java_compile_successfully = false ]]; then
      break
    fi
  done

  if [[ $java_compile_successfully = true ]]; then
    echo "* java compiled successfully"
    if [[ -e java_compile_log ]]; then
      rm java_compile_log
    fi
  else
    echo "# Error: failed in java compilation. check ./bin/$main_folder/java_compile_log"
    exit 1
  fi
  cd ../..
fi
# compile haskell
if [[ $run_haskell = true || $run_java_haskell = true ]]; then
  if [[ ! -d ./repository/$main_folder/haskell ]]; then
    echo "haskell folder does not exist in repository/$main_folder"
    exit 1
  fi
  if [[ ! -e ./repository/$main_folder/haskell/Main.hs ]]; then
    echo "Main.hs file does not exist in repository/$main_folder/haskell"
    exit 1
  fi
  mkdir -p ./bin/$main_folder/haskell
  cp ./repository/$main_folder/haskell/* ./bin/$main_folder/haskell
  cd ./bin/$main_folder/haskell
  ghc -fhpc Main &>../haskell_compile_log && haskell_compile_successfully=true
  if [[ $haskell_compile_successfully = true ]]; then
    echo "* haskell compiled successfully"
    if [[ -e ../haskell_compile_log ]]; then
      rm ../haskell_compile_log
    fi
  else
    echo "# Error: failed in haskell compilation. check ./bin/$main_folder/haskell_compile_log"
    exit 1
  fi
  cd ../../..
fi



# check exit after compile phase
if [[ $ignore_run_testcases = true ]]; then
  echo "* compilation phase ran successfully"
  exit 0
fi
# run test case generator
if [[ ! -d ./repository/$main_folder/testgenerator ]]; then
  echo "test generator folder does not exist in repository/$main_folder"
  exit 1
fi
if [[ ! -e ./repository/$main_folder/testgenerator/testcaseGenerator.py ]]; then
  echo "testcaseGenerator.py file does not exist in repository/$main_folder/testcasegenerator"
  exit 1
fi
mkdir -p ./bin/$main_folder/testgenerator
cp ./repository/$main_folder/testgenerator/* ./bin/$main_folder/testgenerator
cd ./bin/$main_folder/testgenerator
if [[ $is_batch_testcases = false ]]; then
  python3 testcaseGenerator.py > ../testcases 2>../testgenerator_execution_log || test_generator_execution_successfully=false
else
  testcases=$(python3 testcaseGenerator.py 2>../testgenerator_execution_log || test_generator_execution_successfully=false)
fi
if [[ $test_generator_execution_successfully = true ]]; then
  echo
  echo "** test-generator ran successfully and new test case is generated"
  echo
  if [[ -e ../testgenerator_execution_log ]]; then
    rm ../testgenerator_execution_log
  fi
else
  echo
  echo "# Error: failed in execute test generator. check ./bin/$main_folder/testgenerator_execution_log"
  exit 1
fi
cd ../../..



# run java code with test cases and generate report
if [[ $run_java = true || $run_java_haskell = true ]]; then
  cd ./bin/$main_folder
  for (( i=1; i <= $java_files_number ; i++ )); do
    java_exec_file="$main_folder.java.Main${i}"
    java_output_file="java_output${i}"
    if [[ $is_batch_testcases = false ]]; then
      java -javaagent:./jacocoagent.jar -cp ./bin/classes $java_exec_file < testcases > $java_output_file 2>java_execution_log || java_execution_successfully=false
    else
      for testcase in $testcases; do
        java -javaagent:./jacocoagent.jar -cp ./bin/classes $java_exec_file < ./testgenerator/$testcase >> $java_output_file 2>java_execution_log || java_execution_successfully=false
        if [[ $java_execution_successfully = false ]]; then
          break
        fi
      done
    fi
  done
  if [[ $java_execution_successfully = true ]]; then
    echo "*** java program ran successfully with test cases"
    if [[ -e java_execution_log ]]; then
      rm java_execution_log
    fi
  else
    echo "# Error: failed in execute java program. check ./bin/$main_folder/java_execution_log"
    exit 1
  fi
  (java -jar ./jacococli.jar report ./jacoco.exec --html ./report --sourcefiles ./bin/java/ --classfiles ./bin/classes) |& cat >> log
  cd ../..
fi
# run haskell code with test cases and generate report
if [[ $run_haskell = true || $run_java_haskell = true ]]; then
  print
  cd ./bin/$main_folder/haskell
  if [[ $is_batch_testcases = false ]]; then
    (cat ../testcases | ./Main > ../haskell_output 2>../haskell_execution_log) && haskell_execution_successfully=true
  else
    haskell_execution_successfully=true
    for testcase in $testcases; do
      (cat ../testgenerator/$testcase | ./Main >> ../haskell_output 2>../haskell_execution_log) || haskell_execution_successfully=false
      if [[ $haskell_execution_successfully = false ]]; then
        break
      fi
    done
  fi
  if [[ $haskell_execution_successfully = true ]]; then
    echo "*** haskell program ran successfully with test cases"
    if [[ -e ../haskell_execution_log ]]; then
      rm ../haskell_execution_log
    fi
  else
    echo
    echo "# Error: failed in execute haskell program. check ./bin/$main_folder/haskell_execution_log"
    exit 1
  fi
  mkdir -p ../report
  temp_out=$(hpc markup Main)
  cp *.html ../report
  hpc report Main > ../report/reportfile
  cd ../report
  cp ../../../resources/haskell/* ./
  unzip -qq haskell-report-resources.zip
  python3 haskell_textual_report_generator.py < reportfile > haskell_textual_report.html
  for h_report in ${haskell_reports[@]}; do
    python3 haskell_html_report_generator.py $h_report
    cp $h_report temp
    cat haskell_textual_report.html temp > $h_report
  done
  rm haskell_html_report_generator.py
  rm haskell_textual_report_generator.py
  rm haskell-report-resources.zip
  rm temp
  cd ../../..
fi

# check ignoring generating reports
if [[ ($run_haskell = true && $run_java = true) || $run_java_haskell = true ]]; then
  cd ./bin/$main_folder/report
  for h_report in ${haskell_reports[@]}; do
    cat index.html $h_report > temp
    cat temp > $h_report
    rm temp
  done
  cp hpc_index.html index.html
elif [[ $run_haskell = true ]]; then
  cd ./bin/$main_folder/report
  cp hpc_index.html index.html
elif [[ $run_java = true ]]; then
  cd ./bin/$main_folder/report
fi
echo
echo "**** report is completed"
firefox index.html &
exit 0
