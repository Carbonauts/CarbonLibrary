/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team1829.library;

/**
 * This class provides a means to set a target value to slowly ramp up or down
 * to.  The ramp speed is dependent upon the delay between updates and the
 * magnitude of the steps at each interval.  The ramp will slowly increment
 * from it's current value to the target value.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class CarbonRamp {
    
	/**
	 * Ramps are enabled by default.
	 */
	public static final boolean RAMPS_DEFAULT_ENABLED = true;
	
	/**
	 * Default ramp step size is how much the ramp will step
	 * each iteration of tick().
	 */
	public static final double RAMP_DEFAULT_STEPSIZE = 0.05;
	
	/**
	 * Default ramp step time is how long (in milliseconds)
	 * to wait between steps.
	 */
    public static final long RAMP_DEFAULT_STEPTIME = 10;  
	
    private double stepSize;
    private long stepTime;
    private double target = 0;
    private double currentOutput = 0;
    private boolean enabled;
    
    private CarbonTimer delayTimer;
    
    /**
     * Creates a CarbonRamp with an initial target of 0, with default
     * step size and step time.
     */
    public CarbonRamp() 
    {
        this(0);
    }
    
    /**
     * Creates a CarbonRamp with a custom initial target, with default
     * step size and step time.
     * @param target The initial target of this CarbonRamp.
     */
    public CarbonRamp(double target) 
    {
        this(target, RAMP_DEFAULT_STEPSIZE, RAMP_DEFAULT_STEPTIME);
    }
    
    /**
     * Creates a CarbonRamp with a custom initial target, step size, and
     * step time.
     * @param target The initial target of this CarbonRamp.
     * @param stepSize The initial step size of this CarbonRamp.
     * @param stepTime The initial step time of this CarbonRamp.
     */
    public CarbonRamp(double target, double stepSize, long stepTime) 
    {
        this.stepSize = stepSize;
        this.stepTime = stepTime;
        this.target = target;
        
        delayTimer = new CarbonTimer(0);
        enabled = RAMPS_DEFAULT_ENABLED;
    }
    
    /**
     * tick() must be run continuously in an execution loop (see CarbonRampCommand).
     * The faster tick() is run the better, since that resolution will affect how
     * closely the actual step times reflect the set step times.  tick() will not 
     * freeze the execution flow by waiting for the step time, rather it will check
     * if the step time has passed since the last step.  If not it skips the execution,
     * if so then it ticks the output by one step size toward the target, resets the
     * step time, and continues.
     */
    public void tick() 
    {
        if(enabled) 
        {
            if(delayTimer.isDone()) 
            {
                delayTimer.reset(stepTime);
                boolean isUp = (currentOutput < target);
                double step = isUp ? stepSize : -stepSize;

                if((isUp && (currentOutput + step >= target)) || 
                        (!isUp && (currentOutput + step <= target))) 
                {
                    currentOutput = target;
                } 
                else 
                {
                    currentOutput += step;
                }
            }
        /*
         * If the ramps are not enabled, simply set the output directly
         * to the target, don't worry about ramping to it.
         */
        } 
        else 
        {
            currentOutput = target;
        }
    }
    
    /**
     * Returns the current value of this ramp.
     * @return The current value of this ramp.
     */
    public double getOutput() 
    {
        return currentOutput;
    }
    
    /**
     * Sets the step time.
     * @param time The new step time.
     */
    public void setStepTime(long time) 
    {
        if(time > 0) 
        {
            this.stepTime = time;
        }
    }
    
    /**
     * Returns the current step time.
     * @return The current step time.
     */
    public long getStepTime() 
    {
        return this.stepTime;
    }
    
    /**
     * Sets the step size.
     * @param size The new step size.
     */
    public void setStepSize(double size) 
    {
        if(size >= 0) 
        {
            this.stepSize = size;
        } 
        else
        {
            this.stepSize = -size;
        }
    }
    
    /**
     * Returns the current step size.
     * @return The current step size.
     */
    public double getStepSize() 
    {
        return this.stepSize;
    }
    
    /**
     * Sets a new target for this ramp to increment toward.
     * @param target Target for the ramp to approach.
     */
    public void setTarget(double target) 
    {
        this.target = target;
    }
    
    /**
     * Returns the current target.
     * @return The current target.
     */
    public double getTarget() 
    {
        return this.target;
    }
    
    /**
     * Sets the enabled state of this ramp.
     * @param enabled Whether this ramp is enabled.
     */
    public void setEnabled(boolean enabled) 
    {
        this.enabled = enabled;
    }
    
    /**
     * Returns the enabled status of this ramp.
     * @return The enabled status of this ramp.
     */
    public boolean isEnabled() 
    {
        return this.enabled;
    }
    
    /**
     * Sets both the output and the target to 0, nullifying all
     * action in this ramp.  To reactivate, simply set a new
     * target for the ramp.
     */
    public void reset() 
    {
        this.target = 0;
        this.currentOutput = 0;
    }
}