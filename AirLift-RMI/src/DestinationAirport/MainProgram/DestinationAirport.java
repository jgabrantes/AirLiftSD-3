/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DestinationAirport.MainProgram;

import DestinationAirport.SharedRegions.*;
import EntitiesState.PassengerState;
import DestinationAirport.MainProgram.DestinationAirportMain;
import DestinationAirport.Stubs.RepositoryStub;


/**
 *
 * @author jgabrantes
 */
public class DestinationAirport {
    
    private int passengersLeft= 0;
    
    private RepositoryStub repo;
    

    public DestinationAirport(RepositoryStub repo){
        this.repo = repo;
    }
    /**
     *
     */
    public synchronized void leaveThePlane(int id) {
      
        passengersLeft +=1;
        System.out.println("Passenger "+id+" is leaving the plane");
        
        repo.outPlane();
        repo.updatePassengerState(id, PassengerState.AT_DESTINATION);
        System.out.println("PassengersLeft---------->"+passengersLeft+"<---------------------------------");
        notifyAll();
        
    }

    /**
     *
     * @param numPassengers
     */
    public synchronized void waitForAllPassengersToLeave(int numPassengers){
         System.out.println("Pilot:waiting for all passengers to leave");
         System.out.println("NUMMM_PASSENGERS:"+numPassengers);
         while(numPassengers != passengersLeft){
             try{
                 wait();
             }catch(InterruptedException e){
                 System.exit(1);
             }
         }
         System.out.println("PILOT:leaving..");
         passengersLeft = 0;
     }

    public synchronized void finish() {
        DestinationAirportMain.serviceEnd = true;
    }
    
}
