package statistics;

import java.util.List;

public class Statistics {
	
	private long sessionID;
	private List<AbstractChart> charts;
	
	public Statistics(long sessionID) {
		this.sessionID = sessionID;
	}
	
	/**
	 * 
	 * @return the session ID of which these statistics belong to
	 */
	public long getSessionID() {
		return sessionID;
	}
	
	/**
	 * Add a chart to these statistics
	 * @param chart
	 */
	public void addChart(AbstractChart chart) {
		charts.add(chart);
	}

}
