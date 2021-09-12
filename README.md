The goal of this project is comparing criteria code coverage on java and haskell problems

## Table of Contents:
- [Description](#description)
- [Project Structure](#project-structure)
- [Scripts](#scripts)
    - [ccs script](#ccs-script)
    - [bccs script](#bccs-script)
    - [pccs script](#pccs-script)
    - [Dependencies](#dependencies)
- [Add your Project](#add-your-project)

<br>

## Description
The goal of this project is defining coverage criterion on declarative language to test an implemented model in imperative language.We chose haskell that is a pure functional language as our declarative language and java as imperative one. So I implemented both haskell and java projects in different size and their automatic test generators. In addition, I implemented three scripts that can compile-run source code and then generate report coverage. I used maven build automation tool for java codes, and to get report I used jacoco and hpc toolkits.
<br>


## Project Structure
#### &emsp; repository:
&emsp; &emsp; &emsp; This directory contains two type of projects:
<br>
&emsp; &emsp; &emsp; &emsp; &emsp; &emsp; Projects that have single java source code(SS-Type projects)
<br>
&emsp; &emsp; &emsp; &emsp; &emsp; &emsp; Projects that have multi java source code(MS-Type projects)
#### &emsp; big-projects:
&emsp; &emsp; &emsp; This directory contains bigger projects(PS-Type project)
#### &emsp; lib:
&emsp; &emsp; &emsp; This directory contains jacoco jar file that is the main tool for calculating code coverage for java source codes
#### &emsp; resources:
&emsp; &emsp; &emsp; This directory contains two scripts to generate java reports in .html format
#### &emsp; ccs.sh bccs.sh pccs.sh scripts: 
&emsp; &emsp; &emsp; These scripts compile, run test generator and run executable output of both java and haskell for generated tests and generate HTML reports for one of them
#### &emsp; README.md:
&emsp; &emsp; &emsp; The document of this project
#### &emsp; bin:
&emsp; &emsp; &emsp; This directory contains script outputs to running a project (included copies of java and Haskell source codes, their compiled files, error logs in compiling and running phases of source codes and test generator, and HTML reports for every project output)
#### &emsp; commands:
&emsp; &emsp; &emsp; This file contains all commands that can run current implemented project 
<br>


## Scripts
We have three types of problems(SS, MS and PS types) and for each one we implemented a script to run the compiling, test generating, executing and reporting code coverage automatically.
Every script needs a main argument that indicates the project directory name, and each script search its content in the related repository. Every project type should have the defined structure that is explained in its related script runner.


### ccs script
&emsp;&emsp;&emsp; ccs.sh is implemented for SS type projects:
<br>&emsp;&emsp;&emsp; ccs script runs below statements:
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 1) compile java and haskell source codes
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 2) run test generator script (implemented in python)
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 3) run executable java and haskell programs with generated testcases
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 4) make jacoco and haskell report files and combine them
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; example: ```bash ccs.sh quickSort```
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; in this example, quickSort should be sub-directory of repository folder, the structure of quickSort is:
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;quickSort
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;├── haskell
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; └── Main.hs
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;├── java
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; └── Main.java
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;└── testgenerator
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;&emsp;  └── testcaseGenerator.py
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; NOTE: the name of directories and files should be same as this structure(because script compile and run a Main file)
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; Options:
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
1) [-j or --java] : You can run the script to compile and run executable output of compiled codes just for java
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
e.g. ```bash ccs.sh linearSearch -j```  run script just for java codes
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
2) [-h or --haskell] : You can run the script to compile and run executable output of compiled codes just for haskell
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
e.g. ```bash ccs.sh linearSearch -h``` run script just for haskell codes
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
3) [-dc or --check-outputs] : Script compare output of java and haskell programs with each other, and report their difference if any different be found
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
e.g. ```bash ccs.sh quickSort -dc``` 
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
4) [-bt or --batch-testcases] : If test generator makes some test case files instead of single test case, script can run all them with this option
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
 e.g. ```bash ccs.sh selectionSort -h -bt``` 
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
5) [-i or --ignore-testcases] : With this option script just runs compilation phase
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
e.g. ```bash ccs.sh linearSearch -i``` 
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
6) [-l or --last-testcases] : With this option script does not run test-generator and run programs with last tests
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
e.g. ```bash ccs.sh linearSearch -l``` 
<br>

