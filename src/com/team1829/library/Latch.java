/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team1829.library;

/**
 * Boolean latch function directly copied from Team 254's FRC-2013 code 
 * @author Nick
 */
public class Latch {
    private boolean lastBool;
    
    public Latch() {
        lastBool = true;
    }
    
    public boolean onTrue(boolean nowBool) {
        boolean result = nowBool && !lastBool;
        lastBool = nowBool;
        return result;
    }
    
    public boolean onFalse(boolean nowBool) {
        return onTrue(!nowBool);
    }
    
    public boolean onChange(boolean nowBool) {
        return onTrue(nowBool) || onFalse(nowBool);
    }
}