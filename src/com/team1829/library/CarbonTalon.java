/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1829.library;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * Our own implementation of the Talon supports the use of a CarbonRamp so that
 * a set() call can alternately use setRamp() so that the Talon will slowly
 * approach the set value rather than abruptly jump to it.
 * @author Greg Armstrong, Team 1839 Carbonauts Mentor
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class CarbonTalon extends Talon implements ICarbonRampable 
{	
    private final CarbonRamp ramp;
    private CarbonRampCommand rampCommand;
    
    public CarbonTalon(int channel) 
    {
        this(channel, CarbonRamp.RAMP_DEFAULT_STEPSIZE, CarbonRamp.RAMP_DEFAULT_STEPTIME);
    }
    
    public CarbonTalon(int channel, double stepSize, long stepTime) 
    {
        super(channel);
        ramp = new CarbonRamp(0, stepSize, stepTime);
        rampCommand = new CarbonRampCommand(ramp, this);
        Scheduler.getInstance().add(rampCommand);
    }

    public double getStepSize() 
    {
        return ramp.getStepSize();
    }
    
    public void setStepSize(double stepSize) 
    {
        ramp.setStepSize(stepSize);
    }
    
    public long getStepTime() 
    {
        return ramp.getStepTime();
    }
    
    public void setStepTime(long stepTime) 
    {
        ramp.setStepTime(stepTime);
    }
    
    public void setDefaultStep() 
    {
        ramp.setStepSize(CarbonRamp.RAMP_DEFAULT_STEPSIZE);
        ramp.setStepTime(CarbonRamp.RAMP_DEFAULT_STEPTIME);
    }
    
    public void setRamp(double setPoint) 
    {
        if(!rampCommand.isRunning()) 
        {
            rampCommand = new CarbonRampCommand(ramp, this);
            Scheduler.getInstance().add(rampCommand);
        }
        ramp.setTarget(setPoint);
    }
    
    public double getTargetSpeed() 
    {
        return ramp.getTarget();
    }
    
    public void stopMotor() 
    {
        ramp.setTarget(0.0);
    }
    
    public void reset() 
    {
        ramp.reset();
    }
    
    public void setRampEnabled(boolean enabled) 
    {
        ramp.setEnabled(enabled);
    }
    
    public boolean isRampEnabled() 
    {
        return ramp.isEnabled();
    }
    
    public void hardSet(double setpoint) 
    {
        super.set(setpoint);
    }
    
    public void hardStopMotor() 
    {
        ramp.reset();
        //rampCommand.setFinished(true);
        super.stopMotor();
    }
}