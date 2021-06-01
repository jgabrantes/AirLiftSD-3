/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DepartureAirport.MainProgram;
import static DepartureAirport.MainProgram.Parameters.*;
import java.net.SocketTimeoutException;

/**
 * Departure Airport main 
 * @author jgabrantes
 */
public class DepartureAirportMain {
    /**
     * Check if service must terminate
     */
    public static boolean serviceEnd = false;
    
    /**
     * Departure Airport main thread
     */
    public static void main(String [] args){
        /**
         * Communication channels
         */
         
        
      
        
        DepartureAirport depAirport = new DepartureAirport();
        
       
        
        /**
         * Start listening on the communication channel
         */
        System.out.println("-------------------------------DEPARTURE AIRPORT --------------------------");
       
        
        /**
         * while service not finished , accept connections and redirect them to the Service Provider
         */
        
        while(!serviceEnd){
            
          
            
        }
        
    }

    
}
