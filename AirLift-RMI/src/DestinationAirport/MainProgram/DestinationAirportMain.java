/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DestinationAirport.MainProgram;

import Communications.ServerCom;
import static DestinationAirport.MainProgram.Parameters.*;

import DestinationAirport.Proxies.DestinationAirportProxy;

import DestinationAirport.Proxies.ServiceProvider;

import DestinationAirport.SharedRegions.DestinationAirport;

import DestinationAirport.Stubs.RepositoryStub;
import java.net.SocketTimeoutException;

/**
 * Plane main 
 * @author jgabrantes
 */
public class DestinationAirportMain {
    /**
     * Check if service must terminate
     */
    public static boolean serviceEnd = false;
    
    /**
     * Plane main thread
     */
    public static void main(String [] args){
        /**
         * Communication channels
         */
        ServerCom sCom , sComi;
        ServiceProvider serviceProv;
        
        RepositoryStub repo = new RepositoryStub(REPOSITORY_HOST_NAME, REPOSITORY_PORT);
        
        DestinationAirport destAirport = new DestinationAirport(repo);
        
        DestinationAirportProxy destAirportProxy = new DestinationAirportProxy(destAirport);
        System.out.println("-------------------------------DESTINATION AIRPORT --------------------------");
        /**
         * Start listening on the communication channel
         */
        sCom = new ServerCom(DESTINATION_AIRPORT_PORT);
        sCom.start();
        
        /**
         * while service not finished , accept connections and redirect them to the Service Provider
         */
        while(!serviceEnd){
            try{
                sComi = sCom.accept();
                serviceProv = new ServiceProvider(sComi, destAirportProxy);
                serviceProv.start();
            }catch (SocketTimeoutException e){
                
            }
        }
        System.out.print("Communication channel closed");
    }
}
