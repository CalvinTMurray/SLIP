/**
 * 
 */
package di.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author Calvin.T.Murray
 *
 */
@Configuration
@ComponentScan(value = {"dataAccessLayer"})
public class DIConfiguration {
	
	private static final String host = "localhost";
	private static final String port = "5432";
	private static final String dbName = "SLIP";
	private static final String username = "postgres";
	private static final String password = "SPORT";
	
	@Bean
	public DriverManagerDataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");  
		dataSource.setUrl("jdbc:postgresql://" + host + ":" + port + "/"+ dbName + "?autoReconnect=true");  
		dataSource.setUsername(username);  
		dataSource.setPassword(password);
		return dataSource;
	}
}
