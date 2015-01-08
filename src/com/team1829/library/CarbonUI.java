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
 * For example, for driving, typically Joystick Axes are used for either a Tank
 * Drive or Arcade Drive.  To assign controls for each, you would define methods
 * such as "getArcadeXAxis()" and "getArcadeYAxis()".  These methods would
 * return the raw joystick value of the axis which was assigned to each of those
 * actions, regardless of which physical thumb-stick was assigned.  These
 * assignments are also dynamic, and they may be changed at any time.
 * 
 * The way to assign controller resources to an action is to create a PORT
 * variable and an ID variable for the action inside the UIConfig class (which
 * is a sub-class inside of CarbonUI).  These PORT and ID variables for the
 * action need encapsulation (getters and setters).  Then, a method is needed
 * in CarbonUI which has a return type which is the same as the value being 
 * retrieved from the USB controller.  The name of the method should reflect the
 * action which is using the resource.  Note: If more than one action all refer
 * to the same USB controller resource, they should both have unique action
 * methods in CarbonUI and unique ID and PORT variables in the UIConfig.  This
 * way, a chance in the resource for one action will not unintentionally remap
 * the resources for another action.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class CarbonUI {
	
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
     */
    public void addControl(String name, ControlType type, int port, int id)
    {
    	for(Control c : controls)
    	{
    		if(c.getName().equalsIgnoreCase(name))
    		{
    			System.out.println("Control " + name + " already exists.");
    			return;
    		}
    	}
    	controls.add(new Control(name, type, port, id));
    }
    
    /**
     * Removes a control from the CarbonUI.
     * @param name The name of the control to remove.
     * @return True if the process completed successfully, i.e. a control with the
     * name 'name' was found and removed.  False if no matching control was found
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