package dataAccessLayer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import model.ServerFrame;
import model.ServerPayload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class SessionPayloadQueries extends JdbcDaoSupport implements SessionPayloadDAO {

	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

//	@Autowired
//	@Override
//	public void setDataSource(DataSource dataSource) {
//		System.out.println("setDataSource. Datasource: " + (dataSource != null));
//		jdbcTemplateObject = new JdbcTemplate(dataSource);
//	}

//	public JdbcTemplate getJdbcTemplate(){
//		return jdbcTemplateObject;
//	}

	@PostConstruct
	private void initialise() {
		setDataSource(dataSource);
		jdbcTemplateObject = getJdbcTemplate();
	}

	@Override
	public List<ServerPayload> getAllPayloads(int sessionID) {
		String sql =	"SELECT \"SessionID\", \"xPosition\", \"yPosition\", \"Timestamp\" " +
				"FROM \"\" " + 
				"WHERE \"SessionID\" = ?";

		System.out.println("listUsers jdbcTemplateObject is null: " + (jdbcTemplateObject==null));
		List<ServerPayload> payloads = jdbcTemplateObject.query(sql, new Object[] {sessionID}, new ServerPayloadMapper());
		return payloads;
	}

	@Override
	public ServerPayload getPayload(int sessionID, int payloadID) {
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

				System.out.println("Inserting payload with session ID: " + sessionID + " x Position: " + payload.getX()
						+ " y Position: " + payload.getY());
			}

			@Override
			public int getBatchSize() { 
				return frame.getPayloads().size();
			}
		});
	}

	@Override
	public void insertPayload(int sessionID, ServerPayload payload) {
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
		String sqlInsertSession = 	"INSERT INTO \"Session\" (\"SessionID\", \"Date\")" +
									"VALUES (?,?)";

		jdbcTemplateObject.update(sqlInsertSession, sessionID, date);
	}
	
	@Override
	public List<Long> getAllSessionsIDs() {
		String sql = 	"SELECT DISTINCT \"SessionID\"" +
						"FROM \"SessionPayload\"";

		List<Long> sessions = jdbcTemplateObject.queryForList(sql, null, Long.class);
		
		return sessions;
	}

	@Override
	public void insertSessionTime(long sessionID, long startTime, long endTime) {
		String sql = 	"INSERT INTO \"Session\" (\"StartTime\", \"EndTime\")" +
						"VALUES (?,?)" +
						"WHERE \"SessionID\" = ?";
		
		jdbcTemplateObject.update(sql, startTime, endTime, sessionID);
	}
}
