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
public class CarbonDigitalInput extends DigitalInput {
    
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
    public CarbonDigitalInput(int channel) {
        super(channel);
    }
    
    /**
     * Constructs a CarbonDigitalInput that may be inverted based on the 'inv'
     * parameter.  An inverted CarbonDigitalInput will return the opposite
     * reading when calling 'get()' than a DigitalInput.
     * @param channel The DIO channel that this digital input is on.
     * @param inv True to invert the reading of this input, false to stay the same.
     */
    public CarbonDigitalInput(int channel, boolean inv) {
        super(channel);
        this.inverted = inv;
    }
    
    /**
     * Overrides DigitalInput's 'get()' method and simply applies an inversion
     * based on object properties set in the constructor or mutator methods.
     */
    public boolean get() {
        if(inverted) {
            return !super.get();
        }
        return super.get();
    }
    
    /**
     * Sets the inversion property of this CarbonDigitalInput to 'inv'.
     * @param inv True to invert the reading of this input, false otherwise.
     */
    public void setInverted(boolean inv)
    {
    	inverted = inv;
    }
    
    /**
     * Toggles the inversion property of this CarbonDigitalInput.
     */
    public void toggleInverted()
    {
    	inverted = !inverted;
    }
}