#!/bin/bash 
echo "Run Repository"
rm -r classes; 
mkdir classes; 
javac -cp ".:genclass.jar" -d "classes" ../../Communications/*.java ../MainProgram/*.java ../../Entities/*.java ../Proxies/*.java ../SharedRegions/*.java
java -classpath ".:genclass.jar:classes" Repository.MainProgram.RepositoryMain
