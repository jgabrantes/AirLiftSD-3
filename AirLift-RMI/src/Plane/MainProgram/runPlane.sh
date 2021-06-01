#!/bin/bash 
echo "Run Plane"
rm -r classes; 
mkdir classes; 
javac -cp ".:genclass.jar" -d "classes" ../../Communications/*.java ../MainProgram/*.java ../../Entities/*.java ../Stubs/*.java ../Proxies/*.java ../SharedRegions/*.java
java -classpath ".:genclass.jar:classes" Plane.MainProgram.PlaneMain
