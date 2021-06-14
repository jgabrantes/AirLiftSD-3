/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DepartureAirport.MainProgram;

import DepartureAirport.Interfaces.DepartureAirportInterface;
import DepartureAirport.Interfaces.RegisterInterface;
import DepartureAirport.Interfaces.RepositoryInterface;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;



/**
 * Departure Airport main 
 * @author jgabrantes
 */
public class DepartureAirportMain {
    /**
     * Check if service must terminate
     */
    public static boolean serviceEnd = false;
    
    /**
     * Departure Airport main thread
     */
    public static void main(String [] args) {
        String regHostName = Parameters.REGISTRY_HOST_NAME;
        int regPortNum = Parameters.REGISTRY_PORT;
        
        String nameEntryBase = Parameters.REGISTER_HANDLER;
        String nameEntryObject = Parameters.DEPARTURE_AIRPORT_HANDLER;
        Registry registry = null;
        RegisterInterface registerInt = null;  
        
        RepositoryInterface repo = null;
        
        if (System.getSecurityManager () == null)
            System.setSecurityManager (new SecurityManager ());
        
        try{
            registry = LocateRegistry.getRegistry(regHostName, regPortNum);
        }catch(RemoteException e){
            System.out.println("RMI registry creation exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        
        
        try
        {
            repo = (RepositoryInterface) registry.lookup (Parameters.REPOSITORY_HANDLER);
        }
        catch (NotBoundException ex) {
            System.out.println("Repository is not registered: " + ex.getMessage () );
            ex.printStackTrace ();
            System.exit(1);
        } catch (RemoteException ex) {
            System.out.println("Exception thrown while locating Repository: " + ex.getMessage () );
            ex.printStackTrace ();
            System.exit (1);
        }
        
        /**
         * Start listening on the communication channel
         */
        System.out.println("-------------------------------DEPARTURE AIRPORT --------------------------");
       
        DepartureAirport depAirport = new DepartureAirport(repo);
        DepartureAirportInterface depAirportInt = null;
        
        
        try
        {
            depAirportInt = (DepartureAirportInterface)UnicastRemoteObject.exportObject(depAirport, Parameters.DEPARTURE_AIRPORT_PORT);
        }
        catch (RemoteException ex) {
            System.out.println("Departure Airport stub generation exception: " + ex.getMessage () );
            ex.printStackTrace ();
            System.exit(1);
        }
        try
        { registerInt = (RegisterInterface) registry.lookup (nameEntryBase);
        }
        catch (RemoteException e)
        { System.out.println ("Register lookup exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        catch (NotBoundException e)
        { System.out.println ("Register not bound exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }

        try
        { registerInt.bind (nameEntryObject, depAirportInt);
        }
        catch (RemoteException e)
        { System.out.println ("Departure Airport registration exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        catch (AlreadyBoundException e)
        { System.out.println("Departure Airport already bound exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        System.out.println ("Departure Airport object was registered!");    
        
        /**
         * while service not finished , accept connections and redirect them to the Service Provider
         */
        
        while(!serviceEnd){
            try {
                synchronized(depAirport){
                    depAirport.wait();
                }
            } catch (InterruptedException e) {
                System.out.println("Main thread of departure airport was interrupted.");
                System.exit(1);
            }
        }
        System.out.println("Departure Airport finished execution");
        
         /* Unregister shared region */
        try
        { registerInt.unbind (nameEntryObject);
        }
        catch (RemoteException e)
        { System.out.println ("Departure Airport unregistration exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        } catch (NotBoundException ex) {
         System.out.println ("Departure Airport unregistration exception: " + ex.getMessage ());
          ex.printStackTrace ();
          System.exit (1);
        }
        System.out.println ("Departure Airport object was unregistered!");
        
        /* Unexport shared region */
        try
        { UnicastRemoteObject.unexportObject (depAirport, false);
        }
        catch (RemoteException e)
        { System.out.println ("Departure Airport unexport exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        
       System.out.println("Departure Airport object was unexported successfully!");
    }
}


    

