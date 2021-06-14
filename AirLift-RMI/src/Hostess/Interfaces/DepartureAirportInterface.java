/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hostess.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;



/**
 *
 * @author jgabrantes
 */
public interface DepartureAirportInterface  extends Remote{

    public int prepareForPassBoarding() throws RemoteException;

    public void checkDocuments()throws RemoteException;

    public int waitForNextPassenger()throws RemoteException;

    public void waitForNextFlight()throws RemoteException;
    
}
