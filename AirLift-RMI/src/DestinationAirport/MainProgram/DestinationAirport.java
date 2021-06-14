/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DestinationAirport.MainProgram;


import DestinationAirport.Interfaces.*;
import DestinationAirport.EntitiesState.*;
import java.rmi.RemoteException;



/**
 *
 * @author jgabrantes
 */
public class DestinationAirport  implements DestinationAirportInterface{
    
    private int passengersLeft= 0;
    
    private RepositoryInterface repo;
    

    public DestinationAirport(RepositoryInterface repo){
        this.repo = repo;
    }
    /**
     *
     */
    @Override
    public synchronized void leaveThePlane(int id) throws RemoteException {
      
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
    @Override
    public synchronized void waitForAllPassengersToLeave(int numPassengers) throws RemoteException{
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
    
    @Override
    public synchronized void finish() throws RemoteException{
        DestinationAirportMain.serviceEnd = true;
    }
    
}
