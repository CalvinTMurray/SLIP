package controllers;

import java.util.ArrayList;
import java.util.List;

import model.ServerFrame;
import model.ServerPayload;
import model.Session;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import statistics.Statistics;
import statistics.StatisticsThread;
import charts.ChartType;
import dataAccessLayer.SessionPayloadDAO;
import dataAccessLayer.SessionPayloadQueries;
import di.configuration.DIConfiguration;

@RestController
public class MainController {


	private ApplicationContext ctx = new AnnotationConfigApplicationContext(DIConfiguration.class);
	private SessionPayloadDAO sessionPayloadQueries = ctx.getBean(SessionPayloadQueries.class);
	
	@RequestMapping(method = RequestMethod.POST, value = "/test", headers = { "Content-type=application/json" }, produces = { "application/json" })
	public @ResponseBody String test(@RequestBody String payload) {
		System.out.println(payload);

		return "Post handled";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/server-frame", headers = { "Content-type=application/json" }, produces = { "application/json" })
	public @ResponseBody String sframe(@RequestBody ServerFrame frame) {
		sessionPayloadQueries.insertFrame(frame);

		return "Post for server-frame handled";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/server-payload", headers = { "Content-type=application/json" }, produces = { "application/json" })
	public @ResponseBody String spayload(@RequestBody ServerPayload payload) {
		System.out.println(payload);

		return "Posting server-payload handled";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/payloads")
	public List<ServerPayload> payloads(
			@RequestParam(value = "session-id", required = true) long sessionID,
			@RequestParam(value = "timestamp", required = false, defaultValue = "0") long timestamp) {
		System.out.println("Requesting payloads");

		return sessionPayloadQueries.getPayloadsRange(sessionID, timestamp);
	}

	/**
	 * Session ID anonymous class
	 */
	private class SessionID {
		public long sessionID;

		public SessionID(long sessionID) {
			this.sessionID = sessionID;
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/new-session", produces = { "application/json" })
	public SessionID newSessionID(
			@RequestParam(value = "suggestedSessionID", required = true) long suggestedSessionID) {

		System.out.println("SuggestedSessionID: " + suggestedSessionID);
		SessionID sessionID;

		if (suggestedSessionID < 0) {
			sessionID = new SessionID(sessionPayloadQueries.getNewSessionID());
			StatisticsThread.getInstance().addSession(sessionID.sessionID);

			System.out
					.println("Issuing new Session ID: " + sessionID.sessionID);
		} else {
			sessionID = new SessionID(suggestedSessionID);
			StatisticsThread.getInstance().addSession(sessionID.sessionID);
			System.out.println("Issuing the previous Session ID: "
					+ sessionID.sessionID);
		}

		return sessionID;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/end-session", produces = { "application/json" })
	public void endSessionID() {
		StatisticsThread.getInstance().terminateAllSessions();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/ping", headers = { "Content-type=application/json" }, produces = { "application/json" })
	public @ResponseBody String ping(@RequestBody String timestamp) {
		System.out.println("Responding to ping");

		return Long.toString(System.currentTimeMillis());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/position-data", produces = { "application/json" })
	public @ResponseBody ResponseEntity<List<?>> getData(
			@RequestParam(value = "sessionID", required = true) long sessionID,
			@RequestParam(value = "chartType", required = true) ChartType type) {
		System.out.println("Getting statistics for session " + sessionID
				+ " and chart " + type);

		Statistics statistics = StatisticsThread.getInstance().getStatistics(sessionID);

		if (statistics == null) {
			// Empty list if we don't have any statistics yet
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(statistics.getChart(type).getData(), HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all-sessions", produces = { "application/json" })
	public @ResponseBody List<Session> getAllSessions() {
		System.out.println("Retrieving a list of sessions");

		// --- For testing purposes --- START
		ArrayList<Session> sessions = new ArrayList<>();

		for (int i = 1; i <= 19; i++) {
			Session s = new Session();
			s.setSessionID(i);
			sessions.add(s);
		}

		return sessions;

		// --- For testins purposes --- END

//		return sessionPayloadQueries.getAllSessionsIDs();
	}

}