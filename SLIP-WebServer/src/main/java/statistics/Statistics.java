package statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import charts.AbstractChart;
import charts.ChartType;

public class Statistics implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long sessionID;
	private Date createdOn;
	private Map<ChartType, AbstractChart<?>> charts;
	
	public Statistics(long sessionID) {
		this.sessionID = sessionID;
		this.createdOn = new Date();
		this.charts = new HashMap<ChartType, AbstractChart<?>>();
	}
	
	/**
	 * 
	 * @return the session ID of which these statistics belong to
	 */
	public long getSessionID() {
		return sessionID;
	}
	
	/**
	 * 
	 * @return the date and time of when these statistics were generated
	 */
	public Date getDate() {
		return createdOn;
	}
	
	/**
	 * Add a chart to these statistics <br>
	 * @param chart
	 */
	protected void addChart(AbstractChart<?> chart) {
		charts.put(chart.getType(), chart);
	}
	
	public AbstractChart<?> getChart(ChartType type) {
		return charts.get(type);
	}

}
