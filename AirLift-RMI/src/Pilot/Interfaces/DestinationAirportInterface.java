/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pilot.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jgabrantes
 */
public interface DestinationAirportInterface extends Remote{
    
    public void waitForAllPassengersToLeave(int boardedPassengers) throws RemoteException;
    
    public void finish() throws RemoteException;
}
