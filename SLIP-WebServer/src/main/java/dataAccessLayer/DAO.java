package dataAccessLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by Calvin . T . Murray on 07/01/2015.
 */
@Repository
public class DAO {

    protected static JdbcTemplate jdbcTemplateObject;

    @Autowired
    private void setDataSource(DataSource dataSource) {

        System.out.println("setDataSource. Datasource: " + (dataSource != null));
        jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

}
