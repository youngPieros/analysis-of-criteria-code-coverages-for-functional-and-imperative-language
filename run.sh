#!/usr/bin/env sh

haskell_file=''
java_file=''
directory='./'
testcase='testcase'
out='./bin'

while test $# -gt 0; do
  case "$1" in
    --help)
      echo
      echo "Usage:"
      echo "$runner - compile and run java and haskell files"
      echo
      echo "Options:"
      echo "  --help                    show brief help"
      echo "  -h, -haskell,             haskell file name"
      echo "  -j, -java,                java file name"
      echo "  -d, -dir,                 common directory address of java and haskell files(default dir is ./"
      echo "  -t, -testcase,            name of testcase file(default testcase file is ./testcase"
      echo "  -o, -output               directory of compiled files(default directory is ./bin)"
      echo
      echo "example: "
      echo "  ./run.sh -h sort.hs -j sort.java -d ./sorting_algorithms_dir -t sort_testcase -o ./out"
      exit 0
      ;;
    -h|--haskell)
      shift
      if test $# -gt 0; then
        export haskell_file=$1
      else
        echo "invalid command\nTry 'run --help' for more information."
        exit 1
      fi
      shift
      ;;
    -j|-java)
      shift
      if test $# -gt 0; then
        export java_file=$1
      else
        echo "invalid command\nTry 'run --help' for more information."
        exit 1
      fi
      shift
      ;;
	  -t|-testcase)
      shift
      if test $# -gt 0; then
        export testcase=$1
      else
        echo "invalid command\nTry 'run --help' for more information."
        exit 1
      fi
      shift
      ;;
	-d|-dir)
      shift
      if test $# -gt 0; then
        export directory=$1
      else
        echo "invalid command\nTry 'run --help' for more information."
        exit 1
      fi
      shift
      ;;
    -o|output)
      shift
      if test $# -gt 0; then
        export out=$1
      else
        echo "invalid command\nTry 'run --help' for more information."
        exit 1
      fi
      shift
      ;;
    *)
      echo "run: '-$1' invalid option\nTry 'run --help' for more information."
      exit
      ;;
  esac
done


# make address files
java_address="./${directory}/${java_file}"
haskell_address="./${directory}/${haskell_file}"
testcase_address="./${directory}/${testcase}"

mkdir -p out

# compile files
java_job_done=false
haskell_job_done=false
cp $java_address "./${out}/temp" && java_address="./${out}/${java_file}" && sed -e '1d' "./${out}/temp" > $java_address && javac $java_address && java_job_done=true
cp $haskell_address "./${out}" && haskell_address="./${out}/${haskell_file}" && ghc --make $haskell_address -o "./${out}/${haskell_file%.*}.out" > ./${out}/log.txt && haskell_job_done=true
if [ $java_job_done = true ]
then
  echo "java compiled successfully"
else
  echo "java compile failed"
  exit 1
fi

if [ $haskell_job_done = true ]
then
  echo "haskell compiled successfully"
else
  echo "haskell compile failed"
  exit 1
fi
echo

# load arguments
if ! [ -e $testcase_address ];
then
  echo "testcase file not found"
  exit 1
fi
args=$(cat $testcase_address)

# run
cd out
echo "haskell output:"
echo $args | "./${haskell_file%.*}.out"
echo
echo "java output: "
java "${java_file%.*}" $args
