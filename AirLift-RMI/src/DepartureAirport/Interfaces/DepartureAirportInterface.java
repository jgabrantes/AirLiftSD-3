/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DepartureAirport.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;



/**
 *
 * @author jgabrantes
 */
public interface DepartureAirportInterface extends Remote {


    public void travelToAirport(int id)  throws RemoteException;
   
    public void showDocuments(int id)  throws RemoteException;
        
    public void informPlaneReadyForBoarding() throws RemoteException;
    
    public int prepareForPassBoarding() throws RemoteException;

    public void waitInQueue(int id) throws RemoteException;
    
    public void checkDocuments() throws RemoteException;
    
    public int waitForNextPassenger() throws RemoteException;
    
    public void boardThePlane(int id) throws RemoteException;
    
    public void waitForNextFlight() throws RemoteException;
    
    public void parkAtTransfeGate() throws RemoteException;
    
    public void finish() throws RemoteException;
}
