/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pilot.Interfaces;

import Pilot.EntitiesState.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jgabrantes
 */
public interface RepositoryInterface extends Remote {

    public void sumUp(int[] flights) throws RemoteException;

    public void finish() throws RemoteException;

    public void boardingStarted();

    public void updatePilotState(PilotState pilotState);  

    public void inQueue();   

    public void outQueue();

    public void passengerChecked(int id);

    public void inPlane();
    
}
