package charts;

import java.io.Serializable;

public class DistancePlotData implements Serializable{

	private static final long serialVersionUID = 3135832287178651143L;
	private long timestamp;
	private Double distance;
	
	public DistancePlotData(long timestamp, Double distance) {
		this.timestamp = timestamp;
		this.distance = distance;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public Double getDistance() {
		return distance;
	}
}
