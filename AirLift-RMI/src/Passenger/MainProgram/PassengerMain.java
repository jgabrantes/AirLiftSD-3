/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Passenger.MainProgram;

import static Passenger.MainProgram.Parameters.*;
import Passenger.Stubs.*;

/**
 *Passenger Launcher
 * @author jgabrantes
 */
public class PassengerMain {
    /**
     * Passenger's main thread
     */
    public static void main(String [] args){
        
        DepartureAirportStub depAirport = new DepartureAirportStub(DEPARTURE_AIRPORT_HOST_NAME, DEPARTURE_AIRPORT_PORT);
        DestinationAirportStub destAirport = new DestinationAirportStub(DESTINATION_AIRPORT_HOST_NAME,DESTINATION_AIRPORT_PORT);
        PlaneStub plane = new PlaneStub(PLANE_HOST_NAME,PLANE_PORT);
        
        
        Passenger [] passengers = new Passenger[Parameters.N_PASSENGERS];
        
        for(int i =0 ; i<Parameters.N_PASSENGERS;i++){
            passengers[i] = new Passenger(i, depAirport, destAirport, plane);
        }
        System.out.println("-------------------------------PASSENGER --------------------------");
        for(Passenger passenger: passengers ) passenger.start();
        
        for(Passenger passenger : passengers){
            try{
                passenger.join();
            }catch (InterruptedException ex) {
                
            }
        }
        
    }
}
