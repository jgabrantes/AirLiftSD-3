package Passenger.MainProgram;
import Passenger.EntitiesState.PassengerState;
import Passenger.Stubs.*;


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
    private  DestinationAirportStub destAirport;
    /**
     * DepartureAirport Stub
     */
    private final DepartureAirportStub depAirport;
    /**
     * Plane Stub
     */
    private final PlaneStub plane;
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
    public Passenger(int id, DepartureAirportStub depAirport,  DestinationAirportStub destAirport, PlaneStub plane) {
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
        
       depAirport.travelToAirport();
       
        try {
            this.sleep((long)(Math.random() * 1000));
            } catch (InterruptedException ex) {}
       
       depAirport.waitInQueue();
       
       depAirport.showDocuments(this.id);
       
       depAirport.boardThePlane();
       
       plane.waitForEndOfFlight();
       
       destAirport.leaveThePlane();
    }
}
