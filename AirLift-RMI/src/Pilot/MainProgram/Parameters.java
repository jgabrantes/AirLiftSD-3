/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pilot.MainProgram;


import Passenger.MainProgram.*;
import java.util.Date;

/**
 *
 * @author jgabrantes
 */
public class Parameters {

    /**
     *
     */
    public static final int  N_PASSENGERS = 21;

    /**
     *
     */
    public static final int  MIN = 5;

    /**
     *
     */
    public static final int MAX = 10;

    /**
     *
     */       
    public static final String FILENAME = "AirliftRun_" + new Date().toString().replace(' ', '_') + ".txt";
    /**
     * 
     */
    public static final String REPOSITORY_HOST_NAME = "l040101-ws01.ua.pt";
    /**
     * 
     */
    public final static int REPOSITORY_PORT = 22111;
    /**
     * 
     */
    public final static String REPOSITORY_HANDLER = "RepositoryHandler";
    /**
     * 
     */
    public final static String DEPARTURE_AIRPORT_HOST_NAME = "l040101-ws02.ua.pt";
    /**
     * 
     */
    public final static int DEPARTURE_AIRPORT_PORT =22112;
    /**
     * 
     */
    public final static String DEPARTURE_AIRPORT_HANDLER = "DepartureAirportHandler";
    /**
     * 
     */
    public final static String DESTINATION_AIRPORT_HOST_NAME ="l040101-ws03.ua.pt";
    /**
     * 
     */
    public final static int DESTINATION_AIRPORT_PORT = 22113;
    /**
     * 
     */
    public final static String DESTINATION_AIRPORT_HANDLER ="DestinationAirportHandler";
    /**
     * 
     */
    public final static String PLANE_HOST_NAME = "l040101-ws04.ua.pt";
    /**
     * 
     */
    public final static int PLANE_PORT = 22114;
    /**
     * 
     */
    public final static String PLANE_HANDLER = "PlaneHandler";
    /**
     * 
     */
    public final static String REGISTRY_HOST_NAME = "l040101-ws05.ua.pt";
    /**
     *
     */
    public final static int REGISTRY_PORT = 22115;
     /**
     * 
     */
    public final static String REGISTER_HANDLER = "RegisterHandler";
    /**
     * 
     */
    public final static String SERVER_REGISTRY_HOST_NAME = "l040101-ws08.ua.pt";
    /**
     *
     */
    public final static int SERVER_REGISTRY_PORT = 22116;
    
}
