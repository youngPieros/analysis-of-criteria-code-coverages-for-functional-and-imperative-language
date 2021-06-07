haskell_file=''
java_file=''
directory='./'
testcase='testcase'


while test $# -gt 0; do
  case "$1" in
    --help)
      echo "$runner - compile and run java and haskell files"
      echo " "
      echo "$runner [options] application [arguments]"
      echo " "
      echo "options:"
      echo "--help                show brief help"
      echo "-h,        specify haskell file name"
      echo "-j,        specify java file name"
      echo "-d,        specify common directory address of java and haskell files"
      echo "-t,        specify name of testcase file"
      exit 0
      ;;
    -h)
      shift
      if test $# -gt 0; then
        export haskell_file=$1
      else
        echo "no process specified"
        exit 1
      fi
      shift
      ;;
    -j)
      shift
      if test $# -gt 0; then
        export java_file=$1
      else
        echo "bad timestamp"
        exit 1
      fi
      shift
      ;;
	-t)
      shift
      if test $# -gt 0; then
        export testcase=$1
      else
        echo "bad timestamp"
        exit 1
      fi
      shift
      ;;
	-d)
      shift
      if test $# -gt 0; then
        export directory=$1
      else
        echo "no dir specified"
        exit 1
      fi
      shift
      ;;
    *)
      exit
      ;;
  esac
done

# make address files
java_address="./${directory}/${java_file}"
haskell_address="./${directory}/${haskell_file}"
testcase_address="./${directory}/${testcase}"

# compile files
cp $haskell_address ./bin/
javac $java_address -d ./bin/
ghc --make "./bin/${haskell_file}" -o ./bin/haskell.out

# load arguments
args=$(cat $testcase_address)

# run 
cd bin
echo "haskell output:"
echo $args | ./haskell.out
echo
echo "java output: "
java "${java_file%.*}" $args
