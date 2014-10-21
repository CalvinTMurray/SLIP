package dataAccessLayer;

import java.util.List;

import javax.sql.DataSource;

import test.ProspeckzFrame;
import test.ProspeckzPayload;
//@Repository
public interface GameDAO {
	
	/**
	 * Set the datasource the JDBC template will use
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource);
	
	/**
	 * Return all the payloads that were received in a given session ID
	 * @param sessionID
	 * @return
	 */
	public List<ProspeckzPayload> getAllPayloads(int sessionID);
	
	/**
	 * Return a specific payload  given a session ID
	 * @param sessionID The ID of the session
	 * @param payloadID The ID of the payload to be retrieved
	 * @return
	 */
	public ProspeckzPayload getPayload(int sessionID, int payloadID);
	
	/**
	 * Insert a frame of payloads into the DB
	 * @param frame
	 */
	public void insertFrame(ProspeckzFrame frame);
	
	/**
	 * Insert a payload into the DB given a session ID
	 * @param sessionID The session ID of the payload to be inserted
	 * @param payload
	 */
	public void insertPayload(int sessionID, ProspeckzPayload payload);
	
}
