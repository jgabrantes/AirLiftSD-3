/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository.MainProgram;

import EntitiesState.PilotState;
import EntitiesState.PassengerState;
import EntitiesState.HostessState;
import Communications.ServerCom;
import Repository.Proxies.RepositoryProxy;
import Repository.Proxies.ServiceProvider;
import java.net.SocketTimeoutException;

/**
 *
 * @author jgabrantes
 */
public class RepositoryMain {
    
    public static boolean serviceEnd = false;
    
    
    public static void main(String [] args){
        
        /**
         * Communication channels
         */
        ServerCom sCom, sComi;
        ServiceProvider serviceProv;
        
        /**
         * Initialization of parameters
         */
        
        int[] passengerIds = new int[Parameters.N_PASSENGERS];
        PassengerState[] passengerState = new PassengerState[Parameters.N_PASSENGERS];
        PilotState pilotState = PilotState.AT_TRANFER_GATE;
        HostessState hostessState = HostessState.WAIT_FOR_FLIGHT;
        
        for(int i = 0; i<Parameters.N_PASSENGERS;i++){
            passengerIds[i] = i;
            passengerState[i] = PassengerState.GOING_TO_AIRPORT;
        }
        
        /**
         * Shared region and proxy initialization
         */
        Repository repo = new Repository(passengerIds, pilotState, hostessState, passengerState);
        repo.initLogging();
        RepositoryProxy repoProxy = new RepositoryProxy(repo);
        
        /**
         * Start listening at the communication channel
         */
        System.out.println("-------------------------------REPOSITORY--------------------------");
        sCom = new ServerCom(Parameters.REPOSITORY_PORT);
        sCom.start();
        
        /**
         * while the service is not terminated, accept connections and redirects them to de Service Provider
         */
        
        while(!serviceEnd){
            try{
                sComi = sCom.accept();
                serviceProv = new ServiceProvider(sComi, repoProxy);
                serviceProv.start();
            }catch(SocketTimeoutException e){
            }
           
        }
        System.out.print("Communication channel closed");
    }
}
