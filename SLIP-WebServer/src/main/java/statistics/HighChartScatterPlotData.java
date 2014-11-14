package statistics;

public class HighChartScatterPlotData {

	private long timestamp;
	private PositionPoint position;
	
	public HighChartScatterPlotData(long timestamp, PositionPoint position) {
		this.timestamp = timestamp;
		this.position = position;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public int getX() {
		return position.xPosition;
	}
	
	public int getY() {
		return position.yPosition;
	}
}
