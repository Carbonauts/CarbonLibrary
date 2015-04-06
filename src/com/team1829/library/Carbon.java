package com.team1829.library;

/**
 * Houses static methods that are useful but don't necessarily
 * need their own class.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Carbon 
{
	public static double map(double value, double oldMin, double oldMax, double newMin, double newMax)
	{
		double mInput = value;
		double x1 = oldMin;
		double x2 = oldMax;
		double y1 = newMin;
		double y2 = newMax;

		return ((mInput * ((y2-y1)/(x2-x1))) + (y2 - (((y2-y1)/(x2-x1)) * x2)));
	}
	
	public static int map(int input, int oldMin, int oldMax, int newMin, int newMax) 
	{
		return (int) map((double) input, (double) oldMin, (double) oldMax, (double) newMin, (double) newMax);
	}
}