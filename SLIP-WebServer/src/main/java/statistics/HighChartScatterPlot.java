package statistics;

import java.util.ArrayList;
import java.util.List;

public class HighChartScatterPlot {

	private List<HighChartScatterPlotData> data;
	
	public HighChartScatterPlot() {
		this.data = new ArrayList<HighChartScatterPlotData>();
	}
	public void addData(long timestamp, PositionPoint coords) {
		System.out.println("insertedData timestamp: " + timestamp + "\tcoordinates: " + coords);
		data.add(new HighChartScatterPlotData(timestamp,coords));
	}
	
	public List<HighChartScatterPlotData> getData() {
		return data;
	}
}