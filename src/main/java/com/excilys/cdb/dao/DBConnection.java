package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.excilys.cdb.exception.ServiceException;

public class DBConnection {

	private static DBConnection instance = null;
	private Connection connectionDB = null;
	private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);

	private DBConnection() {}
	
	public static DBConnection getInstance() {
		if(instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}
	
	public Connection connection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connectionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db", "admincdb",
					"qwerty1234");
		} catch (SQLException | ClassNotFoundException e) {
			logger.error("An error has occured on the db connection : ", e);
		}
		return connectionDB;

	}

}
