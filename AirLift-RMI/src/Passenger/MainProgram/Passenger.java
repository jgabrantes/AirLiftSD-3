package Passenger.MainProgram;
import Passenger.EntitiesState.PassengerState;
import Passenger.Interfaces.*;
import java.rmi.RemoteException;


/**
 *
 * @author jgabrantes
 */
public class Passenger extends Thread {
    
    /**
     * Passenger ID
     */
    private int id;
    /**
     * DestinationAirport Stub
     */
    private  DestinationAirportInterface destAirport;
    /**
     * DepartureAirport Stub
     */
    private final DepartureAirportInterface depAirport;
    /**
     * Plane Stub
     */
    private final PlaneInterface plane;
    /**
     * PassengerState
     */
    private PassengerState state;

    /**
     *
     * Passenger instantiation
     * @param id
     * @param depAirport
     * @param destAirport
     * @param plane
     */
    public Passenger(int id, DepartureAirportInterface depAirport,  DestinationAirportInterface destAirport, PlaneInterface plane) {
        this.id = id;
        this.destAirport = destAirport;
        this.depAirport = depAirport;
        this.plane = plane;
    }

    /**
     *
     * @return
     */
    public int getPassengerId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setPassengerId(int id) {
        this.id = id;
    }
     
    /**
     *
     * @return
     */
    public PassengerState getPassengerState() {
        return state;
    }

    /**
     *
     * @param state
     */
    public void setPassengerState(PassengerState state) {
        this.state = state;
    }

    /**
     *Passenger's Life Cycle
     */
    @Override
    public void run() {
        
       try{
         depAirport.travelToAirport();
       
        try {
            this.sleep((long)(Math.random() * 1000));
            } catch (InterruptedException ex) {}
       
        depAirport.waitInQueue();
       
        depAirport.showDocuments(this.id);
       
        depAirport.boardThePlane();
       
        plane.waitForEndOfFlight();
       
        destAirport.leaveThePlane();  
        } catch (RemoteException ex) {
           System.out.println("Remote exception: " + ex.getMessage ());
           ex.printStackTrace ();
           System.exit (1);
        }
       
    }
}
