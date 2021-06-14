/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository.MainProgram;


import Repository.EntitiesState.HostessState;
import Repository.EntitiesState.PassengerState;
import Repository.EntitiesState.PilotState;
import Repository.Interfaces.RegisterInterface;
import Repository.Interfaces.RepositoryInterface;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author jgabrantes
 */
public class RepositoryMain {
    
    public static boolean serviceEnd = false;
    
    
    public static void main(String [] args){
           
        /* get location of the registry service */
        String rmiRegHostName = Parameters.REGISTRY_HOST_NAME;
        int rmiRegPortNumb = Parameters.REGISTRY_PORT;
        
        String nameEntryBase = Parameters.REGISTER_HANDLER;
        String nameEntryObject = Parameters.REPOSITORY_HANDLER;
        Registry registry = null;
        RegisterInterface registerInt = null;
        
        /* create and install the security manager */
        if (System.getSecurityManager () == null)
            System.setSecurityManager (new SecurityManager ());
        
        /* Get the RMI server registry */
        try
        { registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
        }
        catch (RemoteException e)
        { System.out.println("RMI registry locate exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
      
        /**
         * Initialization of parameters
         */
        
        int[] passengerIds = new int[Parameters.N_PASSENGERS];
        PassengerState[] passengerState = new PassengerState[Parameters.N_PASSENGERS];
        PilotState pilotState = PilotState.AT_TRANFER_GATE;
        HostessState hostessState = HostessState.WAIT_FOR_FLIGHT;
        
        for(int i = 0; i<Parameters.N_PASSENGERS;i++){
            passengerIds[i] = i;
            passengerState[i] = PassengerState.GOING_TO_AIRPORT;
        }
        
        /**
         * Shared region and proxy initialization
         */
        Repository repo = new Repository(passengerIds, pilotState, hostessState, passengerState);
        RepositoryInterface repoInt = null;
        try {
            repo.initLogging();
        } catch (RemoteException ex) {
            Logger.getLogger(RepositoryMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            repo.initLogging();
        } catch (RemoteException ex) {
            Logger.getLogger(RepositoryMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try
        { repoInt = (RepositoryInterface) UnicastRemoteObject.exportObject (repo, Parameters.REPOSITORY_PORT);
        }
        catch (RemoteException e)
        { System.out.println ("Repository stub generation exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        
        /* register it with the general registry service */
        try
        {   
            registerInt = (RegisterInterface) registry.lookup (nameEntryBase);
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
        {   
            registerInt.bind (nameEntryObject, repoInt);
        }
        catch (RemoteException e)
        { System.out.println ("Repository registration exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        catch (AlreadyBoundException e)
        { System.out.println ("Repository already bound exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        System.out.println ("Repository object was registered!");
        
        /* Wait for the service to end */
        while(!serviceEnd){
            try {
                synchronized(repo){
                    repo.wait();
                }
            } catch (InterruptedException ex) {
                System.out.println("Main thread of repo was interrupted.");
                System.exit(1);
            }
        }
        
        System.out.println("Repository finished execution.");
        
        /* Unregister shared region */
        try
        { registerInt.unbind (nameEntryObject);
        }
        catch (RemoteException e)
        { System.out.println ("Repository unregistration exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        } catch (NotBoundException ex) {
          System.out.println ("Repository unregistration exception: " + ex.getMessage ());
          ex.printStackTrace ();
          System.exit (1);
        }
        System.out.println ("Repository object was unregistered!");
        
        /* Unexport shared region */
        try
        { UnicastRemoteObject.unexportObject (repo, false);
        }
        catch (RemoteException e)
        { System.out.println ("Repository unexport exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        
        System.out.println ("Repository object was unexported successfully!");
       
        
      
      
    }
}
