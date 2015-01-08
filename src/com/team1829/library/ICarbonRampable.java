/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team1829.library;

/**
 * Interface to allow objects to be rampable.  To be used with CarbonRampCommand,
 * and any motor controllers we'd like to use.  This expands the ramp's
 * compatibility beyond just the CarbonTalon.
 * @author Nick
 */
public interface ICarbonRampable 
{    
    public void hardSet(double setpoint);
}