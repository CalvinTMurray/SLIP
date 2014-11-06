package test;

public class ServerPayload implements Comparable {

	private long timestamp;
	private int x;
	private int y;
	private boolean[] stateValues;

	public int getX() {
		return x;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getY() {
		return y;
	}

	public boolean[] getStateValues() {
		return stateValues;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setStateValues(boolean[] stateValues) {
		this.stateValues = stateValues;
	}

	@Override
	public int compareTo(Object o) {
		if (!(o instanceof ServerPayload))
			return 0;
		
		ServerPayload payload = (ServerPayload) o;
		if (payload.timestamp < this.timestamp)
			return -1;
		
		if (payload.timestamp > this.timestamp)
			return 1;
		
		if (payload.timestamp == this.timestamp)
			return 0;
		
		return 0;
	}

}