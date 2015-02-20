package com.team1829.library;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Analog sensor implementation that includes a
 * data smoothing algorithm based on rolling calculations.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class CarbonAnalogInput 
{
	public static final int DEFAULT_BUFFER_SIZE = 25;
	public static final long DEFAULT_PERIOD = 50;
	public static final SmoothingMode DEFAULT_MODE = SmoothingMode.AVERAGE;
	
	public enum SmoothingMode
	{
		AVERAGE,
		MEDIAN
	}
	
	private AnalogInput analog;
	private Timer controlLoop;
	private boolean averageEnabled;
	private boolean medianEnabled;
	private int[] dataBuffer;
	private double smoothedAverage;
	private int smoothedMedian;
	
	/**
	 * Constructs a CarbonAnalogInput object with the specified analogPort,
	 * bufferSize, and cycle period.
	 * @param analogPort The analog port of the analog sensor.
	 * @param bufferSize The number of readings to average in the rolling
	 * data smoother.
	 * @param period The time between each sensor read.
	 */
	public CarbonAnalogInput(int analogPort, SmoothingMode defaultMode, int bufferSize, long period)
	{
		analog = new AnalogInput(analogPort);
		averageEnabled = false;
		medianEnabled = false;
		
		switch(defaultMode)
		{
		case AVERAGE:
			averageEnabled = true;
			break;
		case MEDIAN:
			medianEnabled = true;
			break;
		default:
			break;
		}
		
		if(bufferSize < 1)
		{
			bufferSize = 1;
		}
		dataBuffer = new int[bufferSize];
		for(int i = 0; i < dataBuffer.length; i++)
		{
			dataBuffer[i] = 0;
		}
		smoothedAverage = 0;
		smoothedMedian = 0;
		controlLoop = new Timer();
		controlLoop.schedule(new SmoothingTask(), 0L, 50);
	}
	
	/**
	 * Construct a CarbonAnalogInput object at a certain analogPort
	 * and bufferSize with the default cycle period.
	 * @param analogPort The analog port of the analog sensor.
	 * @param bufferSize The number of readings to average in the
	 * rolling data smoother.
	 */
	public CarbonAnalogInput(int analogPort, SmoothingMode mode, int bufferSize)
	{
		this(analogPort, mode, bufferSize, DEFAULT_PERIOD);
	}
	
	/**
	 * Construct a CarbonAnalogInput object at a certain analogPort
	 * and a certain smoothing mode
	 * with the default averaging buffer size and cycle period.
	 * @param analogPort The analog port the analog sensor
	 * is plugged into.
	 */
	public CarbonAnalogInput(int analogPort, SmoothingMode mode)
	{
		this(analogPort, mode, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * Initializes a CarbonAnalogInput at the analogPort with all
	 * other default values.
	 * @param analogPort
	 */
	public CarbonAnalogInput(int analogPort)
	{
		this(analogPort, DEFAULT_MODE);
	}
	
	/**
	 * Sets the specified mode to be either enabled or disabled.
	 * @param mode The mode to enable or disable.
	 * @param enabled Whether to enable or disable.
	 */
	public void setModeEnabled(SmoothingMode mode, boolean enabled)
	{
		switch(mode)
		{
		case AVERAGE:
			averageEnabled = enabled;
			break;
		case MEDIAN:
			medianEnabled = enabled;
			break;
		default:
			break;
		}
	}
	
	/**
	 * Returns a value averaged from the past X sensor readings,
	 * where X is the initialized bufferSize.
	 * @return Current averaged sensor value.
	 */
	public double getAverageSmoothedValue()
	{
		if(averageEnabled)
		{
			return smoothedAverage;			
		}
		return 0.0;
	}
	
	/**
	 * Returns a median value from the past X sensor readings,
	 * where X is the initialized bufferSize.
	 * @return Current median sensor value.
	 */
	public int getMedianSmoothedValue()
	{
		if(medianEnabled)
		{
			return smoothedMedian;			
		}
		return 0;
	}
	
	/**
	 * Returns the unaltered sensor value straight from the 
	 * analog sensor.
	 * @return Current unaltered sensor data.
	 */
	public double getRawValue()
	{
		return analog.getValue();
	}
	
	/**
	 * Task that will spawn a thread for constantly reading
	 * from the analog sensor and storing the values into
	 * an array.  The array is then smoothed, causing the 
	 * output of the CarbonAnalogInput to better represent
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
			/*
			 * Update the dataBuffer regardless of whether
			 * either smoothing algorithm is active or not.
			 */
			synchronized(CarbonAnalogInput.this)
			{
				dataBuffer[index] = analog.getValue();
				index = (index + 1) % dataBuffer.length;
			}
			
			if(averageEnabled)
			{
				/*
				 * Use a rolling-average method of data smoothing.
				 */
				synchronized(CarbonAnalogInput.this)
				{
					smoothedAverage = 0;
					for(int i = 0; i < dataBuffer.length; i++)
					{
						smoothedAverage += (double)dataBuffer[i];
					}
					smoothedAverage /= (double)dataBuffer.length;
				}
			}
			
			if(medianEnabled)
			{
				/*
				 * Use a rolling-median method of data smoothing.
				 */
				synchronized(CarbonAnalogInput.this)
				{
					smoothedMedian = 0;
					int[] orderedList = new int[dataBuffer.length];
					for(int i = 0; i < dataBuffer.length; i++)
					{
						orderedList[i] = dataBuffer[i];
					}
					Arrays.sort(orderedList);
					smoothedMedian = orderedList[((int)orderedList.length)/2];
				}
			}
		}
	}
}