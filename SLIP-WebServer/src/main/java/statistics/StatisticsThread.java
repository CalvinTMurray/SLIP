/**
 * 
 */
package statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import statistics.Time.TimeValue;
import dataAccessLayer.StatisticsQueries;
import di.configuration.DIConfiguration;

/**
 * @author Calvin.T.Murray
 *
 */
public class StatisticsThread implements Runnable {
	
	private ApplicationContext ctx = new AnnotationConfigApplicationContext(DIConfiguration.class);
	private StatisticsQueries statisticsQueries = ctx.getBean(StatisticsQueries.class);
	
	private static StatisticsThread instance;
	
	private Queue<Long> newSessions;
	private Queue<Long> completedSessions;
	
	private Map<Long, Statistics> pendingSessions;

	private Long sessionID;
	
	public StatisticsThread() {
		newSessions = new ConcurrentLinkedQueue<Long>();
		completedSessions = new ConcurrentLinkedQueue<Long>();
		
		pendingSessions = new HashMap<Long, Statistics>();
	}
	
	public static StatisticsThread getInstance() {
		if (instance != null) {
			return instance;
		} else {
			instance = new StatisticsThread();
			return instance;
		}
	}

	@Override
	public void run() {
		while(true) {
			
			while ((sessionID = newSessions.poll()) != null) {
				System.out.println("Initialising statistics for SessionID: " + sessionID);
				pendingSessions.put(sessionID, new Statistics(sessionID));
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			while (((sessionID = completedSessions.poll()) != null)) {
				System.out.println("Processing statistics for SessionID: " + sessionID);
				Statistics statistics = pendingSessions.get(sessionID);
				
			}
		}
	}
	
	public HighChartScatterPlot createHighChartScatterPlot(long sessionID) {
		long startTime = statisticsQueries.getMinTime(sessionID);
		long endTime = statisticsQueries.getMaxTime(sessionID);

		// Game duration in milliseconds
		long duration = (endTime - startTime);
		
		List<Long> timeSlices = Time.getTimeSlice(TimeValue.SECONDS, startTime, endTime);
		
		System.out.println("Size of timeSlices: " + timeSlices.size());
		System.out.println("SessionID: " + sessionID);
		System.out.println("startTime: " + startTime);
		System.out.println("endTime: " + endTime);
		System.out.println("Game duration: " + duration);
		
		HighChartScatterPlot chart = new HighChartScatterPlot();
		
		for (int i = 0; i < timeSlices.size(); i++) {
			PositionPoint point;
			if (i == 0) {
				point = statisticsQueries.getClosestPoint(sessionID, startTime, 0, timeSlices.get(i+1));
			} else if (i == timeSlices.size() - 1) {
				point = statisticsQueries.getClosestPoint(sessionID, timeSlices.get(i), timeSlices.get(i - 1), endTime);
			} else {
				point = statisticsQueries.getClosestPoint(sessionID, timeSlices.get(i), timeSlices.get(i - 1), timeSlices.get(i + 1));
			}
			
			chart.addData(timeSlices.get(i), point);
		}
		
		System.out.println("size of highChartScatterPlot data: " + chart.getData().size());
		
		return chart;
	}
	
	/**
	 * Add a session to create statistics for
	 * @param sessionID the session ID of a game
	 */
	public void addSession(long sessionID) {
		newSessions.add(sessionID);
	}
	
	/**
	 * Inform the statistics thread that it can perform statistics processing on the specified session
	 * @param sessionID the session ID of the game which has been completed
	 */
	public void addSessionTerminated(long sessionID) {
		completedSessions.add(sessionID);
	}
	
	/**
	 * Run statistical analysis on the pending sessions immediately.
	 */
	public synchronized void terminateAllSessions() {
		
		for (long sessionID : pendingSessions.keySet()) {
			completedSessions.add(sessionID);
		}
		
	}		
}
