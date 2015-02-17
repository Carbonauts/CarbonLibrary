package com.team1829.library;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Analog Ultrasonic sensor implementation that includes a
 * data smoothing algorithm based on rolling averages.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class CarbonUltrasonic 
{
	public static final int DEFAULT_BUFFER_SIZE = 25;
	public static final long DEFAULT_PERIOD = 50;
	private AnalogInput ultrasonic;
	private double[] ultraBuffer;
	private double smoothed;
	private Timer controlLoop;
	
	/**
	 * Constructs a CarbonUltrasonic object with the specified analogPort,
	 * bufferSize, and cycle period.
	 * @param analogPort The analog port of the ultrasonic sensor.
	 * @param bufferSize The number of readings to average in the rolling
	 * data smoother.
	 * @param period The time between each sensor read.
	 */
	public CarbonUltrasonic(int analogPort, int bufferSize, long period)
	{
		ultrasonic = new AnalogInput(analogPort);
		ultraBuffer = new double[bufferSize];
		for(int i = 0; i < ultraBuffer.length; i++)
		{
			ultraBuffer[i] = 0;
		}
		smoothed = 0;
		controlLoop = new Timer();
		controlLoop.schedule(new SmoothingTask(), 0L, 50);
	}
	
	/**
	 * Construct a CarbonUltrasonic object at a certain analogPort
	 * and bufferSize with the default cycle period.
	 * @param analogPort The analog port of the ultrasonic sensor.
	 * @param bufferSize The number of readings to average in the
	 * rolling data smoother.
	 */
	public CarbonUltrasonic(int analogPort, int bufferSize)
	{
		this(analogPort, bufferSize, DEFAULT_PERIOD);
	}
	
	/**
	 * Construct a CarbonUltrasonic object at a certain analogPort
	 * with the default averaging buffer size and cycle period.
	 * @param analogPort The analog port the ultrasonic sensor
	 * is plugged into.
	 */
	public CarbonUltrasonic(int analogPort)
	{
		this(analogPort, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * Returns a value averaged from the past X sensor readings,
	 * where X is the initialized bufferSize.
	 * @return Current smoothed sensor value.
	 */
	public double getSmoothedValue()
	{
		return smoothed;
	}
	
	/**
	 * Returns the unaltered sensor value straight from the 
	 * ultrasonic sensor.
	 * @return Current unaltered sensor data.
	 */
	public double getRawValue()
	{
		return ultrasonic.getValue();
	}
	
	/**
	 * Task that will spawn a thread for constantly reading
	 * from the ultrasonic sensor and storing the values into
	 * an array.  The array is then averaged, causing the 
	 * output of the CarbonUltrasonic to better represent
	 * the trend of the data.
	 * @author Nick Mosher, Team 1829 Carbonauts Captain
	 */
	public class SmoothingTask extends TimerTask
	{
		private int index;
		
		public SmoothingTask()
		{
			index = 0;
		}
		
		@Override
		public void run() 
		{
			synchronized(CarbonUltrasonic.this)
			{
				ultraBuffer[index] = ultrasonic.getValue();
				index = (index + 1) % ultraBuffer.length;
				smoothed = 0;
				for(int i = 0; i < ultraBuffer.length; i++)
				{
					smoothed += ultraBuffer[i];
				}
				smoothed /= ultraBuffer.length;
			}
		}
	}
}