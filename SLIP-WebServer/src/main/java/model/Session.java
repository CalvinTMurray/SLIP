package model;


public class Session {

	private long sessionID;
	private long startTime;
	private long endTime;
	private String datePlayed;

	public long getSessionID() {
		return sessionID;
	}

	public void setSessionID(long sessionID) {
		this.sessionID = sessionID;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getDatePlayed() {
		return datePlayed;
	}

	public void setDatePlayed(String datePlayed) {
		this.datePlayed = datePlayed;
	}

}
