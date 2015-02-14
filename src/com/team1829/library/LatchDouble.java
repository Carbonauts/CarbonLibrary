package com.team1829.library;

/**
 * Toolset for detecting when frequently-read double values
 * exceed (or... incede?) certain thresholds.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class LatchDouble 
{
	private boolean initialized;
	private double lastValue;
	
	public LatchDouble()
	{
		initialized = false;
		lastValue = 0.0;
	}
	
	/**
	 * Triggers if the old value was outside of the range
	 * (-limit, limit) AND the new value is within it.
	 * @param value The new value to compare.
	 * @param limit The extent of the range.
	 * @return True if the new value has entered the range.
	 */
	public boolean onEnterThreshold(double value, double limit)
	{
		return onEnterThreshold(value, limit, -limit);
	}
	
	/**
	 * Triggers if the old value was outside the range (downLimit,
	 * uplimit) AND the new value is within it.
	 * @param value The new value to compare.
	 * @param upLimit The upper extend of the range.
	 * @param downLimit The lower extent of the range.
	 * @return True if the new value has entered the range.
	 */
	public boolean onEnterThreshold(double value, double upLimit, double downLimit)
	{
		double thisValue = lastValue;
		lastValue = value;
		if(!initialized)
		{
			initialized = true;
			return false;
		}
		
		if(upLimit < downLimit)
		{
			//Swap up and down limits
			upLimit += downLimit;
			downLimit = upLimit - downLimit;
			upLimit -= downLimit;
		}
		
		if(thisValue < downLimit || thisValue > upLimit)
		{
			if(value > downLimit && value < upLimit)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Triggers when the new value leaves the threshold bounded
	 * by (-limit, limit).
	 * @param value The new value to analyze.
	 * @param limit The boundary of the threshold.
	 * @return True if the value has left the threshold.
	 */
	public boolean onExitThreshold(double value, double limit)
	{
		return onExitThreshold(value, limit, -limit);
	}
	
	/**
	 * Triggers when the new value leaves the threshold bounded
	 * by (downLimit, upLimit).
	 * @param value The new value to analyze.
	 * @param upLimit The upper boundary of the threshold.
	 * @param downLimit The lower boundary of the threshold.
	 * @return True if the value has left the threshold.
	 */
	public boolean onExitThreshold(double value, double upLimit, double downLimit)
	{
		double oldValue = lastValue;
		lastValue = value;
		if(!initialized)
		{
			initialized = true;
			return false;
		}
		
		if(upLimit < downLimit)
		{
			//Swap up and down limits
			upLimit += downLimit;
			downLimit = upLimit - downLimit;
			upLimit -= downLimit;
		}
		
		if(oldValue > downLimit && oldValue < upLimit)
		{
			if(value < downLimit || value > upLimit)
			{
				return true;
			}
		}
		return false;
	}
}