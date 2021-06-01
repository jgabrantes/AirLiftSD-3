package DepartureAirport.MainProgram;


import EntitiesState.HostessState;
import EntitiesState.PassengerState;
import EntitiesState.PilotState;
import DepartureAirport.MainProgram.DepartureAirportMain;


import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author jgabrantes
 */
public class DepartureAirport {
  
    private Queue<Integer> passengerQueue = new LinkedList<Integer>();
    private Queue<Integer> boardedQueue = new LinkedList<Integer>();
    private boolean readyToBoard,inQueue,checkDocs,docsShown,board;
    
    
    private RepositoryInt repo;
    
    
    public DepartureAirport(RepositoryStub repo){
        this.repo = repo;
    }
    /**
     *
     */
    public synchronized void travelToAirport(int id) 
    {
        System.out.println("Passenger"+id+" is traveling to the airport\n"); 
       
             
    }
    
    /**
     *
     */
    public synchronized void informPlaneReadyForBoarding() 
    {
        
        repo.boardingStarted(); 
        repo.updatePilotState(PilotState.READY_FOR_BOARDING);
        System.out.println("Pilot:plane ready to board");
        readyToBoard = true;
        notifyAll();
    }
    
    /**
     *
     * @return
     */
    public synchronized int prepareForPassBoarding() 
    {
        System.out.println("Hostess:Waiting for plane to be ready to board");
        while(!readyToBoard)
        {
            try
            {
                wait();
            }          
            catch(InterruptedException e)
            {
                System.out.println("Hostess:Waiting for passengers");
                System.exit(1);
            }
        }       
        readyToBoard = false;  
       
        repo.updateHostessState(HostessState.WAIT_FOR_PASSENGER);
             
        return passengerQueue.size();
    }
    
    /**
     *
     * @param id
     */
    public  synchronized void waitInQueue(int id) 
    {      
        System.out.printf("Passenger %d is waiting at the queue.\n",id);
        passengerQueue.add(id);  
        repo.inQueue();
        repo.updatePassengerState(id, PassengerState.IN_QUEUE);
           
        notifyAll();
    }    
    
    /**
     *
     */
    public synchronized void checkDocuments() 
    {
        while(passengerQueue.isEmpty()){
            try{
                wait();
            }catch(InterruptedException e){
               
                System.exit(1);
            }
        }
        System.out.println("Hostess:  ready to check documents ");
        
                
        int id= passengerQueue.remove(); 
        repo.outQueue();
        repo.passengerChecked(id);
        
        repo.updateHostessState(HostessState.CHECK_PASSENGER);
        checkDocs = true;
        notifyAll();        
    }
    
    /**
     *
     * @param id
     */
    public  synchronized void showDocuments(int id) 
    {
        System.out.println("Passenger "+ id+" waiting to show documents");
        while(!checkDocs)
        {
            try
            {
                wait();
            }
            
            catch(InterruptedException e)
            {
                
                System.exit(1);
            }
        }
        checkDocs = false;                
        docsShown = true;   
        System.out.println("Passenger "+id+" showed his/her documents" );
        notifyAll();
    }

    /**
     *
     * @return
     */
    public synchronized int waitForNextPassenger() 
    {
        while(!docsShown){
            try{
                wait();
            }catch(InterruptedException e){
                System.exit(1);
            }
        }
        docsShown = false;
        
     
        repo.updateHostessState(HostessState.WAIT_FOR_PASSENGER);
        System.out.println("Hostess:Passanger ready to board");
        System.out.println("PassengerQUEUE_SIZE-->:"+passengerQueue.size());
        board = true;
        notifyAll(); 
        return passengerQueue.size();
    }
    
    /**
     *
     */
    public synchronized void boardThePlane(int id) 
    {
        System.out.println("Passenger "+id+" waiting to board the plane");
       
        while(!board)
        {
            try
            {
                wait();
            }
            
            catch(InterruptedException e)
            {
               
                System.exit(1);
            }
        }
        board= false;  
        System.out.println("Passenger "+id+" boarded the plane");
        repo.inPlane();
             
        repo.updatePassengerState(id, PassengerState.IN_FLIGHT);
     }
    
    /**
     *
     */
    public synchronized void waitForNextFlight() 
    {
        System.out.println("Hostess:Waiting for plane to arrive");        
        repo.updateHostessState(HostessState.WAIT_FOR_FLIGHT);
    }

    /**
     *
     */
    public  synchronized  void parkAtTransfeGate() 
    {
        repo.updatePilotState(PilotState.AT_TRANFER_GATE);
        System.out.println("Pilot has parked at transfer gate");
    }

    public synchronized void finish() {
        DepartureAirportMain.serviceEnd = true;
        
    }

       
}
