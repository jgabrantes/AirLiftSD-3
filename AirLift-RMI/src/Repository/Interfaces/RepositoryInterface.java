/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository.Interfaces;




import Repository.EntitiesState.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jgabrantes
 */
public interface RepositoryInterface  extends Remote{

    public void boardingStarted() throws RemoteException;

    public void updatePilotState(PilotState pilotState) throws RemoteException;

    public void updateHostessState(HostessState hostessState) throws RemoteException;

    public void inQueue() throws RemoteException;

    public void updatePassengerState(int id, PassengerState passengerState) throws RemoteException;

    public void outQueue() throws RemoteException;

    public void passengerChecked(int id) throws RemoteException;

    public void inPlane() throws RemoteException;

    public void outPlane() throws RemoteException;

    public void flightDeparted() throws RemoteException;

    public void flightArrived() throws RemoteException;

    public void flyingBack()throws RemoteException;
    
    public void initLogging() throws RemoteException;
    
    public void printLogging() throws RemoteException;
    
    public void sumUp(int [] flights) throws RemoteException;
    
    public void finish()  throws RemoteException;
}
