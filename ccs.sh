#!/usr/bin/env sh

main_folder=''
destination='./bin'

run_java=false
java_compile_successfully=false
java_execution_successfully=true
run_haskell=false
haskell_compile_successfully=false
run_java_haskell=true
haskell_execution_successfully=false
ignore_run_testcases=false
double_check=false
run_last_testcases=false
test_generator_execution_successfully=true
ignore_report=false
is_batch_testcases=false
testcases=()
haskell_reports=(hpc_index.html hpc_index_alt.html hpc_index_exp.html hpc_index_fun.html)

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
  echo  "  -bt or --batch-testcases         use batch testcases to test"
  echo
  echo "example: "
  echo "  ccs mergesort -h :: this command run all phases of ccs for haskell code with new generated test cases"
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
  if [[ $run_last_testcases = true && -e ./bin/$main_folder/testcases ]]; then
    testcase_content=""
    testcase_content=$(cat ./bin/$main_folder/testcases)
    rm -rf ./bin/$main_folder/*
    echo $testcase_content > ./bin/$main_folder/testcases
  else
    rm -rf ./bin/$main_folder/*
  fi
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
  if [[ ! -e ./repository/$main_folder/java/Main.java ]]; then
    echo "Main.java file does not exist in repository/$main_folder/java"
    exit 1
  fi
  mkdir -p ./bin/$main_folder/java/$main_folder/java
  cp lib/* ./bin/$main_folder/
  cp ./repository/$main_folder/java/* ./bin/$main_folder/java/$main_folder/java
  cd ./bin/$main_folder
  mkdir -p ./bin/java
  cp -r ./java/* ./bin/java/
  mkdir -p ./bin/classes
  javac -d ./bin/classes ./bin/java/$main_folder/java/Main.java &>java_compile_log && java_compile_successfully=true
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
  if [[ $is_batch_testcases = false ]]; then
    python3 testcaseGenerator.py > ../testcases 2>../testgenerator_execution_log || test_generator_execution_successfully=false
  else
    testcases=$(python3 testcaseGenerator.py 2>../testgenerator_execution_log || test_generator_execution_successfully=false)
  fi
  if [[ $test_generator_execution_successfully = true ]]; then
    echo
    echo "** test-generator ran successfully and new test case is generated"
    if [[ -e ../testgenerator_execution_log ]]; then
      rm ../testgenerator_execution_log
    fi
  else
    echo
    echo "# Error: failed in execute test generator. check ./bin/$main_folder/testgenerator_execution_log"
    exit 1
  fi
  cd ../../..
fi



echo
# run java code with test cases and generate report
if [[ $run_java = true || $run_java_haskell = true ]]; then
  cd ./bin/$main_folder
  if [[ $is_batch_testcases = false ]]; then
    java -javaagent:./jacocoagent.jar -cp ./bin/classes $main_folder.java.Main < testcases > java_output 2>java_execution_log || java_execution_successfully=false
  else
    for testcase in $testcases; do
      java -javaagent:./jacocoagent.jar -cp ./bin/classes $main_folder.java.Main < ./testgenerator/$testcase >> java_output 2>java_execution_log || java_execution_successfully=false
      if [[ $java_execution_successfully = false ]]; then
        break
      fi
    done
  fi
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
    (java -jar ./jacococli.jar report ./jacoco.exec --html ./report --sourcefiles ./bin/java/ --classfiles ./bin/classes) |& cat >> log
  fi
  cd ../..
fi
# run haskell code with test cases and generate report
if [[ $run_haskell = true || $run_java_haskell = true ]]; then
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
  if [[ $ignore_report = true ]]; then
    cd ../../..
  else
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
