package Hostess.MainProgram;



import Hostess.Interfaces.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jgabrantes
 * 
 * Hostess Launcher
 */
public class HostessMain {
    /**
     * Hostess main thread
     * @param args 
     */
    public static void main(String [] args){
        
       String regHostName = Parameters.REGISTRY_HOST_NAME;
        int regPortNum = Parameters.REGISTRY_PORT;
        String entry = Parameters.REGISTER_HANDLER;
        Registry registry = null;
        
        
        DepartureAirportInterface depAirport = null;
        PlaneInterface plane = null;
        DestinationAirportInterface destAirport = null;     
        
        
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
               
        
        Hostess hostess = new Hostess(0, depAirport,destAirport, plane);
        System.out.println("-------------------------------HOSTESS --------------------------");
        hostess.start();
        try{
            hostess.join();
        } catch(InterruptedException e){}
    }
}
