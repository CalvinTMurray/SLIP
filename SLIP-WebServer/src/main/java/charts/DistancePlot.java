package charts;

import java.util.ArrayList;
import java.util.List;

import statistics.PositionPoint;

public class DistancePlot extends AbstractChart<DistancePlotData> {

	private static final long serialVersionUID = -9118688909014559483L;
	private List<DistancePlotData> data;
	private List<PositionPoint> points;
	
	public DistancePlot(long sessionID, List<PositionPoint> points) {
		this.data = new ArrayList<DistancePlotData>();
		this.points = points;
		super.setType(ChartType.DISTANCE);
		
		createChart(sessionID);
	}
	
	private void addData(long timestamp, Double distance) {
		System.out.println("inserted DistancePlot timestamp: " + timestamp + "\tdistance: " + distance);
		data.add(new DistancePlotData(timestamp, distance));
	}
	
	@Override
	public List<DistancePlotData> getData() {
		return data;
	}
	
	@Override
	protected void createChart (long sessionID) {
		
		PositionPoint previousPoint = points.get(0);
		double totalDistance = 0;
		
		for (int i = 1; i < points.size(); i++) {
			
			PositionPoint currentPoint = points.get(i);
			
			if (!previousPoint.hasPositionPoint()) {
				previousPoint = currentPoint;
				continue;
			}
			
			if (!currentPoint.hasPositionPoint()) {
				addData(currentPoint.timestamp, null);
				continue;
			}
			
			double distance = euclideanDistance(previousPoint, currentPoint);
			totalDistance += distance;
			previousPoint = currentPoint;
			
			addData(currentPoint.timestamp, totalDistance);
		}
		
		
		
		
//		for (int i = 1; i < points.size(); i++) {
//			PositionPoint currentPoint = points.get(i);
//			
//			// TODO need to check that the x and y position are not null
//			if (currentPoint == null) {
//				System.out.println("Added null distance data");
//				data.add(null);
//			} else {
//				double distance = euclideanDistance(previousPoint, points.get(i));
//				totalDistance += distance;
//				previousPoint = currentPoint;
//				
//				addData(currentPoint.timestamp, totalDistance);
//			}
//		}
	}
	
	private double euclideanDistance(PositionPoint pointOne, PositionPoint pointTwo) {
		double dx = Math.pow((pointOne.xPosition - pointTwo.xPosition),2);
		double dy = Math.pow((pointOne.yPosition - pointTwo.yPosition),2);
		
		return Math.sqrt(dx+dy);
		
	}
}