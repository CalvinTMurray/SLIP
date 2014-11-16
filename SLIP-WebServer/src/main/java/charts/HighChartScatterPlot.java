package charts;

import java.util.ArrayList;
import java.util.List;

import statistics.PositionPoint;
import statistics.StatisticsThread;
import statistics.Time;
import statistics.Time.TimeValue;
import dataAccessLayer.StatisticsQueries;

public class HighChartScatterPlot extends AbstractChart<HighChartScatterPlotData> {

	private List<HighChartScatterPlotData> data;
	
	public HighChartScatterPlot(long sessionID) {
		this.data = new ArrayList<HighChartScatterPlotData>();
		super.setType(ChartType.HIGHCHART_SCATTER_PLOT);
		
		createChart(sessionID);
	}
	
	private void addData(long timestamp, PositionPoint coords) {
		System.out.println("insertedData timestamp: " + timestamp + "\tcoordinates: " + coords);
		data.add(new HighChartScatterPlotData(timestamp,coords));
	}
	
	@Override
	public List<HighChartScatterPlotData> getData() {
		return data;
	}
	
	@Override
	protected void createChart (long sessionID) {
		
		StatisticsQueries queries = StatisticsThread.statisticsQueries;
		
		// All in milliseconds
		long startTime = queries.getMinTime(sessionID);
		long endTime = queries.getMaxTime(sessionID);
		long duration = (endTime - startTime);
		
		List<Long> timeSlices = Time.getTimeSlice(TimeValue.SECONDS, startTime, endTime);
		
		System.out.println("Size of timeSlices: " + timeSlices.size());
		System.out.println("SessionID: " + sessionID);
		System.out.println("startTime: " + startTime);
		System.out.println("endTime: " + endTime);
		System.out.println("Game duration: " + duration);
		
		// Add data points to the chart
		for (int i = 0; i < timeSlices.size(); i++) {
			PositionPoint point;
			long currentTimeSlice = timeSlices.get(i);
			
			if (i == 0) {
				point = queries.getClosestPoint(sessionID, currentTimeSlice, 0, timeSlices.get(i+1));
			} else if (i == timeSlices.size() - 1) {
				point = queries.getClosestPoint(sessionID, currentTimeSlice, timeSlices.get(i - 1), endTime);
			} else {
				point = queries.getClosestPoint(sessionID, currentTimeSlice, timeSlices.get(i - 1), timeSlices.get(i + 1));
			}
			
			addData(timeSlices.get(i), point);
		}
		
		System.out.println("Size of highChartScatterPlot data: " + getData().size());
	}
}