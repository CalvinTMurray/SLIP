/**
 * 
 */
package statistics;

import java.util.ArrayList;
import java.util.List;

/** A class which manipulates time units 
 * @author Calvin.T.Murray
 *
 */
public class Time {
	
	// Constants for conversion from milliseconds and time slicing
	public class TimeValue {
		public static final int SECONDS = 1000;
		public static final int MINUTES = SECONDS*60;
	}
	

	/**
	 * Splits the time into segments, according to some specified interval
	 * @param interval the time interval in milliseconds
	 * @param startTime the start in milliseconds
	 * @param endTime the end time in milliseconds
	 * @return a list of time intervals from the start time to the end time inclusive
	 */
	public static List<Long> getTimeSlice(int interval, long startTime, long endTime) {
		// Don't time slice if we have an interval of 0
		if (interval == 0) {
			return null;
		}
		
		List<Long> timeSlices = new ArrayList<Long>();
		
		// Time slice up to the endTime
		for (long i = startTime; i <= endTime; i = i + interval) {
			timeSlices.add(i);
		}
		
		System.out.println("Size of timeSlices: " + timeSlices.size());
		return timeSlices;
	}
	
	/**
	 * Converts from milliseconds to seconds
	 * @param milliseconds
	 * @return seconds
	 */
	public static double convertToSeconds (long milliseconds) {
		return milliseconds/(double) TimeValue.SECONDS;
	}
	
}
