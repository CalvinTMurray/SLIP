package test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import statistics.HighChartScatterPlot;
import statistics.HighChartScatterPlotData;
import statistics.PositionPoint;
import statistics.StatisticsThread;
import dataAccessLayer.GameDAO;
import dataAccessLayer.GameQueries;
import dataAccessLayer.StatisticsQueries;
import di.configuration.DIConfiguration;

@RestController
public class TestController {

//	private ArrayList<ServerPayload> payloads = new ArrayList<ServerPayload>();
	private ApplicationContext ctx = new AnnotationConfigApplicationContext(DIConfiguration.class);
	private GameDAO userJDBCTemplate = ctx.getBean(GameQueries.class);
	private StatisticsQueries statisticsQueries = ctx.getBean(StatisticsQueries.class);
	
	@RequestMapping(method = RequestMethod.POST, value = "/test", headers = { "Content-type=application/json" }, produces = { "application/json" })
	public @ResponseBody String test(@RequestBody String payload) {

		System.out.println(payload);

		return "Post handled";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/server-frame", headers = { "Content-type=application/json" }, produces = { "application/json" })
	public @ResponseBody String sframe(@RequestBody ServerFrame frame) {

		
		userJDBCTemplate.insertFrame(frame);

		return "Post for frame handled";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/server-payload", headers = { "Content-type=application/json" }, produces = { "application/json" })
	public @ResponseBody String spayload(@RequestBody ServerPayload payload) {

		System.out.println(payload);

		return "Posting payload handled";
		
	}

	@RequestMapping(method = RequestMethod.GET, value = "/payloads")
    public List<ServerPayload> payloads(@RequestParam(value="timestamp", required=false, defaultValue="0") long timestamp) {
        System.out.println("Requesting payloads");
		return userJDBCTemplate.getPayloadsRange(0, timestamp);
        
    }
	
	class SessionID {
		public long sessionID;
		public SessionID (long sessionID) {
			this.sessionID = sessionID;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/new-session", produces = { "application/json" })
    public SessionID newSessionID(@RequestParam(value = "suggestedSessionID", required=true) long suggestedSessionID) {
		
		System.out.println("SuggestedSessionID: " + suggestedSessionID);
		
		SessionID sessionID;
		if (suggestedSessionID < 0) {
			
			sessionID = new SessionID(userJDBCTemplate.getNewSessionID());
			StatisticsThread.getInstance().addSession(sessionID.sessionID);
			
			System.out.println("Issuing new Session ID: " + sessionID.sessionID);
		} else {
			sessionID = new SessionID(suggestedSessionID);
			System.out.println("Issuing the previous Session ID: " + sessionID.sessionID);
		}

		return sessionID;
		
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/end-session", produces = { "application/json" })
    public void endSessionID() {
		
		StatisticsThread.getInstance().terminateAllSessions();
        System.out.println("Terminating Sessions");
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/ping", headers = { "Content-type=application/json" }, produces = { "application/json" })
	public @ResponseBody String ping(@RequestBody String timestamp) {
		System.out.println("Responding to ping");
			
		return Long.toString(System.currentTimeMillis());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/position-data", produces = { "application/json" })
    public @ResponseBody HighChartScatterPlot getData() {
		
		
		long sessionID = userJDBCTemplate.getNewSessionID() - 1;
		
		return StatisticsThread.getInstance().createHighChartScatterPlot(sessionID);
    }
}