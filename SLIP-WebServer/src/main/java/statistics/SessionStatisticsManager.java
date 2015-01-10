/**
 * 
 */
package statistics;

import charts.DistancePlot;
import charts.HeatmapPlot;
import charts.HighChartScatterPlot;
import com.sun.corba.se.spi.activation.RepositoryOperations;
import dataAccessLayer.StatisticsDAO;
import dataAccessLayer.StatisticsQueries;
import javafx.geometry.Pos;
import org.springframework.util.StopWatch;
import statistics.Time.TimeValue;

import javax.swing.text.Position;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A thread which runs in parallel with the server to generate statistics 
 * @author Calvin.T.Murray
 *
 */
public class SessionStatisticsManager implements Runnable {
	
	public static StatisticsDAO statisticsQueries = new StatisticsQueries();

	private static SessionStatisticsManager instance;
	
	private Queue<Long> newSessions;
	private Map<Long, Statistics> pendingSessions;
	private Map<Long, Statistics> completedSessions;

	private final String basePathStatistics = "C:/Users/Calvin . T . Murray/Documents/GitHub/SLIP/SLIP-WebServer/serialized/statistics/";
	private Map<Long,Statistics> loadedStatistics;
	
	private SessionStatisticsManager() {
		newSessions = new ConcurrentLinkedQueue<Long>();
		pendingSessions = new ConcurrentHashMap<Long, Statistics>();
		completedSessions = new ConcurrentHashMap<Long, Statistics>();
		
		loadedStatistics = new ConcurrentHashMap<Long, Statistics>();
	}
	
	public static SessionStatisticsManager getInstance() {
		if (instance != null) {
			return instance;
		} else {
			instance = new SessionStatisticsManager();
			return instance;
		}
	}

