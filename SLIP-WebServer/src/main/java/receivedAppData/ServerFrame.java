package receivedAppData;

import java.util.List;

public class ServerFrame {

	private int sessionID;
	private int payloadSize;
	private List<ServerPayload> payloads;

	public int getSessionID() {
		return sessionID;
	}

	public int getPayloadSize() {
		return payloadSize;
	}

	public List<ServerPayload> getPayloads() {
		return payloads;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	public void setPayloadSize(int payloadSize) {
		this.payloadSize = payloadSize;
	}

	public void setPayloads(List<ServerPayload> payloads) {
		this.payloads = payloads;
	}

	@Override
	public String toString() {

		String str = "-----------------\nSession ID:[" + sessionID + "]" + "\n"
				+ "PayloadSize:[" + payloadSize + "]\n-----------------\n";

		for (int i = 0; i < payloadSize; i++) {
			str += "Payload: " + i + " ";
			str += payloads.get(i).toString();
			str += "\n";
		}

		return str;

	}

}