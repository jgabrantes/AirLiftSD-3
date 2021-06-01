/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plane.MainProgram;

import Communications.ServerCom;
import static Plane.MainProgram.Parameters.*;

import Plane.Proxies.*;

import Plane.Stubs.RepositoryStub;
import java.net.SocketTimeoutException;

/**
 * Plane main 
 * @author jgabrantes
 */
public class PlaneMain {
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
        
        Plane plane = new Plane(repo);
        
        PlaneProxy planeProxy = new PlaneProxy(plane);
        
        /**
         * Start listening on the communication channel
         */
        sCom = new ServerCom(PLANE_PORT);
        System.out.println("-------------------------------PLANE--------------------------");
        sCom.start();
        
        /**
         * while service not finished , accept connections and redirect them to the Service Provider
         */
        while(!serviceEnd){
            try{
                sComi = sCom.accept();
                serviceProv = new ServiceProvider(sComi, planeProxy);
                serviceProv.start();
            }catch (SocketTimeoutException e){
                
            }
        }
    }
}
