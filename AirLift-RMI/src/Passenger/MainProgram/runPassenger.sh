#!/bin/bash 
echo "Run Passenger"
rm -r classes; 
mkdir classes; 
javac -cp ".:genclass.jar" -d "classes" ../Interfaces/*.java ../MainProgram/*.java ../EntitiesState/*.java 
java -classpath ".:genclass.jar:classes" Passenger.MainProgram.PassengerMain
