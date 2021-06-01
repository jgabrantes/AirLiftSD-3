#!/bin/bash 
echo "Run Hostess"
rm -r classes; 
mkdir classes; 
javac -cp ".:genclass.jar" -d "classes" ../../Communications/*.java ../MainProgram/*.java ../../Entities/*.java ../Entities/*.java ../Stubs/*.java
java -classpath ".:genclass.jar:classes" Hostess.MainProgram.HostessMain
