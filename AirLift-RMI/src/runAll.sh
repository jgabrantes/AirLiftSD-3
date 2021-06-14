#!/bin/bash 

echo "Run Registry"
gnome-terminal -- /bin/bash -c 'cd src/Registry/MainProgram/ && sh runRegistry.sh'

echo "Run Repository"
gnome-terminal -- /bin/bash -c 'cd src/Repository/MainProgram/ && sh runRepository.sh'

echo "Run Departure Airport"
gnome-terminal -- /bin/bash -c 'cd src/DepartureAirport/MainProgram/ && sh runDepartureAirport.sh'

echo "Run Destination Airport"
gnome-terminal -- /bin/bash -c 'cd src/DestinationAirport/MainProgram/ && sh runDestinationAirport.sh'

echo "Run Plane"
gnome-terminal -- /bin/bash -c 'cd src/Plane/MainProgram/ && sh runPlane.sh'

echo "Run Pilot"
gnome-terminal -- /bin/bash -c 'cd src/Pilot/MainProgram/ && sh runPilot.sh'

echo "Run Hostess"
gnome-terminal -- /bin/bash -c 'cd src/Hostess/MainProgram/ && sh runHostess.sh'

echo "Run Passenger"
gnome-terminal -- /bin/bash -c 'cd src/Passenger/MainProgram/ && sh runPassenger.sh'

