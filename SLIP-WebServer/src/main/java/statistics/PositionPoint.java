package statistics;

import java.io.Serializable;

public class PositionPoint implements Serializable{
	
	private static final long serialVersionUID = -973461268477223680L;
	public long timestamp;
	public int xPosition;
	public int yPosition;
	
	public PositionPoint(long timestamp, int x, int y) {
		this.timestamp = timestamp;
		this.xPosition = x;
		this.yPosition = y;
	}
	
	@Override
	public String toString() {
		return "(" + xPosition + "," + yPosition + ")";
	}
}