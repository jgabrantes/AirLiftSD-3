/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pilot.MainProgram;

import Pilot.MainProgram.Parameters.*;
import static Pilot.MainProgram.Parameters.DEPARTURE_AIRPORT_HOST_NAME;
import static Pilot.MainProgram.Parameters.DEPARTURE_AIRPORT_PORT;
import static Pilot.MainProgram.Parameters.DESTINATION_AIRPORT_HOST_NAME;
import static Pilot.MainProgram.Parameters.DESTINATION_AIRPORT_PORT;
import static Pilot.MainProgram.Parameters.PLANE_HOST_NAME;
import static Pilot.MainProgram.Parameters.PLANE_PORT;
import static Pilot.MainProgram.Parameters.REPOSITORY_HOST_NAME;
import static Pilot.MainProgram.Parameters.REPOSITORY_PORT;
import Pilot.Stubs.*;

/**
 * Pilot launcher
 * @author jgabrantes
 */
public class PilotMain {
    /**
     * Pilot's main thread
     */
    public static void main(String [] args){
        
        DepartureAirportStub depAirport = new DepartureAirportStub(DEPARTURE_AIRPORT_HOST_NAME, DEPARTURE_AIRPORT_PORT);
        PlaneStub plane = new PlaneStub(PLANE_HOST_NAME, PLANE_PORT );
        DestinationAirportStub destAirport = new DestinationAirportStub(DESTINATION_AIRPORT_HOST_NAME, DESTINATION_AIRPORT_PORT);
        RepositoryStub repo = new RepositoryStub(REPOSITORY_HOST_NAME, REPOSITORY_PORT);
        Pilot pilot = new Pilot(0,depAirport,destAirport, plane, repo );
        System.out.println("-------------------------------PILOT --------------------------");
        pilot.start();
        
        try{
            pilot.join();
        }catch(InterruptedException e){}
        
        
        
    }
}
