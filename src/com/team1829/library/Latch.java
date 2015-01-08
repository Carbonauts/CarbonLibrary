/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team1829.library;

/**
 * Boolean latch function.
 * 
 * Inspired by Team 254's FRC-2013 code.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Latch 
{
    private boolean lastBool;
    
    /**
     * Constructs a new latch.
     */
    public Latch() 
    {
        lastBool = true;
    }
    
    /**
     * Keeps track of the parameter and returns true only
     * if the last onTrue() call was false and this onTrue()
     * call is true.
     * @param nowBool The current status of the latching input.
     * @return True when the parameter CHANGES from false to true.
     */
    public boolean onTrue(boolean nowBool) 
    {
        boolean result = nowBool && !lastBool;
        lastBool = nowBool;
        return result;
    }
    
    /**
     * Keeps track of the parameter and returns true only
     * if the last onFalse() call was true and this onFalse()
     * call is false.
     * @param nowBool The current status of the latching input.
     * @return True when the parameter CHANGES from true to false.
     */
    public boolean onFalse(boolean nowBool) 
    {
        return onTrue(!nowBool);
    }
    
    /**
     * Keeps track of the parameter and returns true only
     * if the last onChange() is different from this onChange() call.
     * @param nowBool The current status of the latching input.
     * @return True when the parameter changes.
     */
    public boolean onChange(boolean nowBool) 
    {
        return onTrue(nowBool) || onFalse(nowBool);
    }
}