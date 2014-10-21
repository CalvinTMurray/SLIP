package test;

import java.util.List;

public class ProspeckzFrame {

	private int sessionID;
	private int payloadSize;
	private List<ProspeckzPayload> payloads;

	public int getPayloadSize() {
		return payloadSize;
	}

	public List<ProspeckzPayload> getPayloads() {
		return payloads;
	}

	public void setPayloadSize(int payloadSize) {
		this.payloadSize = payloadSize;
	}

	public void setPayloads(List<ProspeckzPayload> payloads) {
		this.payloads = payloads;
	}

	public int getSessionID() {
		return sessionID;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

}