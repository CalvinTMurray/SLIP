package dataAccessLayer;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import test.ProspeckzPayload;

public class ProspeckzPayloadMapper implements RowMapper<ProspeckzPayload>{

	@Override
	public ProspeckzPayload mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("I'm getting called yaya!");
		ProspeckzPayload payload = new ProspeckzPayload();
		payload.setReceiver_one(rs.getInt("ReceiverOneDist"));
		payload.setReceiver_two(rs.getInt("ReceiverTwoDist"));
		payload.setReceiver_three(rs.getInt("ReceiverThreeDist"));
		payload.setReceiver_four(rs.getInt("ReceiverFourDist"));
		
		return payload;
	}

}