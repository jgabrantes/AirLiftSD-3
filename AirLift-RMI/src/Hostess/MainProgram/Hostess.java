package Hostess.MainProgram;
import Hostess.EntitiesState.*;
import Hostess.Interfaces.*;
import java.rmi.RemoteException;


public class Hostess  extends Thread{
    private int id;
    private HostessState state;
    private DepartureAirportInterface depAirport;
    private PlaneInterface  plane;
    private DestinationAirportInterface destAirport;
    
    public Hostess(int id, DepartureAirportInterface depAirport, DestinationAirportInterface destAirport, PlaneInterface plane) {
        this.id = id;
        this.depAirport = depAirport;
        this.destAirport = destAirport;
        this.plane = plane;
    }
    public int getHostessId() {
        return id;
    }
    public void setHostessId(int id) {
        this.id = id;
    }
    
      /**
     *
     * @return
     */
    public HostessState getHostessState() {
        return state;
    }

    /**
     *
     * @param state
     */
    public void setHostessState(HostessState state) {
        this.state = state;
    }
    
    int nPassenger = Parameters.N_PASSENGERS;
    int boarded = 0;
    int queued;
    int tmp = nPassenger;
    
    /**
     * Hostess's Life Cycle
     */
    @Override
    public void run(){
        try{
            System.out.println("Hostess:Started coming");
            while(nPassenger> 0){  
                boarded = 0;
                queued = depAirport.prepareForPassBoarding();

                while((queued > 0 && boarded < Parameters.MAX || nPassenger >= Parameters.MIN && boarded <Parameters.MIN || nPassenger > 0 && nPassenger < Parameters.MIN) && tmp>0){
                    System.out.println("REMAIN TO BE CHECKED:"+ nPassenger);
                    System.out.println("IN QUEUE:"+ queued);
                    depAirport.checkDocuments();                
                    queued = depAirport.waitForNextPassenger();  
                    boarded++;
                    tmp--;
                }        

                //nPassenger = nPassenger - boarded;
                nPassenger -= boarded;
                System.out.println("PassengersLeft: "+nPassenger);
                plane.informPlaneReadyToTakeoff(boarded);
                depAirport.waitForNextFlight();         
            }
        }catch (RemoteException ex) {
           System.out.println("Remote exception: " + ex.getMessage ());
           ex.printStackTrace ();
           System.exit (1);
        }
       
    }
}
