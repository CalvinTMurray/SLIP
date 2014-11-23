package statistics;

import java.io.Serializable;

public class PositionPoint implements Serializable{
	
	private static final long serialVersionUID = -973461268477223680L;
	public long timestamp;
	public Integer xPosition;
	public Integer yPosition;
	
	public PositionPoint(long timestamp, Integer x, Integer y) {
		this.timestamp = timestamp;
		this.xPosition = x;
		this.yPosition = y;
	}
	
	public boolean hasPositionPoint() {
		if (xPosition == null && yPosition == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return "(" + xPosition + "," + yPosition + ")";
	}
}