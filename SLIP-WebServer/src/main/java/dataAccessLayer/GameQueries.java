package dataAccessLayer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import test.ServerFrame;
import test.ServerPayload;

@Repository
public class GameQueries implements GameDAO {

	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	@Override
	public void setDataSource(DataSource dataSource) {
		System.out.println("setDataSource. Datasource: " + (dataSource != null));
		jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getJdbcTemplate(){
		return jdbcTemplateObject;
	}


	@Override
	public List<ServerPayload> getAllPayloads(int sessionID) {
		String sql =	"SELECT \"SessionID\", \"xPosition\", \"yPosition\", \"Timestamp\" " +
				"FROM \"Game\" " + 
				"WHERE \"SessionID\" = ?";

		System.out.println("listUsers jdbcTemplateObject is null: " + (jdbcTemplateObject==null));
		List<ServerPayload> payloads = jdbcTemplateObject.query(sql, new Object[] {sessionID}, new ServerPayloadMapper());
		return payloads;
	}

	@Override
	public ServerPayload getPayload(int sessionID, int payloadID) {
		String sql = 	"SELECT \"SessionID\", \"xPosition\", \"yPosition\" " +
						"FROM \"Game\" " + 
						"WHERE \"SessionID\" = ? AND \"PayloadID\" = ?";

		return jdbcTemplateObject.queryForObject(sql, new Object[] {sessionID, payloadID}, new ServerPayloadMapper());
	}

	@Override
	public void insertFrame(final ServerFrame frame) {
		String sql = 	"INSERT INTO \"Game\" (\"SessionID\", \"xPosition\", \"yPosition\", \"Timestamp\") " +
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
		String sql = 	"INSERT INTO \"Game\" (\"SessionID\", \"xPosition\", \"yPosition\") " +
						"VALUES (?,?,?)";

		jdbcTemplateObject.update(sql, sessionID, payload.getX(), payload.getY());
		
	}

	@Override
	public List<ServerPayload> getPayloadsRange(int sessionID, long timestamp) {
		String sql =	"SELECT \"SessionID\", \"xPosition\", \"yPosition\", \"Timestamp\" " +
						"FROM \"Game\" " +
						"WHERE \"SessionID\" = ? AND \"Timestamp\" > ?";
		
		List<ServerPayload> payloads = jdbcTemplateObject.query(sql, new Object[] {sessionID, timestamp},  new ServerPayloadMapper());
		
		System.out.println("Executed getPayloadsRange");
		return payloads;
	}

	@Override
	public long getNewSessionID() {
		String sql = 	"SELECT MAX(\"SessionID\") " +
						"FROM \"Game\"";
		
		Long currentMaximumSessionID = jdbcTemplateObject.queryForObject(sql, Long.class);
		
		// Null check if there is no records in the database
		if (currentMaximumSessionID == null) {
			currentMaximumSessionID = (long) 0;
		}
		
		return (currentMaximumSessionID + 1);
	}
}
