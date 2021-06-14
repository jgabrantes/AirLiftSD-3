/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DestinationAirport.EntitiesState;

/**
 *
 * @author jgabrantes
 */
public enum HostessState {

    /**
     *
     */
    WAIT_FOR_FLIGHT ("WTFL"),

    /**
     *
     */
    WAIT_FOR_PASSENGER ("WTPS"),

    /**
     *
     */
    CHECK_PASSENGER ("CKPS"),

    /**
     *
     */
    READY_TO_FLY ("RDTF");

    private final String state;

    private HostessState(String description){
        this.state = description;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return this.state;
    }
}
