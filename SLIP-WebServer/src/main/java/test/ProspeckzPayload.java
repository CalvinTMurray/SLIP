package test;

public class ProspeckzPayload {

	private long timestamp;

	private int receiver_one;
	private int receiver_two;
	private int receiver_three;
	private int receiver_four;

	private boolean state_one;
	private boolean state_two;
	private boolean state_three;
	private boolean state_four;

	public long getTimestamp() {
		return timestamp;
	}

	public int getReceiver_one() {
		return receiver_one;
	}

	public int getReceiver_two() {
		return receiver_two;
	}

	public int getReceiver_three() {
		return receiver_three;
	}

	public int getReceiver_four() {
		return receiver_four;
	}

	public boolean isState_one() {
		return state_one;
	}

	public boolean isState_two() {
		return state_two;
	}

	public boolean isState_three() {
		return state_three;
	}

	public boolean isState_four() {
		return state_four;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void setReceiver_one(int receiver_one) {
		this.receiver_one = receiver_one;
	}

	public void setReceiver_two(int receiver_two) {
		this.receiver_two = receiver_two;
	}

	public void setReceiver_three(int receiver_three) {
		this.receiver_three = receiver_three;
	}

	public void setReceiver_four(int receiver_four) {
		this.receiver_four = receiver_four;
	}

	public void setState_one(boolean state_one) {
		this.state_one = state_one;
	}

	public void setState_two(boolean state_two) {
		this.state_two = state_two;
	}

	public void setState_three(boolean state_three) {
		this.state_three = state_three;
	}

	public void setState_four(boolean state_four) {
		this.state_four = state_four;
	}

}