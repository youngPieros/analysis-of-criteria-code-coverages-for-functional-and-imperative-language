project=""
ignore_compile=false


function help() {
  echo "project code coverage script:"
  echo "  this command compiles and runs java and haskell files and gets code coverage for projects"
  echo
  echo "Options:"
  echo  "  -p or --project:                 specifies name of project"
  echo  "  -ic or --ignore-compiling        ignore compiling maven project"
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
     -ic|--ignore-compiling)
      export ignore_compile=true
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
if [[ $ignore_compile = false ]]; then
  cd $main_dir
  java_projects=$(ls java)
  for java_project in ${java_projects[@]}; do
    if [[ -d java/$java_project ]]; then
      cp ../../../lib/* java/$java_project/
      cd java/$java_project
      mvn package
      cd target
      jar_file=$project-1.0-jar-with-dependencies.jar
      if [[ -e $jar_file ]]; then
        unzip $jar_file -d ./out
        cd ..
      else
        echo "please check artifactId in $java_project/pom.xml file. it should be same as project name folder(in this case it should be $project)"
        exit 1
      fi
      java -javaagent:./jacocoagent.jar -cp ./target/out Main < ../../tests
      (java -jar ./jacococli.jar report ./jacoco.exec --html ../../report/$java_project --sourcefiles ./src/main/java/ --classfiles ./target/classes) |& cat >> ../../log
      cd ../../../../..
    fi
  done
fi

