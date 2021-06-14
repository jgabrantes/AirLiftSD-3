/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pilot.MainProgram;

import Pilot.Interfaces.*;
import Pilot.Interfaces.RepositoryInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * Pilot launcher
 * @author jgabrantes
 */
public class PilotMain {
    /**
     * Pilot's main thread
     */
    public static void main(String [] args){
        
        String regHostName = Parameters.REGISTRY_HOST_NAME;
        int regPortNum = Parameters.REGISTRY_PORT;
        String entry = Parameters.REGISTER_HANDLER;
        Registry registry = null;
        
        
        DepartureAirportInterface depAirport = null;
        PlaneInterface plane = null;
        DestinationAirportInterface destAirport = null;
        RepositoryInterface repo = null;
        
        
        try{
            registry = LocateRegistry.getRegistry(regHostName, regPortNum);
        }catch(RemoteException e){
            System.out.println("RMI registry creation exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        System.out.println("RMI registry was created!");
        
       
        try
        {
            depAirport = (DepartureAirportInterface) registry.lookup (Parameters.DEPARTURE_AIRPORT_HANDLER);
        }
        catch (NotBoundException ex) {
            System.out.println("Departure Airport is not registered: " + ex.getMessage () );
            ex.printStackTrace ();
            System.exit(1);
        } catch (RemoteException ex) {
            System.out.println("Exception thrown while locating Departure Airport: " + ex.getMessage () );
            ex.printStackTrace ();
            System.exit (1);
        }
        
        try
        {
            destAirport = (DestinationAirportInterface) registry.lookup (Parameters.DESTINATION_AIRPORT_HANDLER);
        }
        catch (NotBoundException ex) {
            System.out.println("Destination Airport is not registered: " + ex.getMessage () );
            ex.printStackTrace ();
            System.exit(1);
        } catch (RemoteException ex) {
            System.out.println("Exception thrown while locating Destination Airport: " + ex.getMessage () );
            ex.printStackTrace ();
            System.exit (1);
        }
        
        try
        {
            plane = (PlaneInterface) registry.lookup (Parameters.PLANE_HANDLER);
        }
        catch (NotBoundException ex) {
            System.out.println("Plane is not registered: " + ex.getMessage () );
            ex.printStackTrace ();
            System.exit(1);
        } catch (RemoteException ex) {
            System.out.println("Exception thrown while locating Plane: " + ex.getMessage () );
            ex.printStackTrace ();
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
        
        System.out.println("-----------------------PILOT---------------------------------");
        
        Pilot pilot = new Pilot(0,depAirport,destAirport,plane,repo);
        
        pilot.start();
        
        try {
            pilot.join();
        }catch(InterruptedException e){}
        
        
    }
}
