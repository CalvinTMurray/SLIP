package statistics;

import java.io.Serializable;

public class PositionPoint implements Serializable{
	
	private static final long serialVersionUID = -973461268477223680L;
	public int xPosition;
	public int yPosition;
	
	public PositionPoint(int x, int y) {
		this.xPosition = x;
		this.yPosition = y;
	}
	
	@Override
	public String toString() {
		return "(" + xPosition + "," + yPosition + ")";
	}
}