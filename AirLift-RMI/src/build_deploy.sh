mkdir -p DepartureAirport/target/EntitiesState
mkdir -p DepartureAirport/target/Interfaces
mkdir -p DepartureAirport/target/MainProgram

mkdir -p DestinationAirport/target/EntitiesState
mkdir -p DestinationAirport/target/Interfaces
mkdir -p DestinationAirport/target/MainProgram

mkdir -p Plane/target/EntitiesState
mkdir -p Plane/target/Interfaces
mkdir -p Plane/target/MainProgram

mkdir -p Repository/target/EntitiesState
mkdir -p Repository/target/Interfaces
mkdir -p Repository/target/MainProgram

mkdir -p Registry/target/EntitiesState
mkdir -p Registry/target/Interfaces
mkdir -p Registry/target/MainProgram

mkdir -p Hostess/target/EntitiesState
mkdir -p Hostess/target/Interfaces
mkdir -p Hostess/target/MainProgram

mkdir -p Pilot/target/EntitiesState
mkdir -p Pilot/target/Interfaces
mkdir -p Pilot/target/MainProgram

mkdir -p Passenger/target/EntitiesState
mkdir -p Passenger/target/Interfaces
mkdir -p Passenger/target/MainProgram

cd Registry
javac MainProgram/*.java Interfaces/*.java EntitiesState/*.java
mv MainProgram/*.class target/MainProgram
mv Interfaces/*.java target/Interfaces
mv EntitiesState/*.class target/EntitiesState
cd ..

cd Repository
javac MainProgram/*.java Interfaces/*.java EntitiesState/*.java
mv MainProgram/*.class target/MainProgram
mv Interfaces/*.java target/Interfaces
mv EntitiesState/*.class target/EntitiesState
cd ..

cd DepartureAirport
javac MainProgram/*.java Interfaces/*.java EntitiesState/*.java
mv MainProgram/*.class target/MainProgram
mv Interfaces/*.java target/Interfaces
mv EntitiesState/*.class target/EntitiesState
cd ..

cd DestinationAirport
javac MainProgram/*.java Interfaces/*.java EntitiesState/*.java
mv MainProgram/*.class target/MainProgram
mv Interfaces/*.java target/Interfaces
mv EntitiesState/*.class target/EntitiesState
cd ..

cd Plane
javac MainProgram/*.java Interfaces/*.java EntitiesState/*.java
mv MainProgram/*.class target/MainProgram
mv Interfaces/*.java target/Interfaces
mv EntitiesState/*.class target/EntitiesState
cd ..

cd Pilot
javac MainProgram/*.java Interfaces/*.java EntitiesState/*.java
mv MainProgram/*.class target/MainProgram
mv Interfaces/*.java target/Interfaces
mv EntitiesState/*.class target/EntitiesState
cd ..

cd Passenger
javac MainProgram/*.java Interfaces/*.java EntitiesState/*.java
mv MainProgram/*.class target/MainProgram
mv Interfaces/*.java target/Interfaces
mv EntitiesState/*.class target/EntitiesState
cd ..

cd Hostess
javac MainProgram/*.java Interfaces/*.java EntitiesState/*.java
mv MainProgram/*.class target/MainProgram
mv Interfaces/*.java target/Interfaces
mv EntitiesState/*.class target/EntitiesState
cd ..

zip -r Registry.zip Registry/target
zip -r Repository.zip Repository/target
zip -r DepartureAirport.zip DepartureAirport/target
zip -r DestinationAirport.zip DestinationAirport/target
zip -r Plane.zip Plane/target
zip -r Passenger.zip Passenger/target
zip -r Hostess.zip Hostess/target
zip -r Pilot.zip Pilot/target

passwd = $(cat passwd.txt)

spawn  scp DepartureAirport/target/DepartureAirport.zip sd102@lo40101-ws02@ua.pt:.
expect "sd102@l040101-ws02.ua.pt's password:"
send $passwd
interact sleep 5

spawn  scp DestinationAirport/target/DestinationAirport.zip sd102@lo40101-ws03@ua.pt:.
expect "sd102@l040101-ws03.ua.pt's password:"
send $passwd
interact sleep 5

spawn  scp Plane/target/Plane.zip sd102@lo40101-ws04@ua.pt:.
expect "sd102@l040101-ws04.ua.pt's password:"
send $passwd
interact sleep 5

spawn  scp Pilot/target/Pilot.zip sd102@lo40101-ws05@ua.pt:.
expect "sd102@l040101-ws05.ua.pt's password:"
send $passwd
interact sleep 5

spawn  scp Hostess/target/Hostess.zip sd102@lo40101-ws06@ua.pt:.
expect "sd102@l040101-ws06.ua.pt's password:"
send $passwd
interact sleep 5

spawn  scp Passenger/target/Passenger.zip sd102@lo40101-ws07@ua.pt:.
expect "sd102@l040101-ws07.ua.pt's password:"
send $passwd
interact sleep 5

spawn  scp Repository/target/Repository.zip sd102@lo40101-ws01@ua.pt:.
expect "sd102@l040101-ws01.ua.pt's password:"
send $passwd
interact sleep 5



