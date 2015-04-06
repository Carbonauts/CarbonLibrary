/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team1829.library;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Custom DigitalInput object that allows the inversion of the result
 * of the 'get' method by overriding it.  
 * @author Nick
 */
public class CarbonDigitalInput extends DigitalInput 
{
	/**
	 * Object property that specifies whether this CarbonDigitalInput is
	 * inverted or not.
	 */
    private boolean inverted = false;
    
    /**
     * Constructs a CarbonDigitalInput that acts identically to DigitalInput,
     * defaulting to NO inversion.
     * @param channel The DIO channel that this digital input is on.
     */
    public CarbonDigitalInput(int channel) 
    {
        super(channel);
    }
    
    /**
     * Constructs a CarbonDigitalInput that may be inverted based on the 'inv'
     * parameter.  An inverted CarbonDigitalInput will return the opposite
     * reading when calling 'get()' than a DigitalInput.
     * @param channel The DIO channel that this digital input is on.
     * @param inverted True to invert the reading of this input, false to stay the same.
     */
    public CarbonDigitalInput(int channel, boolean inverted) 
    {
        super(channel);
        this.inverted = inverted;
    }
    
    /**
     * Overrides DigitalInput's 'get()' method and simply applies an inversion
     * based on object properties set in the constructor or mutator methods.
     */
    public boolean get() 
    {
    	return this.inverted ? !super.get() : super.get();
    }
    
    /**
     * Sets the inversion property of this CarbonDigitalInput.
     * @param inverted True to invert the reading of this input, false otherwise.
     */
    public void setInverted(boolean inverted)
    {
    	this.inverted = inverted;
    }
    
    /**
     * Returns whether this object currently inverts values.
     * @return Whether this object currently inverts values.
     */
    public boolean getInverted()
    {
    	return this.inverted;
    }
    
    /**
     * Toggles the inversion property of this CarbonDigitalInput.
     */
    public void toggleInverted()
    {
    	this.inverted = !this.inverted;
    }
}