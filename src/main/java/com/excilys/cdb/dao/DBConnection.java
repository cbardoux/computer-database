package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
@Scope("singleton")
public class DBConnection {

	private static HikariConfig config;
	private HikariDataSource connection;

	private void connection() {
		config = new HikariConfig("/main/resources/db.properties");
		connection = new HikariDataSource(config);
	}
	
	public Connection getConnection() throws SQLException {
		if(connection == null || connection.isClosed()) {
			connection();
		}
		return connection.getConnection();
	}

}
