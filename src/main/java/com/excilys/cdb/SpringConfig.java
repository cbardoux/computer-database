package main.java.com.excilys.cdb;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({ "main.java.com.excilys.cdb.dao", "main.java.com.excilys.cdb.service",
		"main.java.com.excilys.cdb.servlets", "main.java.com.excilys.cdb.view",
		"main.java.com.excilys.cdb.validator", "main.java.com.excilys.cdb.controller",
		"main.java.com.excilys.cdb.dto"})
public class SpringConfig extends AbstractContextLoaderInitializer {

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(SpringConfig.class);
		return context;
	}
	
	@Bean(destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        HikariConfig config = new HikariConfig("/main/resources/db.properties");
        return new HikariDataSource(config);
    }

}
