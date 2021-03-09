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

	private static HikariConfig config = new HikariConfig();
	private HikariDataSource connection;

	private void connection() {
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		config.setJdbcUrl("jdbc:mysql://localhost:3306/computer-database-db");
		config.setUsername("admincdb");
		config.setPassword("qwerty1234");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		connection = new HikariDataSource(config);
	}
	
	public Connection getConnection() throws SQLException {
		if(connection == null || connection.isClosed()) {
			connection();
		}
		return connection.getConnection();
	}

}
