#!/usr/bin/env bash
cd ..
arg1=src/main/resources/parking_lot file_inputs.txt
dir=target
jar_name=parkinglot-1.0-SNAPSHOT.jar
java -jar $dir/$jar_name $arg1
