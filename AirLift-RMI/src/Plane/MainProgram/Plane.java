/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plane.MainProgram;


import EntitiesState.PilotState;
import EntitiesState.HostessState;
import Plane.MainProgram.PlaneMain;
import Plane.Stubs.RepositoryStub;

/**
 *
 * @author jgabrantes
 */
public class Plane {
    private boolean  arrivalAnnounced, readyToFly;
    private int passengersBoarded;    
    private RepositoryStub repo;
    
    
    public Plane(RepositoryStub repo){
        this.repo =repo;
    }
    
    /**
     *
     * @param boarded
     */
    public synchronized void informPlaneReadyToTakeoff(int boarded){
        System.out.println("Hostess: Informing pilot that the plane is ready to take off");
        repo.flightDeparted();
        repo.updateHostessState(HostessState.READY_TO_FLY);
        passengersBoarded  = boarded;
        readyToFly= true;        
        notifyAll();
    }
    
    /**
     *
     * @return
     */
    public  synchronized  int waitForAllInBoard() 
    {        
        System.out.println("EYYYYYYYOOOOOOOO");
        repo.updatePilotState(PilotState.WAIT_FOR_BOARDING);
        System.out.println("Pilot:waiting  For all in board");
        while(!readyToFly)
        {
            try
            {
                wait();
            }
            
            catch(InterruptedException e)
            {
                System.out.print("Ready to fly");
                System.exit(1);
            }
        }
        readyToFly = false;
        return passengersBoarded;
        
    }
    
    /**
     *
     */
    public synchronized void flyToDestinationPoint() {
       
        System.out.println("Pilot: LIFT OF");           
        repo.updatePilotState(PilotState.FLYING_FORWARD);
    }

    /**
     *
     */
    public  synchronized void  announceArrival() {
        System.out.println("Passangers able to deboard");
        repo.flightArrived();
        repo.updatePilotState(PilotState.DEBOARDING);
        arrivalAnnounced= true;
        notifyAll();      
    } 
    
    /**
     *
     */
    public synchronized void waitForEndOfFlight() {
              
       while(!arrivalAnnounced){
           try{
                wait();
           }catch(InterruptedException ex){
               System.exit(1);
               
           }
       }
              
    }
     
    /**
     *
     */
    public synchronized void flyToDeparturePoint() {
        arrivalAnnounced = false;
        System.out.println("Pilot:flying back");
        repo.flyingBack();
        repo.updatePilotState(PilotState.FLYING_BACK);
    }    

    public  synchronized void finish() {
        PlaneMain.serviceEnd = true;
    }

 }
