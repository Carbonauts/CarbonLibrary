/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1829.library;

/**
 * CarbonTimer will use the system time to detect if a certain
 * amount of time has elapsed.  This is a non-freezing timer
 * meaning that it will not hold program execution until time
 * is up, rather it will change the status of isDone().  To
 * keep track of your CarbonTimer, construct it or reset() it
 * to the desired countdown time, then call isDone() continuously
 * to trigger your desired action, as isDone will return true
 * starting on the first call after time is up.
 * @author Greg Armstrong, Team 1829 Carbonauts Mentor
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class CarbonTimer 
{    
    private long endTime;

    /**
     * Creates a CarbonTimer that will trigger in 'time' milliseconds.
     * Triggering is indicated with the isDone() method.
     * @param time The number of milliseconds to time.
     */
    public CarbonTimer(long time) 
    {
        endTime = System.currentTimeMillis() + time;
    }
    
    /**
     * Stops the previous countdown and starts a new one for 'time' milliseconds.
     * @param time The new countdown time in milliseconds.
     */
    public void reset(long time) 
    {
        endTime = System.currentTimeMillis() + time;
    }
    
    /**
     * The indicator method used to detect if this timer is done.
     * @return True if the timer is done, false otherwise.
     */
    public boolean isDone() 
    {
        return System.currentTimeMillis() >= endTime;
    }
}
