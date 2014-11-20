package model;

public class ServerPayload {

	private long timestamp;
	private int x;
	private int y;
	private boolean[] stateValues;


	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public boolean[] getStateValues() {
		return stateValues;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}

	public void setStateValues(boolean[] stateValues) {
		this.stateValues = stateValues;
	}

}