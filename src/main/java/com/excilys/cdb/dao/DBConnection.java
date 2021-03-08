package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnection {

	private static DBConnection instance = null;
	private Connection connectionDB = null;
	private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);
//	private static HikariConfig config = new HikariConfig();
//    private HikariDataSource connection;

	private DBConnection() {}
	
	public static DBConnection getInstance() {
		if(instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}
	
	public Connection connection() throws SQLException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connectionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db", "admincdb",
					"qwerty1234");
//			config.setJdbcUrl( "jdbc:mysql://localhost:3306/computer-database-db" );
//	        config.setUsername( "admincdb" );
//	        config.setPassword( "qwerty1234" );
//	        config.addDataSourceProperty( "cachePrepStmts" , "true" );
//	        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
//	        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
//	        connection = new HikariDataSource( config );
	        
		} catch (ClassNotFoundException e) {
			logger.error("An error has occured on the db connection : ", e);
		}
		return connectionDB;

	}

}
