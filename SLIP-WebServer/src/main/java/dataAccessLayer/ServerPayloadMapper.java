package dataAccessLayer;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.ServerPayload;

import org.springframework.jdbc.core.RowMapper;

public class ServerPayloadMapper implements RowMapper<ServerPayload>{

  @Override
  public ServerPayload mapRow(ResultSet rs, int rowNum) throws SQLException {
    ServerPayload payload = new ServerPayload();
    
    payload.setTimestamp(rs.getLong("Timestamp"));
    payload.setX(rs.getInt("xPosition"));
    payload.setY(rs.getInt("yPosition"));
    
    return payload;
  }

}