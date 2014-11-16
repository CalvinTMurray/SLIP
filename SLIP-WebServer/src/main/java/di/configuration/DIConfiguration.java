/**
 * 
 */
package di.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author Calvin.T.Murray
 *
 */
@ComponentScan(basePackages = {"dataAccessLayer", "di.configuration"})
public class DIConfiguration {
	
	private static final String host = "localhost";
	private static final String port = "5432";
	private static final String dbName = "SLIP";
	private static final String username = "postgres";
	private static final String password = "SPORT";
	
	@Bean
	public DataSource getDataSource(){
		System.out.println("Bean created");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");  
		dataSource.setUrl("jdbc:postgresql://" + host + ":" + port + "/"+ dbName + "?autoReconnect=true");  
		dataSource.setUsername(username);  
		dataSource.setPassword(password);
		return dataSource;
	}
	
}
