package dataAccessLayer;

import java.util.List;

import model.ServerFrame;
import model.ServerPayload;
public interface SessionPayloadDAO {
  
//  /**
//   * Set the datasource the JDBC template will use
//   * @param dataSource
//   */
//  public void setDataSource(DataSource dataSource);
  
  /**
   * Return all the payloads that were received in a given session ID
   * @param sessionID
   * @return
   */
  public List<ServerPayload> getAllPayloads(int sessionID);
  
  /**
   * Return a specific payload  given a session ID
   * @param sessionID The ID of the session
   * @param payloadID The ID of the payload to be retrieved
   * @return
   */
  public ServerPayload getPayload(int sessionID, int payloadID);
  
  /**
   * Insert a frame of payloads into the DB
   * @param frame
   */
  public void insertFrame(ServerFrame frame);
  
  /**
   * Insert a payload into the DB given a session ID
   * @param sessionID The session ID of the payload to be inserted
   * @param payload
   */
  public void insertPayload(int sessionID, ServerPayload payload);
  
  /**
   * Return all the payloads that arrived later than the given timestamp
   * @param sessionID
   * @param timestamp
   */
  public List<ServerPayload> getPayloadsRange(long sessionID, long timestamp);
  
  /**
   * TODO This should be split into another DAO called SessionDAO!!!!!!!!!
   * @return the next session ID that has not been used in the DB
   */
  public long getNewSessionID();
  
  /**
   * TODO This should be split into another DAO called SessionDAO!!!!!!!!!!
   * Get a list of all the current sessions that are in the DB
   * @return
   */
  public List<Long> getAllSessionsIDs();
  
  public void insertSessionTime(long sessionID, long startTime, long endTime);
}