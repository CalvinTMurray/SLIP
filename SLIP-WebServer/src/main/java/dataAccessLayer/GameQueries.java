package dataAccessLayer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import test.ProspeckzFrame;
import test.ProspeckzPayload;

@Repository
public class GameQueries implements GameDAO {

	private JdbcTemplate jdbcTemplateObject;

//	public ProspeckzFrameQueries(DataSource dataSource) {
//		jdbcTemplateObject = new JdbcTemplate(dataSource);
//	}
	
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
	public List<ProspeckzPayload> getAllPayloads(int sessionID) {
		String sql = "SELECT \"SessionID\", \"ReceiverOneDist\", \"ReceiverTwoDist\"," +
				     " \"ReceiverThreeDist\", \"ReceiverFourDist\" " +
				     "FROM \"Game\"" + 
				     "WHERE \"SessionID\" = ?";
		System.out.println("listUsers jdbcTemplateObject is null: " + (jdbcTemplateObject==null));
		List<ProspeckzPayload> payloads = jdbcTemplateObject.query(sql, new Object[] {sessionID}, new ProspeckzPayloadMapper());
		return payloads;
	}

	@Override
	public ProspeckzPayload getPayload(int sessionID, int payloadID) {
		String sql = "SELECT \"ReceiverOneDist\", \"ReceiverTwoDist\", \"ReceiverThreeDist\", \"ReceiverFourDist\" "
				+ "FROM \"Game\" WHERE \"SessionID\" = ? AND \"PayloadID\" = ?";
		return jdbcTemplateObject.queryForObject(sql, new Object[] {sessionID, payloadID}, new ProspeckzPayloadMapper());
	}

	@Override
	public void insertFrame(ProspeckzFrame frame) {
		String sql = "INSERT INTO \"Game\" (\"SessionID\") VALUES (?)";
		int sessionID = frame.getSessionID();
		
		jdbcTemplateObject.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				System.out.println("setValues is getting called!");
				
				ProspeckzPayload payload = frame.getPayloads().get(i);
				ps.setInt(1, sessionID);
				System.out.println("Adding session ID: " + sessionID);
//				ps.setInt(2, payload.getReceiver_one());
//				System.out.println("");
//				ps.setInt(3, payload.getReceiver_two());
//				ps.setInt(4, payload.getReceiver_three());
//				ps.setInt(5, payload.getReceiver_four());
//				ps.setLong(6, payload.getTimestamp());
			}
			
			@Override
			public int getBatchSize() {	
				return frame.getPayloads().size();
			}
		});
	}

	@Override
	public void insertPayload(int sessionID, ProspeckzPayload payload) {
		
		String sql = "INSERT INTO \"Game\" (\"SessionID\") VALUES (?)";
		jdbcTemplateObject.update(sql, sessionID);
		
		// TODO Auto-generated method stub
		
	}


}
