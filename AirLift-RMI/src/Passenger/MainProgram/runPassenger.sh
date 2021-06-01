#!/bin/bash 
echo "Run Passenger"
rm -r classes; 
mkdir classes; 
javac -cp ".:genclass.jar" -d "classes" ../../Communications/*.java ../MainProgram/*.java ../Entities/*.java  ../../Entities/*.java ../Stubs/*.java 
java -classpath ".:genclass.jar:classes" Passenger.MainProgram.PassengerMain
