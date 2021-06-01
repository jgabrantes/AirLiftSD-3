package Hostess.MainProgram;


import Hostess.Entities.Hostess;
import  static Hostess.MainProgram.Parameters.*;
import Hostess.Stubs.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jgabrantes
 * 
 * Hostess Launcher
 */
public class HostessMain {
    /**
     * Hostess main thread
     * @param args 
     */
    public static void main(String [] args){
        
        DepartureAirportStub depAirport = new DepartureAirportStub(DEPARTURE_AIRPORT_HOST_NAME, DEPARTURE_AIRPORT_PORT);
        PlaneStub plane = new PlaneStub(PLANE_HOST_NAME, PLANE_PORT);
        
        Hostess hostess = new Hostess(0, depAirport, plane);
        System.out.println("-------------------------------HOSTESS --------------------------");
        hostess.start();
        try{
            hostess.join();
        } catch(InterruptedException e){}
    }
}
