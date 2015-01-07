package dataAccessLayer;

import model.ServerFrame;
import model.ServerPayload;
import model.Session;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class SessionPayloadQueries extends DAO implements SessionPayloadDAO {

	@Override
	public List<ServerPayload> getAllPayloads(long sessionID) {
		String sql =	"SELECT \"SessionID\", \"xPosition\", \"yPosition\", \"Timestamp\" " +
				"FROM \"\" " + 
				"WHERE \"SessionID\" = ?";

		System.out.println("listUsers jdbcTemplateObject is null: " + (jdbcTemplateObject==null));
		return jdbcTemplateObject.query(sql, new Object[] {sessionID}, new ServerPayloadMapper());
	}

	@Override
	public ServerPayload getPayload(long sessionID, long payloadID) {
		String sql = 	"SELECT \"SessionID\", \"xPosition\", \"yPosition\" " +
						"FROM \"SessionPayload\" " + 
						"WHERE \"SessionID\" = ? AND \"PayloadID\" = ?";

		return jdbcTemplateObject.queryForObject(sql, new Object[] {sessionID, payloadID}, new ServerPayloadMapper());
	}

	@Override
	public void insertFrame(final ServerFrame frame) {
		String sql = 	"INSERT INTO \"SessionPayload\" (\"SessionID\", \"xPosition\", \"yPosition\", \"Timestamp\") " +
						"VALUES (?,?,?,?)";

		final int sessionID = frame.getSessionID();

		jdbcTemplateObject.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {

				ServerPayload payload = frame.getPayloads().get(i);
				ps.setLong(1, sessionID);
				ps.setInt(2, payload.getX());
				ps.setInt(3, payload.getY());
				ps.setLong(4, payload.getTimestamp());

				System.out.println("	-------------------------------------------------------------------------");
				System.out.println("		Inserting payload for session ID: " + sessionID + " - Position: (" + payload.getX()
						+ "," + payload.getY()+")");
			}

			@Override
			public int getBatchSize() { 
				return frame.getPayloads().size();
			}
		});
	}

	@Override
	public void insertPayload(long sessionID, ServerPayload payload) {
		String sql = 	"INSERT INTO \"SessionPayload\" (\"SessionID\", \"xPosition\", \"yPosition\") " +
						"VALUES (?,?,?)";

		jdbcTemplateObject.update(sql, sessionID, payload.getX(), payload.getY());
	}

	@Override
	public List<ServerPayload> getPayloadsRange(long sessionID, long timestamp) {
		String sql =	"SELECT \"SessionID\", \"xPosition\", \"yPosition\", \"Timestamp\" " +
						"FROM \"SessionPayload\" " +
						"WHERE \"SessionID\" = ? AND \"Timestamp\" > ?";
		
		List<ServerPayload> payloads = jdbcTemplateObject.query(sql, new Object[] {sessionID, timestamp},  new ServerPayloadMapper());
		
		System.out.println("Executed getPayloadsRange");
		return payloads;
	}

	@Override
	public long getNewSessionID() {
		String sqlMaxSessionID = 	"SELECT MAX(\"SessionID\") " +
									"FROM \"Session\"";
		
		Long currentMaximumSessionID = jdbcTemplateObject.queryForObject(sqlMaxSessionID, Long.class);
		
		// Null check if there is no records in the database
		if (currentMaximumSessionID == null) {
			currentMaximumSessionID = (long) 0;
		}
		
		Date sessionDate = new Date();
		long newSessionID = currentMaximumSessionID + 1;
		
		insertNewSession(newSessionID, sessionDate);
		
		return newSessionID;
	}
	
	private void insertNewSession(long sessionID, Date date) {
		String sqlInsertSession = 	"INSERT INTO \"Session\" (\"SessionID\", \"Date\") " +
									"VALUES (?,?)";

		jdbcTemplateObject.update(sqlInsertSession, sessionID, date);
	}
	
	@Override
	public List<Session> getAllSessionsIDs() {
		String sql = 	"SELECT \"SessionID\", \"StartTime\", \"EndTime\", \"Date\" " +
						"FROM \"Session\"";

		// TODO take this out and move it into a new package
		List<Session> sessions = jdbcTemplateObject.query(sql, new RowMapper<Session>() {

			@Override
			public Session mapRow(ResultSet rs, int rowNum) throws SQLException {
				Session session = new Session();
				session.setSessionID(rs.getLong("SessionID"));
				session.setStartTime(rs.getLong("StartTime"));
				session.setEndTime(rs.getLong("EndTime"));
				session.setDate(rs.getString("Date"));
				return session;
			}
			
		});
		
		return sessions;
	}

	@Override
	public void insertSessionTime(long sessionID, long startTime, long endTime) {
		String sql = 	"UPDATE \"Session\" " +
						"SET (\"StartTime\", \"EndTime\") = (?,?) " +
						"WHERE \"SessionID\" = ?";
		
		jdbcTemplateObject.update(sql, startTime, endTime, sessionID);
	}
}