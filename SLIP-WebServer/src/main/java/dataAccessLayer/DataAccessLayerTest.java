package dataAccessLayer;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import receivedAppData.ServerFrame;
import receivedAppData.ServerPayload;
import di.configuration.DIConfiguration;

@ComponentScan({ "dataAccessLayer", "di.configuration" })
@EnableAutoConfiguration
public class DataAccessLayerTest {

	public static void main(String[] args) throws SQLException {
		
		SpringApplication.run(DataAccessLayerTest.class, args);
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(DIConfiguration.class);
		GameDAO userJDBCTemplate = ctx.getBean(GameQueries.class);

//		ProspeckzFrameDAO userJDBCTemplate = new TestControllerSingleton().getDao();
		
		
		/**
		 * Testing Connection to DB
		 */
//		Connection connection = null;
//		try {
//			 
//			connection = DriverManager.getConnection(§
//					"jdbc:postgresql://localhost:5432/SLIP", "postgres",
//					"SPORT");
// 
//		} catch (SQLException e) {
// 
//			System.out.println("Connection Failed! Check output console");
//			e.printStackTrace();
//			return;
// 
//		}
//		
//		if (connection == null){
//			System.out.println("No DB connection!");
//		} else {
//			System.out.println("Connection established!");
//		}
//		
		
		/**
		 * Checking tables in DB
		 */
//		try {
//			DatabaseMetaData md = dataSource.getConnection().getMetaData();
//			ResultSet rs = md.getColumns(null, null, "user", null);
//			while (rs.next()) {
//				System.out.println(rs.getString(4));
//			}
//		} catch (Exception e) {
//			System.out.println("Connection failed can't return metadata!");
//		}
		

		ServerPayload payload =  new ServerPayload();
//		payload.setReceiver_one(1);
//		payload.setReceiver_two(1);
//		payload.setReceiver_three(1);
//		payload.setReceiver_four(1);
//		payload.setTimestamp(1);

		ArrayList<ServerPayload> payloads = new ArrayList<ServerPayload>();
		payloads.add(payload);
		
		ServerFrame frame = new ServerFrame();
		frame.setSessionID(4);
		
		frame.setPayloads(payloads);

		System.out.println("Main: is userJDBCTemplate null: " + (userJDBCTemplate == null));
		
		System.out.println("Adding frame to DB");
		userJDBCTemplate.insertFrame(frame);
		
//		userJDBCTemplate.insertPayload(3, payload);
		
		
		
//		System.out.println(userJdbcTemplate.getUser(1).getForename());
//		System.out.println(userJdbcTemplate.listUsers().get(0).toString());
		
		
		
	}
}
