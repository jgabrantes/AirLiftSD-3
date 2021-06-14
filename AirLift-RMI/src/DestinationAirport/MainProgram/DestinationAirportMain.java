/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DestinationAirport.MainProgram;

import DestinationAirport.Interfaces.*;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * Plane main 
 * @author jgabrantes
 */
public class DestinationAirportMain {
    /**
     * Check if service must terminate
     */
    public static boolean serviceEnd = false;
    
    /**
     * Plane main thread
     */
    public static void main(String [] args){
        String regHostName = Parameters.REGISTRY_HOST_NAME;
        int regPortNum = Parameters.REGISTRY_PORT;
        
        String nameEntryBase = Parameters.REGISTER_HANDLER;
        String nameEntryObject = Parameters.DESTINATION_AIRPORT_HANDLER;
        Registry registry = null;
        RegisterInterface registerInt = null;  
        
        RepositoryInterface repo = null;
       
        
        DestinationAirport destAirport = new DestinationAirport(repo);
        DestinationAirportInterface destAirportInt = null;
        
        /* create and install the security manager */
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
              
        try
        {
            destAirportInt = (DestinationAirportInterface)UnicastRemoteObject.exportObject(destAirport, Parameters.DESTINATION_AIRPORT_PORT);
        }
        catch (RemoteException ex) {
            System.out.println("Destination Airport stub generation exception: " + ex.getMessage () );
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
        { registerInt.bind (nameEntryObject, destAirportInt);
        }
        catch (RemoteException e)
        { System.out.println ("Destination Airport registration exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        catch (AlreadyBoundException e)
        { System.out.println("DestinationAirport already bound exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        System.out.println ("Departure Airport object was registered!");    
        
        /**
         * while service not finished , accept connections and redirect them to the Service Provider
         */
        
        while(!serviceEnd){
            try {
                synchronized(destAirport){
                    destAirport.wait();
                }
            } catch (InterruptedException e) {
                System.out.println("Main thread of destination airport was interrupted.");
                System.exit(1);
            }
        }
        System.out.println("Destination Airport finished execution");
        
         /* Unregister shared region */
        try
        { registerInt.unbind (nameEntryObject);
        }
        catch (RemoteException e)
        { System.out.println ("Destination Airport unregistration exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        } catch (NotBoundException ex) {
         System.out.println ("Destination Airport unregistration exception: " + ex.getMessage ());
          ex.printStackTrace ();
          System.exit (1);
        }
        System.out.println ("Destination Airport object was unregistered!");
        
        /* Unexport shared region */
        try
        { UnicastRemoteObject.unexportObject (destAirport, false);
        }
        catch (RemoteException e)
        { System.out.println ("Destinaiton Airport unexport exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        
       System.out.println("Destination Airport object was unexported successfully!");
        
      
    }
}
