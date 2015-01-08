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
 * a ControlType.BUTTON, then the data for that will only be retrieveable using
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
	public enum ControlType {
		AXIS,
		BUTTON
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
    	for(Control c : controls)
    	{
    		if(c.getName().equalsIgnoreCase(name))
    		{
    			System.out.println("Control " + name + " already exists.");
    			return false;
    		}
    		
    		if(c.getPort() == port && c.getID() == id)
    		{
    			System.out.println("A control already exists with port " + port +
    					"and ID " + id + ".");
    			return false;
    		}
    	}
    	controls.add(new Control(name, type, port, id));
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
    		if(c.getName().equalsIgnoreCase(control.getName()))
    		{
    			System.out.println("Control " + control.getName() + " already exists.");
    			return false;
    		}
    		
    		if(c.getPort() == control.getPort() && c.getID() == control.getID())
    		{
    			System.out.println("A control already exists with port " + control.getPort() + 
    					"and ID " + control.getID() + ".");
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
    public boolean getButtonData(String name)
    {
    	for(Control c : controls)
    	{
    		if(c.getName().equalsIgnoreCase(name))
    		{
    			if(c.getControlType() == ControlType.BUTTON)
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
    			if(c.getControlType() == ControlType.AXIS)
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
}