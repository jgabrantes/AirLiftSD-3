/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plane.MainProgram;



import Plane.EntitiesState.HostessState;
import Plane.EntitiesState.PilotState;
import Plane.Interfaces.*;
import java.rmi.RemoteException;



/**
 *
 * @author jgabrantes
 */
public class Plane implements PlaneInterface {
    private boolean  arrivalAnnounced, readyToFly;
    private int passengersBoarded;    
    private RepositoryInterface repo;
    
    
    public Plane(RepositoryInterface repo){
        this.repo =repo;
    }
    
    /**
     *
     * @param boarded
     */
    @Override
    public synchronized void informPlaneReadyToTakeoff(int boarded)throws RemoteException{
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
    @Override
    public  synchronized  int waitForAllInBoard() throws RemoteException 
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
    @Override
    public synchronized void flyToDestinationPoint() throws RemoteException {
       
        System.out.println("Pilot: LIFT OF");           
        repo.updatePilotState(PilotState.FLYING_FORWARD);
    }

    /**
     *
     */
    @Override
    public  synchronized void  announceArrival() throws RemoteException {
        System.out.println("Passangers able to deboard");
        repo.flightArrived();
        repo.updatePilotState(PilotState.DEBOARDING);
        arrivalAnnounced= true;
        notifyAll();      
    } 
    
    /**
     *
     */
    @Override
    public synchronized void waitForEndOfFlight()throws RemoteException {
              
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
    @Override
    public synchronized void flyToDeparturePoint() throws RemoteException {
        arrivalAnnounced = false;
        System.out.println("Pilot:flying back");
        repo.flyingBack();
        repo.updatePilotState(PilotState.FLYING_BACK);
    }    

    @Override
    public  synchronized void finish() throws RemoteException{
        PlaneMain.serviceEnd = true;
    }

 }
