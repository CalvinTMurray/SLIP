package statistics;

public class PositionPoint {
	
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