	@Override
	public void run() {
		while(true) {

			synchronized (pendingSessions) {

				Long sessionID;
				while ((sessionID = newSessions.poll()) != null) {
					System.out.println("Initialising statistics for SessionID: " + sessionID);
					if (!pendingSessions.containsKey(sessionID)) {
						pendingSessions.put(sessionID, new Statistics(sessionID));
					}
				}

			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			

			Iterator<Entry<Long, Statistics>> iterator = completedSessions.entrySet().iterator();
			while (iterator.hasNext()) {
				StopWatch stopwatch = new StopWatch();
				stopwatch.start();

				Entry<Long, Statistics> entry = iterator.next();
				
				System.out.println("Processing statistics for session ID: " + entry.getKey());
				boolean success = addCharts(entry.getValue());
				
				if (success) {
					serializeStatistics(entry.getValue());
				}
				stopwatch.stop();
				System.out.println("Statistics for session ID: " + entry.getKey() + " processed in " + stopwatch.getLastTaskTimeMillis());
				
				iterator.remove();
			}
		}
	}
	
	/**
	 * Include all the charts that you want to add to the statistics here
	 * @param statistics the statistics object the charts should be created for
	 * @return
	 */
	private boolean addCharts(Statistics statistics) {
		
		long sessionID = statistics.getSessionID();

		Long startTime = statisticsQueries.getMinTime(sessionID);
		if (startTime == null) {
			System.out.println("No charts will be added for session ID: " + sessionID + " because no data exists!");
			return false;
		}

		long endTime = statisticsQueries.getMaxTime(sessionID);
		long duration = (endTime - startTime);

		List<Long> timeSlices = Time.getTimeSlice(TimeValue.SECONDS, startTime, endTime);
		List<PositionPoint> points = new ArrayList<PositionPoint>();

		// NEW CODE STARTS
		PositionPoint[] data = new PositionPoint[0];
		data = statisticsQueries.getPoints(sessionID).toArray(data);
		// NEW CODE ENDS

		System.out.println("Size of timeSlices: " + timeSlices.size());
		System.out.println("SessionID: " + sessionID);
		System.out.println("startTime: " + startTime);
		System.out.println("endTime: " + endTime);

		// NEW CODE STARTS
		for (Long timeSlice : timeSlices) {
			points.add(closestValueBinarySearch(data, timeSlice, (TimeValue.SECONDS)/2));
		}
		// NEW CODE ENDS

//		for (int i = 0; i < timeSlices.size(); i++) {
//			PositionPoint point;
//			long currentTimeSlice = timeSlices.get(i);
//
//
//			if (i == 0 && timeSlices.size() == 1) { // At the start and there is only one point
//				point = statisticsQueries.getClosestPoint(sessionID, currentTimeSlice, currentTimeSlice, currentTimeSlice);
//			} else if (i == timeSlices.size() - 1) { // At the end
//				point = statisticsQueries.getClosestPoint(sessionID, currentTimeSlice, timeSlices.get(i - 1), endTime);
//			} else if (i == 0) { // At the start with time slices still to process
//				point = statisticsQueries.getClosestPoint(sessionID, currentTimeSlice, 0, timeSlices.get(i+1));
//			} else { // Processing points somewhere in the middle
//				point = statisticsQueries.getClosestPoint(sessionID, currentTimeSlice, timeSlices.get(i - 1), timeSlices.get(i + 1));
//			}
//
//			points.add(point);
//		}

		statistics.addChart(new HighChartScatterPlot(sessionID, points));
		statistics.addChart(new DistancePlot(sessionID, points));
		statistics.addChart(new HeatmapPlot(sessionID, points));

		System.out.println("Session duration: " + duration);

		return true;
	}

	/**
	 * Add a new session which the statistcs thread will create statistics for
	 * @param sessionID the session ID of a game
	 */
	public void addSession(long sessionID) {
		if (!newSessions.contains(sessionID)) {
			newSessions.add(sessionID);
		}
	}
	
	/**
	 * Inform the statistics thread that it can perform statistical processing on the specified session
	 * @param sessionID the session ID of the game which has been completed
	 */
	public void terminateSession(long sessionID) {
		
		completedSessions.put(sessionID, pendingSessions.remove(sessionID));

	}
	
	/**
	 * Inform the statistics thread that it can perform statistical processing on all pending sessions
	 */
	public void terminateAllSessions() {
		System.out.println("Terminating all sessions");
		synchronized (pendingSessions) {
			
			for (Entry<Long, Statistics> sessionStatistics : pendingSessions.entrySet()) {
				System.out.println("Session: " + sessionStatistics.getKey() + " is completed");
				completedSessions.put(sessionStatistics.getKey(), sessionStatistics.getValue());
			}
			
			pendingSessions.clear();

		}
		
	}
	
	/**
	 * Get the statistics from the specified session ID
	 * @param sessionID 
	 * @return
	 */
	public Statistics getStatistics(long sessionID) {
		
		if (loadedStatistics.containsKey(sessionID)) {
			return loadedStatistics.get(sessionID);
		} else {
			deserializeStatistics(sessionID);
			return loadedStatistics.get(sessionID);
		}
	}
	
	private void serializeStatistics(Statistics statistics) {
		
		String statsFileName = "session-" + statistics.getSessionID();
		
		try {
			System.out.println(new File(basePathStatistics + statsFileName + ".ser").getAbsolutePath());
			FileOutputStream fileOut = new FileOutputStream(basePathStatistics + statsFileName + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(statistics);
			out.close();
			fileOut.close();
			System.out.println("Serialized statistics to " + basePathStatistics + statsFileName + ".ser");
		} catch(IOException i) {
			i.printStackTrace();
		}
	}
	
	private void deserializeStatistics(long sessionID) {
		
		Statistics statistics = null;
		
		String statsFileName = "session-" + sessionID;
		
		File file = new File(basePathStatistics + statsFileName + ".ser");
		if( !file.exists() ) {
			System.out.println("File: " + file.toString() + " does not exist");
			return;
		}
		
		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			statistics = (Statistics) in.readObject();
			in.close();
			fileIn.close();
			System.out.println("Deserialized statistics for session " + sessionID);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Statistics class not found");
			c.printStackTrace();	
			return;
		}
		
		loadedStatistics.put(sessionID, statistics);
	}

	/**
	 * Modified binary search algorithm which find the closest value to the specified key
	 * @param a
	 * @param key
	 * @param interval
	 * @return
	 */
	public static PositionPoint closestValueBinarySearch(PositionPoint[] a, long key, long interval) {

		int lowIndex = 0;
		int highIndex = a.length - 1;
		int mid = (lowIndex + highIndex)/2;

		// Binary search (mid should be index to insert value at
		while (lowIndex <= highIndex) {

			if (a[mid].timestamp == key) {
				return a[mid];
			} else if (a[mid].timestamp > key) {
				highIndex = mid - 1;
			} else if (a[mid].timestamp < key) {
				lowIndex = mid + 1;
			}

			mid = (lowIndex + highIndex)/2;
		}

		// Interval check
		if (mid > 0 && mid < a.length && ((key - interval) <= a[mid].timestamp || (key + interval) >= a[mid + 1].timestamp)) {

			// Closest Value
			if ((key - a[mid].timestamp) < (a[mid + 1].timestamp - key)) {
				return a[mid];
			} else {
				return a[mid + 1];
			}

		}

		return new PositionPoint(key, null, null);
	}
	
}