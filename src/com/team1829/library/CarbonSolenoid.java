/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team1829.library;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Carbonaut's own Solenoid object, with added constructors to invert 'get()'
 * and 'set()'
 * @author Nick
 */
public class CarbonSolenoid extends Solenoid {
    
    private boolean mInverted = false;
    
    public CarbonSolenoid(int channel) {
        super(channel);
    }
    
    public CarbonSolenoid(int moduleNumber, int channel) {
        super(moduleNumber, channel);
    }
    
    public CarbonSolenoid(int channel, boolean inverted) {
        super(channel);
        mInverted = inverted;
    }
    
    public CarbonSolenoid(int moduleNumber, int channel, boolean inverted) {
        super(moduleNumber, channel);
        mInverted = inverted;
    }
    
    public void set(boolean on) {
        if(mInverted) {
            super.set(!on);
        } else {
            super.set(on);
        }
    }
    
    public boolean get() {
        if(mInverted) {
            return !super.get();
        }
        return super.get();
    }   
    
    public void toggleInverted() {
        mInverted = !mInverted;
    }
    
    public void setIverted(boolean inverted) {
        mInverted = inverted;
    }
    
    public boolean getInverted() {
        return mInverted;
    }
}
