/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository.MainProgram;

import Repository.EntitiesState.*;
import Repository.Interfaces.RepositoryInterface;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author jgabrantes
 */
public class Repository implements RepositoryInterface {
    
    
    private int inQueue, inPlane, outPlane, flightNum;
    private int []passengerIds;
    private List<Integer> flights = new ArrayList<Integer>();
    private PilotState pilotState;
    private HostessState hostessState;
    private PassengerState[] passengerState;

    /**
     *
     * @param passengerIds
     * @param pilotState
     * @param hostessState
     * @param passengerState
     */
    public Repository(int [] passengerIds, PilotState pilotState, HostessState hostessState, PassengerState[] passengerState){
        this.inQueue = 0;
        this.inPlane = 0;
        this.outPlane = 0;
        this.flightNum = 0;
        this.passengerIds = passengerIds;
        this.pilotState = pilotState;
        this.hostessState = hostessState;
        this.passengerState = passengerState;
        
    }
    
    /**
     *
     */
    @Override
    public synchronized void initLogging() throws RemoteException{
        
        try{
            
            FileWriter fw = new FileWriter(Parameters.FILENAME);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write("                                                Airlift - Description of the internal stage");
            bw.newLine();
            bw.newLine();
            
            bw.write(" Pt");
            bw.write("   HT");
            for(int i = 0; i< Parameters.N_PASSENGERS; i++){
                bw.write("   P"+String.format("%02d",i));                
            }
            bw.write(" InQ");
            bw.write(" InF");
            bw.write(" PTAL");
            bw.newLine();
            bw.close();
            fw.close();
                    
            
            
        }catch(IOException ex){
          //  GenericIO.writelnString("initStateLog error - Could not write to logger file.");
          System.exit(1);
        }
    }
    
    /**
     *
     */
    @Override
    public synchronized void printLogging() throws RemoteException{
       try{
           
           FileWriter fw = new FileWriter(Parameters.FILENAME, true);
           BufferedWriter bw = new BufferedWriter(fw);
           
           
           bw.write(pilotState.toString());
           bw.write(" "+hostessState.toString());
           
           for(int i = 0; i<passengerIds.length; i++){
               bw.write(" "+ passengerState[i]+" ");
           }
           bw.write(" "+ String.format("%2d", this.inQueue));
           bw.write("  "+ String.format("%2d",this.inPlane));
           bw.write("  "+ String.format("%2d",this.outPlane));
           
           bw.newLine();
           bw.close();
           fw.close();
       } catch (IOException ex) { 
            System.exit(1);
        } 
    }
    
    /**
     *
     * @param pilotState
     */
    @Override
    public synchronized void updatePilotState(PilotState pilotState) throws RemoteException{
        this.pilotState = pilotState;
        this.printLogging();
    }
    
    /**
     *
     * @param hostessState
     */
    @Override
    public synchronized void updateHostessState(HostessState hostessState)throws RemoteException{
        this.hostessState = hostessState;
        this.printLogging();
    }
    
    /**
     *
     * @param idx
     * @param passengerState
     */
    @Override
    public synchronized void updatePassengerState(int idx, PassengerState passengerState) throws RemoteException{
        for(int i = 0; i<passengerIds.length; i++){
            if(passengerIds[i] == idx) this.passengerState[i] = passengerState;
        }
        this.printLogging();
    }
    
    /**
     *
     */
    @Override
    public synchronized void inQueue() throws RemoteException{
        this.inQueue += 1;
    }

    /**
     *
     */
    @Override
    public synchronized void outQueue() throws RemoteException{
        this.inQueue -= 1;
    }
    
    /**
     *
     */
    @Override
    public synchronized void inPlane() throws RemoteException{
        this.inPlane += 1;
    }
    
    /**
     *
     */
    @Override
    public synchronized void outPlane() throws RemoteException{
        this.inPlane -= 1;
        this.outPlane += 1;
    }
   
    /**
     *
     * @param passengerID
     */
    @Override
    public synchronized void passengerChecked(int passengerID) throws RemoteException{
         
        try {
            FileWriter fw = new FileWriter(Parameters.FILENAME, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(String.format("Flight %1d: passenger %1d checked.",this.flightNum, passengerID));
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException ex) {
            System.exit(1);
        }       
    }
    
    /**
     *
     */
    @Override
    public synchronized void boardingStarted() throws RemoteException{
        try {
           FileWriter fw = new FileWriter(Parameters.FILENAME, true);
           BufferedWriter bw = new BufferedWriter(fw);
           bw.newLine();
           this.flightNum += 1;
           bw.write(String.format("Flight %1d: boarding started.",this.flightNum));
           bw.newLine();
           bw.close();
           fw.close();
        } catch (IOException ex) {
           System.exit(1);
        }               
    }
    
    /**
     *
     */
    @Override
    public synchronized void flightDeparted() throws RemoteException{
        try {
               FileWriter fw = new FileWriter(Parameters.FILENAME, true);
               BufferedWriter bw = new BufferedWriter(fw);
               bw.newLine();
               bw.write(String.format("Flight %1d: departed with %1d passengers.",this.flightNum, this.inPlane));
               flights.add(this.inPlane);
               bw.newLine();
               bw.close();
               fw.close();
            } catch (IOException ex) {
               System.exit(1);
            }                  
    }
    
    /**
     *
     */
    @Override
    public synchronized void flightArrived() throws RemoteException{
        try {
            FileWriter fw = new FileWriter(Parameters.FILENAME, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(String.format("Flight %1d: arrived.",this.flightNum, this.inPlane));
            bw.newLine();
            bw.close();
            fw.close();
         } catch (IOException ex) {
            System.exit(1);
         }        
    }
    
    /**
     *
     */
    @Override
    public synchronized void flyingBack() throws RemoteException{
        try {
            FileWriter fw = new FileWriter(Parameters.FILENAME, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(String.format("Flight %1d: returning.",this.flightNum, this.inPlane));
            bw.newLine();
            bw.close();
            fw.close();
         } catch (IOException ex) {
            System.exit(1);
         }        
    }
    
    /**
     *
     * @param flights
     */
    @Override
    public synchronized void sumUp(int [] flights) throws RemoteException{
        try {
            FileWriter fw = new FileWriter(Parameters.FILENAME, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write("Airlift sum up:");
            for(int i =0; i< flights.length;i++){
                bw.newLine();
                bw.write(String.format("Flight %1d transported: %1d passengers",i+1, flights[i] ));
            }
            bw.write(".");
            
            bw.newLine();
            bw.close();
            fw.close();
         } catch (IOException ex) {
            System.exit(1);
         }        
    }
    @Override
    public  synchronized void finish()  throws RemoteException{
        RepositoryMain.serviceEnd = true;
    }

   
    
}