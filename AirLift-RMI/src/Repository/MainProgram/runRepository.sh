#!/bin/bash 
echo "Run Repository"
rm -r classes; 
mkdir classes; 
javac -cp ".:genclass.jar" -d "classes" ../Interfaces/*.java ../MainProgram/*.java ../EntitiesState/*.java 
java -classpath ".:genclass.jar:classes" Repository.MainProgram.RepositoryMain
