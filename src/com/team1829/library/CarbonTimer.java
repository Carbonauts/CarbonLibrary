/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1829.library;

/**
 *
 * @author Greg Armstrong
 */
public class CarbonTimer {
    
    private long endTime;
 
    public CarbonTimer(long time) {
        endTime = System.currentTimeMillis() + time;
    }
    
    public void reset(long time) {
        endTime = System.currentTimeMillis() + time;
    }
    
    public boolean isDone() {
        return System.currentTimeMillis() >= endTime;
    }
}
