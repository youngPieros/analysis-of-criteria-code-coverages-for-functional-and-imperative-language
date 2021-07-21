#!/usr/bin/env sh

main_folder=''
destination='./bin'

run_java=false
run_haskell=false
run_java_haskell=true
generate_new_testcases=true
ignore_run_testcases=false
run_last_testcases=false
ignore_report=false


function help() {
  echo
  echo "code coverage script:"
  echo "  ccs command compiles and runs java and haskell files and gets code coverage "
  echo
  echo "Options:"
  echo  "  -j or --java:                    compile java folder and use it to run test cases."
  echo  "  -h or --haskell:                 compile haskell folder and use it to run test cases."
  echo  "  -t or --test:                    run test-generator script to generate new test cases."
  echo  "  -i or --ignore-testcase:         does not run output files with test case."
  echo  "  -l or --last-testcases:          does not run test-generator script to run programs with last test cases"
  echo  "  -n or --no-report:ignore generate report html files"
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
      ;;
    -j|--java)
      export run_java=true
      ;;
	  -t|--test)
	    export generate_new_testcases=true
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
    *)
      echo "run: '-$1' invalid command\nTry 'run --help' for more information."
      exit 1
      ;;
  esac
  shift
done

