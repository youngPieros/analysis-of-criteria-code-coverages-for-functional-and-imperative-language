#!/usr/bin/env sh

main_folder=''
destination='./bin'

run_java=false
java_compile_successfully=false
java_execution_successfully=false
run_haskell=false
haskell_compile_successfully=false
run_java_haskell=true
haskell_execution_successfully=false
ignore_run_testcases=false
double_check=false
run_last_testcases=false
ignore_report=false


function help() {
  echo "code coverage script:"
  echo "  ccs command compiles and runs java and haskell files and gets code coverage "
  echo
  echo "Options:"
  echo  "  -j or --java:                    compile java folder and use it to run test cases."
  echo  "  -h or --haskell:                 compile haskell folder and use it to run test cases."
  echo  "  -i or --ignore-testcase:         does not run output files with test case."
  echo  "  -l or --last-testcases:          does not run test-generator script to run programs with last test cases"
  echo  "  -n or --no-report:               ignore generate report html files"
  echo  "  -dc or --check-outputs           compare output of haskell and java(this feature is enabled when both of java and haskell will be run)"
  echo
  echo "example: "
  echo "  ccs mergesort -h -t :: this command run all phases of ccs for haskell code with new generated test cases"
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
    -l|--last-testcases)
      export run_last_testcases=true
      ;;
    -n|--no-report)
      export ignore_report=true
      ;;
    -dc|--check-outputs)
      export double_check=true
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



# create main directory and log file
mkdir -p ./bin/$main_folder
if [[ -e ./bin/$main_folder/log ]]; then
  rm ./bin/$main_folder/log
fi
touch ./bin/$main_folder/log



# compile java
if [[ $run_java = true || $run_java_haskell = true ]]; then
  if [[ ! -d ./repository/$main_folder/java ]]; then
    echo "java folder does not exist in repository/$main_folder"
    exit 1
  fi
  if [[ ! -e ./repository/$main_folder/java/Main.java ]]; then
    echo "Main.java file does not exist in repository/$main_folder/java"
    exit 1
  fi
  mkdir -p ./bin/$main_folder/java
  cp lib/* ./bin/$main_folder/
  cp ./repository/$main_folder/java/* ./bin/$main_folder/java
  cd ./bin/$main_folder
  mkdir -p ./bin/java
  cp ./java/Main.java ./bin/java
  mkdir -p ./bin/classes
  javac -d ./bin/classes ./java/Main.java &>java_compile_log && java_compile_successfully=true
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
  if [[ -e Main ]]; then
    rm Main
  fi
  if [[ -e Main.hi ]]; then
    rm Main.hi
  fi
  if [[ -e Main.o ]]; then
    rm Main.o
  fi
  if [[ -d .hpc ]]; then
    rm -rf .hpc
  fi
  if [[ -e Main.tix ]]; then
    rm -rf Main.tix
  fi
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
if [[ $run_last_testcases = false ]]; then
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
  python3 testcaseGenerator.py > ../testcases
  cd ../../..
  echo
  echo "** test-generator ran successfully and new test case is generated"
fi



echo
# run java code with test cases and generate report
if [[ $run_java = true || $run_java_haskell = true ]]; then
  cd ./bin/$main_folder
  java -javaagent:./jacocoagent.jar -cp ./bin/classes Main < testcases > java_output 2>java_execution_log && java_execution_successfully=true
  if [[ $java_execution_successfully = true ]]; then
    echo "*** java program ran successfully with test cases"
    if [[ -e java_execution_log ]]; then
      rm java_execution_log
    fi
  else
    echo "# Error: failed in execute java program. check ./bin/$main_folder/java_execution_log"
    exit 1
  fi
  if [[ $ignore_report = false ]]; then
    (java -jar ./jacococli.jar report ./jacoco.exec --html ./report --sourcefiles ./bin/ --classfiles ./bin/classes) |& cat >> log
  fi
  cd ../..
fi
# run haskell code with test cases and generate report
if [[ $run_haskell = true || $run_java_haskell = true ]]; then
  cd ./bin/$main_folder/haskell
  (cat ../testcases | ./Main > ../haskell_output 2>../haskell_execution_log) && haskell_execution_successfully=true
  if [[ $haskell_execution_successfully = true ]]; then
    echo "*** haskell program ran successfully with test cases"
    if [[ -e ../haskell_execution_log ]]; then
      rm ../haskell_execution_log
    fi
  else
    echo "# Error: failed in execute haskell program. check ./bin/$main_folder/haskell_execution_log"
    exit 1
  fi

  hpc report Main > reportfile
  if [[ $ignore_report = true ]]; then
    cd ../../..
  else
    cd ..
    mkdir -p report
    cp ../../resources/haskell/haskell-report-resources.zip report
    cd report
    if [[ -d haskell-report-resources ]]; then
      rm -rf haskell-report-resources
    fi
    unzip haskell-report-resources.zip |& cat >> ../log
    rm haskell-report-resources.zip
    cd ../../../resources/haskell/
    python3 haskell_html_report_generator.py < ../../bin/$main_folder/haskell/reportfile > ../../bin/$main_folder/report/h_report.html
    cd ../..
  fi
fi


if [[ $double_check = true ]]; then
  output_different=$(diff ./bin/$main_folder/java_output ./bin/$main_folder/haskell_output)
  if [[ -n $output_different ]]; then
    echo
    echo "Error. There is some different between java output and haskell output"
    diff ./bin/$main_folder/java_output ./bin/$main_folder/haskell_output
    exit 1
  fi
fi
# check ignoring generating reports
if [[ $ignore_report = true ]]; then
  echo "*** run programs with test cases has been completely done"
  exit 0
fi
if [[ ($run_haskell = true && $run_java = true) || $run_java_haskell = true ]]; then
  cd ./bin/$main_folder/report
  cat h_report.html >> index.html
  firefox index.html &
elif [[ $run_haskell = true ]]; then
  cd ./bin/$main_folder/report
  cp h_report.html index.html
  firefox index.html &
elif [[ $run_java = true ]]; then
  cd ./bin/$main_folder/report
  firefox index.html &
fi
echo
echo "**** report is completed"
