/**
 * 
 */
package statistics;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StopWatch;

import charts.HighChartScatterPlot;
import dataAccessLayer.StatisticsQueries;
import di.configuration.DIConfiguration;

/**
 * A thread which runs in parallel with the server to generate statistics 
 * @author Calvin.T.Murray
 *
 */
public class StatisticsThread implements Runnable {
	
	// Automatic dependency injection on the statisticsQueries
	private static ApplicationContext ctx = new AnnotationConfigApplicationContext(DIConfiguration.class);
	public static StatisticsQueries statisticsQueries = ctx.getBean(StatisticsQueries.class);
	
	private static StatisticsThread instance;
	
	private Queue<Long> newSessions;
	private Map<Long, Statistics> pendingSessions;
	private Map<Long, Statistics> completedSessions;

	private Long sessionID;
	
	private final String basePathStatistics = "serialized/statistics/";
	private Map<Long,Statistics> loadedStatistics;
	
	private StatisticsThread() {
		newSessions = new ConcurrentLinkedQueue<Long>();
		pendingSessions = new ConcurrentHashMap<Long, Statistics>();
		completedSessions = new ConcurrentHashMap<Long, Statistics>();
		
		loadedStatistics = new ConcurrentHashMap<Long, Statistics>();
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

			synchronized (pendingSessions) {

				while ((sessionID = newSessions.poll()) != null) {
					System.out.println("Initialising statistics for SessionID: " + sessionID);
					pendingSessions.put(sessionID, new Statistics(sessionID));
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
				addCharts(entry.getValue());
				serializeStatistics(entry.getValue());
				
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
	private Statistics addCharts(Statistics statistics) {
		statistics.addChart(new HighChartScatterPlot(statistics.getSessionID()));
		
		return statistics;
	}

	/**
	 * Add a new session which the statistcs thread will create statistics for
	 * @param sessionID the session ID of a game
	 */
	public void addSession(long sessionID) {
		newSessions.add(sessionID);
	}
	
	/**
	 * Inform the statistics thread that it can perform statistical processing on the specified session
	 * @param sessionID the session ID of the game which has been completed
	 */
	public void addSessionTerminated(long sessionID) {
		
		synchronized (pendingSessions) {
			completedSessions.put(sessionID, pendingSessions.get(sessionID));
		}
		
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
			FileOutputStream fileOut = new FileOutputStream(basePathStatistics + statsFileName + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(statistics);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in " + basePathStatistics + statsFileName + ".ser");
		} catch(IOException i) {
			i.printStackTrace();
		}
	}
	
	private void deserializeStatistics(long sessionID) {
		
		Statistics statistics = null;
		
		String statsFileName = "session-" + sessionID;
		
		try {
			FileInputStream fileIn = new FileInputStream(basePathStatistics + statsFileName + ".ser");
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
	
}