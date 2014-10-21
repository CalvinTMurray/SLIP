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

	//  public ServerFrameQueries(DataSource dataSource) {
	//    jdbcTemplateObject = new JdbcTemplate(dataSource);
	//  }

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
		String sql =	"SELECT \"SessionID\", \"xPosition\", \"yPosition\" " +
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
	public void insertFrame(ServerFrame frame) {
		String sql = 	"INSERT INTO \"Game\" (\"SessionID\", \"xPosition\", \"yPosition\") " +
						"VALUES (?,?,?)";

		int sessionID = frame.getSessionID();

		jdbcTemplateObject.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {

				ServerPayload payload = frame.getPayloads().get(i);
				ps.setLong(1, sessionID);
				ps.setInt(2, payload.getX());
				ps.setInt(3, payload.getY());

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

		// TODO Auto-generated method stub

	}
}
