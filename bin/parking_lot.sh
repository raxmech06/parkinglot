#!/usr/bin/env bash
arg1=$1
#arg1=./src/main/resources/file_inputs.txt
dir=./target
jar_name=parkinglot-1.0-SNAPSHOT.jar
java -jar $dir/$jar_name $arg1
