package model;

public class ServerPayload {

	private long timestamp;
	private int x;
	private int y;
	private float fx;
	private float fy;
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

	public float getFx() {
		return fx;
	}

	public void setFx(float fx) {
		this.fx = fx;
	}

	public float getFy() {
		return fy;
	}

	public void setFy(float fy) {
		this.fy = fy;
	}

}