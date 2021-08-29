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
    *)
      echo "Invalid command. Try --help for more information."
      exit 1
      ;;
  esac
  shift
done

echo $project
