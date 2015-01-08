/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team1829.library;

import com.team1829.library.ICarbonRampable;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class acts as an execution thread for a ramp.  In the case that a new
 * value is set (for example, a new position is wanted for a subsystem) but the
 * condition which set the new value is not part of a frequented loop (if the
 * new value is only set once in a blue moon), then this class will take the
 * ramp and use the Command lifecycle to keep the ramp's timing intact.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class CarbonRampCommand extends Command 
{
    private final CarbonRamp ramp;
    private ICarbonRampable talon;
    
    private boolean finished = false;
    
    public CarbonRampCommand(CarbonRamp ramp) 
    {
        this.ramp = ramp;
    }
    
    public CarbonRampCommand(CarbonRamp ramp, ICarbonRampable talon) 
    {
        this.ramp = ramp;
        this.talon = talon;
    }
    
    protected void initialize() 
    {
        
    }

    protected void execute() 
    {
        ramp.tick();
        if(talon != null) 
        {
            talon.hardSet(ramp.getOutput());
        }
    }

    protected boolean isFinished() 
    {
        return finished;
    }

    protected void end() 
    {
        
    }

    protected void interrupted() 
    {
        
    }
    
    public void setFinished(boolean finished) 
    {
        this.finished = finished;
    }
}