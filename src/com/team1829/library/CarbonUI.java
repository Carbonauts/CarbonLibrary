/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team1829.library;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the core for all of the Operator needs pertaining to any
 * USB controllers.  The structure here is such that any action that the robot
 * needs to perform based on Operator input may be defined by it's name, and
 * then the resource which controls it may be separately manipulated.
 * 
 * Controls can be added to the Carbon User Interface (UI) by calling
 * addControl().  Specify the name, type, port, and ID of the control, then
 * retrieve the data later by using the name as a key in the getButtonData()
 * or getAxisData() methods.  Note, if a Control's type is specified as, say,
 * a ControlType.BUTTON, then the data for that will only be retrievable using
 * the getButtonData() method, and vice versa for ControlType.AXIS and 
 * getAxisData().
 * 
 * Different instances of CarbonUI may be instantiated and stored to allow
 * different control configurations to be accessed.  This can be useful to assign
 * different controller mappings 
 * 
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class CarbonUI 
{
	public enum ControlType
	{
		Axis,
		Button
	}
	
	/**
	 * The default return value for a button that does not exist.
	 */
	public static final boolean BUTTON_NULL = false;
	
	/**
	 * The default return value for an axis that does not exist.
	 */
	public static final double AXIS_NULL = 0.0;
    
    /**
     * Dynamic list that will keep track of all UIElements that are added to the
     * system.
     */
    private List<Control> controls;
    
    /**
     * Creates a new CarbonUI object.  Each CarbonUI can have different configurations,
     * so different users could instantiate their own CarbonUI's and add their own
     * control preferences into them.
     */
    public CarbonUI() 
    {
        controls = new ArrayList<Control>();
    }
    
    /**
     * Adds a control object to the CarbonUI.
     * @param name The name of the control object.
     * @param port The USB port the control object is on.
     * @param id The ID of the control object.
     * @return true if the control was added successfully, false
     * if a control by the same name already exists or if another
     * control references the exact port and id already.
     */
    public boolean addControl(String name, ControlType type, int port, int id)
    {
    	Control control = new Control(name, type, port, id);
    	for(Control c : controls)
    	{
    		if(c.equals(control))
    		{
    			return false;
    		}
    	}
    	controls.add(control);
    	return true;
    }
    
    /**
     * Adds a control object to the CarbonUI.
     * @param control The control to add to this CarbonUI.
     * @return true if the control was added successfully, false
     * if a control by the same name already exists or if another
     * control references the exact port and id already.
     */
    public boolean addControl(Control control)
    {
    	for(Control c : controls)
    	{
    		if(control.equals(c))
    		{
    			return false;
    		}
    	}
    	controls.add(control);
    	return true;
    }
    
    /**
     * Removes a control from the CarbonUI.
     * @param name The name of the control to remove.
     * @return true if the process completed successfully, i.e. a control with the
     * name 'name' was found and removed.  false if no matching control was found
     * and removed.
     */
    public boolean removeControl(String name)
    {
    	for(int i = 0; i < controls.size(); i++)
    	{
    		if(controls.get(i).getName().equalsIgnoreCase(name))
    		{
    			controls.remove(i);
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Gets the data from a button control object based on its name.
     * @param name The name of the button.
     * @return The button's status.
     */
    public boolean getButtonState(String name)
    {
    	for(Control c : controls)
    	{
    		if(c.getName().equalsIgnoreCase(name))
    		{
    			if(c.getType() == ControlType.Button)
    			{
    				return new Joystick(c.getPort()).getRawButton(c.getID());
    			}
    			else
    			{
    				System.out.println(name + " exists, but is not a Button control!");
    			}
    		}
    	}
    	return BUTTON_NULL;
    }
    
    public boolean getButtonPress(String name)
    {
    	for(Control c : controls)
    	{
    		if(c.getName().equalsIgnoreCase(name))
    		{
    			if(c.getType() == ControlType.Button)
    			{
    				return c.getLatch().onTrue(new Joystick(c.getPort()).getRawButton(c.getID()));
    			}
    			else
    			{
    				System.out.println(name + " exists, but is not a Button control!");
    			}
    		}
    	}
    	return BUTTON_NULL;
    }
    
    public boolean getButtonRelease(String name)
    {
    	for(Control c : controls)
    	{
    		if(c.getName().equalsIgnoreCase(name))
    		{
    			if(c.getType() == ControlType.Button)
    			{
    				return c.getLatch().onFalse(new Joystick(c.getPort()).getRawButton(c.getID()));
    			}
    			else
    			{
    				System.out.println(name + " exists, but is not a Button control!");
    			}
    		}
    	}
    	return BUTTON_NULL;
    }
    
    /**
     * Gets the data from an axis control object based on its name.
     * @param name The name of the axis.
     * @return The axis's status.
     */
    public double getAxisData(String name)
    {
    	for(Control c : controls)
    	{
    		if(c.getName().equalsIgnoreCase(name))
    		{
    			if(c.getType() == ControlType.Axis)
    			{
    				return new Joystick(c.getPort()).getRawAxis(c.getID());
    			}
    			else
    			{
    				System.out.println(name + " exists, but is not an Axis control!");
    			}
    		}
    	}
    	return AXIS_NULL;
    }
    
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
    	private LatchBoolean buttonLatch;
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
        	this.buttonLatch = new LatchBoolean();
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
        public ControlType getType()
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
        
        public LatchBoolean getLatch()
        {
        	return this.buttonLatch;
        }
        
        public boolean equals(Object o)
        {
        	if(Control.this == o)
        	{
        		return true;
        	}
        	
        	if(!(o instanceof Control))
        	{
        		return false;
        	}
        	
        	//Since o must be a control, cast it for more comparisons.
        	Control control = (Control) o;
        	
        	if(control.getName().equals(Control.this.getName()) &&
        			control.getPort() == Control.this.getPort() &&
        			control.getType() == Control.this.getType())
        	{
        		return true;
        	}
        	return false;
        }
    }
}