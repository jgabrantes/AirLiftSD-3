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
public interface DepartureAirportInterface  extends Remote{
    
    
    public void parkAtTransfeGate() throws RemoteException;
    
    public void informPlaneReadyForBoarding() throws RemoteException;
     
    public void finish() throws RemoteException;
     
}
