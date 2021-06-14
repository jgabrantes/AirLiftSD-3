package Pilot.MainProgram;

import Pilot.Interfaces.*;
import Pilot.EntitiesState.PilotState;
import Pilot.Interfaces.RepositoryInterface;
import java.rmi.RemoteException;


/**
 *
 * @author jgabrantes
 */
public class Pilot extends Thread {
    
    /**
     * Pilot's ID
     */
    private   int id;
    
    /**
     * Departure Airport Stub
     */
    private  DepartureAirportInterface depAirport;
    
    /**
     * Destination Airport Stub
     */
    private DestinationAirportInterface destAirport;
    
    /**
     * Plane Stub
     */
    private PlaneInterface  plane;
    
    /**
     * Pilot's State
     */
    private  PilotState state;
    
    /**
     * Repository Stub
     */
    private RepositoryInterface repo;
    
    /**
     * Total number of passengers
     */
    private int nPassengers = Parameters.N_PASSENGERS,  passengersMoved = 0;
    private int  flights [] = new int[3];
    
    /**
     *
     * 
     * Pilot's instantiation
     * @param id
     * @param depAirport
     * @param destAirport
     * @param plane
     */
    public Pilot(int id, DepartureAirportInterface depAirport, DestinationAirportInterface destAirport, PlaneInterface plane, RepositoryInterface repo) {
        this.id = id;
        this.depAirport = depAirport;
        this.destAirport = destAirport;
        this.plane = plane;
        this.repo = repo;
        
    }

    /**
     *Pilot's Life cycle
     */
    @Override
    public void run() {
        try{
            
        int i = 0;
        while(passengersMoved < nPassengers){
            
            if(i==flights.length){
              int [] tmp = new int[flights.length +1];
              System.arraycopy(flights, 0, tmp, 0 , flights.length);
            }
            System.out.println("PassengersTransported::"+passengersMoved);

            depAirport.parkAtTransfeGate();
            depAirport.informPlaneReadyForBoarding();
                       
            int boardedPassengers = plane.waitForAllInBoard();
            flights[i]= boardedPassengers;
            
            
            passengersMoved += boardedPassengers;
            plane.flyToDestinationPoint();
            try {
                this.sleep((long)(Math.random() * 1000));
            } catch (InterruptedException ex) {}
        
            plane.announceArrival();
            System.out.println("BOARDED PASSENGERS:"+boardedPassengers);
            destAirport.waitForAllPassengersToLeave(boardedPassengers);
            boardedPassengers = 0;
            plane.flyToDeparturePoint();  
            i++;
        }
        
        depAirport.parkAtTransfeGate();
        repo.sumUp(flights);      
        repo.finish();
        depAirport.finish();
        destAirport.finish();
        plane.finish();
        } catch (RemoteException ex) {
            System.out.println("Remote exception: " + ex.getMessage ());
            ex.printStackTrace ();
            System.exit (1);
        }
    }
    
    /**
     *
     * @return
     */
    public int getPilotId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setPilotId(int id) {
        this.id = id;
    }
    
    /**
     *
     * @return
     */
    public PilotState getPilotState() {
        return state;
    }

    /**
     *
     * @param state
     */
    public void setPilotState(PilotState state) {
        this.state = state;
    }
    
    /**
     *
     * @param repository
     */
    public synchronized void setRepository(RepositoryInterface repository){
        this.repo = repository;
    }
    
}
