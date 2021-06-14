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
public interface DestinationAirportInterface  extends Remote{    

    public void leaveThePlane()  throws RemoteException;
     
    
}
