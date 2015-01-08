package com.team1829.library;

import com.team1829.library.CarbonUI.ControlType;

/**
 * A Control represents a source of data from a controller.
 * Each Control has a USB port which identifies which controller 
 * the control data originates from, and a controller ID which tells
 * which specific button, thumbstick, slider, or other physical 
 * input from that controller in question.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Control 
{	
	private String name;
	private ControlType type;
    private int port;
    private int id;
    
    /**
     * @param port The USB port of the controller that this piece of
     * control data is originating from.
     * @param ID The ID of the physical input on the controller that
     * this piece of data is originating from.
     */
    public Control(String name, ControlType type, int port, int id) 
    {
    	this.name = name;
    	this.type = type;
        this.port = port;
        this.id = id;
    }
    
    /**
     * Sets the USB port and the ID of this control.
     * @param port The USB port of this control.
     * @param ID The ID of this control.
     */
    public void setData(ControlType type, int port, int id) 
    {
    	this.type = type;
        this.port = port;
        this.id = id;
    }

    /**
     * @return The Name of this control.
     */
    public String getName()
    {
    	return name;
    }
    
    /**
     * @param name The Name of this control.
     */
    public void setName(String name)
    {
    	this.name = name;
    }
    
    /**
     * @return The USB port of the control this object
     * represents.
     */
    public int getPort() 
    {
        return port;
    }

    /**
     * @param port The new USB port to which this control is
     * assigned.
     */
    public void setPort(int port) 
    {
        this.port = port;
    }

    /**
     * @return The ID of the control this object represents.
     */
    public int getID() 
    {
        return id;
    }

    /**
     * @param ID The new ID of this control.
     */
    public void setID(int id) 
    {
        this.id = id;
    }
    
    /**
     * @return The ControlType of this control.  Either BUTTON or AXIS.
     */
    public ControlType getControlType()
    {
    	return this.type;
    }
    
    /**
     * @param type The new ControlType of this control.  Either BUTTON or AXIS.
     */
    public void setControlType(ControlType type)
    {
    	this.type = type;
    }
}