### bccs script
&emsp;&emsp;&emsp; bccs.sh is implemented for MS type projects:
<br>&emsp;&emsp;&emsp; bccs scrips runs below statements:
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 1) compile java(multiple files) and haskell source codes 
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 2) run test generator script (implemented in python)
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 3) run compiled java(multiple files) and haskell with generated test cases
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 4) make jacoco and haskell report files and combine them
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; example: ```bash bccs.sh batchSceduling```
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; in this example, batchScheduling should be sub-directory of repository folder, the structure of batchScheduling is:
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;BatchScheduling
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;├── haskell
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; └── Main.hs
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;├── java
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; ├── Main1.java
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; ├── Main2.java
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; └── Main3.java
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;└── testgenerator
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;└── testcaseGenerator.py
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; NOTE: the name of directories and files should be same as this structure (java files are Main1.java, Main2.java, ...)
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; Options:
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
1) [-j or --java] : You can run the script to compile and run executable output of compiled codes just for java
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
e.g. ```bash bccs.sh batchScheduling -j```  run script just for java codes
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
2) [-h or --haskell] : You can run the script to compile and run executable output of compiled codes just for haskell
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
e.g. ```bash bccs.sh batchScheduling -h``` run script just for haskell codes
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 3)
3) [-bt or --batch-testcases] : If test generator makes some test case files instead of single test case, script can run all them with this option
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; e.g. ```bash bccs.sh selectionSort -h -bt``` 
<br>

### pccs script:
&emsp;&emsp;&emsp; pccs.sh is implemented for PS type projects:
<br>&emsp;&emsp;&emsp; pccs.sh run below statements:
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 1) compile java(maven projects) and haskell source codes
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 2) run test generator scripts (implemented in python)
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 3) run compiled java artifacts and haskell with generated test cases
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 4) make jacoco and haskell report files and combine them
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
example: ```bash pccs.sh -p bolbolestan -c```
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
in this example, bolbolestan should be sub-directory of big-projects folder, the structure of bolbolestan is:
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;bolbolestan
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;├── haskell
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; └── Main.hs
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;├── java
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; ├── P1
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; │&emsp; ├── pom.xml
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; │&emsp; └── src
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; ├── P2
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; │&emsp; ├── pom.xml
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; │&emsp; └── src
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; ├── P3
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; │&emsp; ├── pom.xml
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; │&emsp; └── src
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp; └── P4
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp;&emsp;&emsp;&nbsp;├── pom.xml
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;│&emsp;&emsp;&emsp;&nbsp;└── src
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;└── testgenerator
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;├── testcaseGenerator.py
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;└── testcases
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; NOTE: the name of directories and files should be same as this structure (pom.xml should be in every java project root, e.g. P1, P2). Also, the name of directories and files should be same as this structure (in every java project, Main.java file should be placed in src/main/java path)
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; Options:

<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
1) [-j or --java] : You can run the script to compile and run executable output of compiled codes just for java
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
e.g. ```bash pccs.sh -p bolbolestan -j```  run script just for java codes
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
2) [-h or --haskell] : You can run the script to compile and run executable output of compiled codes just for haskell
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
e.g. ```bash pccs.sh -p bolbolestan -h```  run script just for haskell codes
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
3) [-c or --combine-reports] : Because we have some projects, to get less confusing in reports, in pccs script we do not combine java project reports, with this option we combine each java report with haskell's report
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
e.g. ```bash pccs.sh -p bolbolestan -c```
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
4) [-s or --static-tests] : With this option, script does not run test generator. Instead, it runs the programs with static testcases file that is placed in testgenerator directory
<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
e.g. ```bash pccs.sh -p bolbolestan -s```

### Dependencies
To run scripts you need:
- install java and add its path to PATH environment variables
- install maven
- install ghc
- install hpc
- install python3
- install zip/unzip command-line 
<br>

## Add Your Project
How You can start a new project? It's enough you know its type(one of SS, MS, PS). After you create the main directory in the correct hierarchy, you can add /java/Main.java /haskell/Main.hs testgenerator/testcaseGenerator.py and implement their codes.

