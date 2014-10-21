package dataAccessLayer;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import test.ServerPayload;

public class ServerPayloadMapper implements RowMapper<ServerPayload>{

  @Override
  public ServerPayload mapRow(ResultSet rs, int rowNum) throws SQLException {
    ServerPayload payload = new ServerPayload();
    
    payload.setX(rs.getInt("xPosition"));
    payload.setY(rs.getInt("yPosition"));
    
    return payload;
  }

}