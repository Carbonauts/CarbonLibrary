/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team1829.library;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Carbonaut's own Solenoid object, with added constructors to invert 'get()'
 * and 'set()'
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class CarbonSolenoid extends Solenoid 
{    
    private boolean inverted = false;
    
    /**
     * Creates a CarbonSolenoid identical to Solenoid.
     * @param channel The PWM channel of this CarbonSolenoid.
     */
    public CarbonSolenoid(int channel) 
    {
        super(channel);
    }
    
    /**
     * Creates a CarbonSolenoid identical to Solenoid.
     * @param moduleNumber The solenoid module of this CarbonSolenoid.
     * @param channel The PWM channel of this CarbonSolenoid.
     */
    public CarbonSolenoid(int moduleNumber, int channel) 
    {
        super(moduleNumber, channel);
    }
    
    /**
     * Creates a CarbonSolenoid that acts like a Solenoid, but
     * may be inverted based on the 'inverted' parameter.
     * @param channel The PWM channel of this CarbonSolenoid.
     * @param inverted Whether this solenoid is inverted.
     */
    public CarbonSolenoid(int channel, boolean inverted) 
    {
        super(channel);
        this.inverted = inverted;
    }
    
    /**
     * Creates a CarbonSolenoid that acts like a Solenoid, but
     * may be inverted based on the 'inverted' parameter.
     * @param moduleNumber The Solenoid module of this CarbonSolenoid.
     * @param channel The PWM channel of this CarbonSolenoid.
     * @param inverted Whether this solenoid is inverted.
     */
    public CarbonSolenoid(int moduleNumber, int channel, boolean inverted) 
    {
        super(moduleNumber, channel);
        this.inverted = inverted;
    }
    
    /**
     * Overrides Solenoid's set(), but passes the opposite value
     * if this CarbonSolenoid is inverted.
     */
    public void set(boolean on) 
    {
    	super.set(inverted ? !on : on);
    }
    
    public boolean get() 
    {
    	return inverted ? !super.get() : super.get();
    }   
    
    public void toggleInverted() 
    {
        inverted = !inverted;
    }
    
    public void setIverted(boolean inverted) 
    {
        this.inverted = inverted;
    }
    
    public boolean getInverted() 
    {
        return inverted;
    }
}
