/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Passenger.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;



/**
 *
 * @author jgabrantes
 */
public interface DepartureAirportInterface extends Remote {


    public void travelToAirport()  throws RemoteException;

    public void waitInQueue()  throws RemoteException;

    public void showDocuments(int id)  throws RemoteException;

    public void boardThePlane()  throws RemoteException;

    
    
}
