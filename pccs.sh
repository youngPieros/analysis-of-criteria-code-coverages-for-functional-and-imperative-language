project=""


function help() {
  echo "project code coverage script:"
  echo "  this command compiles and runs java and haskell files and gets code coverage for projects"
  echo
  echo "Options:"
  echo  "  -p or --project:                 specifies name of project"
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
      export run_java_haskell=false
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
cp -r ./big-projects/$project ./bin/big-projects/
mkdir -p ./bin/big-projects/$project/report
touch ./bin/big-projects/$project/log


main_dir="./bin/big-projects/$project"

# compile and run java files
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
    java -javaagent:./jacocoagent.jar -cp ./target/out Main < ../../tests > output 2>>log || java_execution_successfully=false
    if [[ $java_execution_successfully = false ]]; then
      echo "# Error: failed in execute java program. check $main_dir/java/$java_project/log"
      exit 1
    fi

    (java -jar ./jacococli.jar report ./jacoco.exec --html ../../report/$java_project --sourcefiles ./src/main/java/ --classfiles ./target/classes) |& cat >> ../../log
    echo "$java_project was compiled and ran successfully"
    cd ../../../../..
  fi
done

