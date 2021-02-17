package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static DBConnection instance = null;
	private Connection connectionDB = null;

	private DBConnection() {}
	
	public static DBConnection getInstance() {
		if(instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}
	
	public Connection connection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connectionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db", "admincdb",
					"qwerty1234");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connectionDB;

	}

}
