package fr.excilys.formation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.formation.data.Computer;

public class ComputerDAO {
	private static ComputerDAO instanceComputer = null;
	private DBConnection instanceDB;
	private Connection connection;

	private ComputerDAO() {
		instanceDB = DBConnection.getInstance();
	}

	public static ComputerDAO getInstance() {
		if (instanceComputer == null) {
			instanceComputer = new ComputerDAO();
		}
		return instanceComputer;
	}

	private static final String FIND_COMPUTERS_QUERY = "SELECT id, name, introduced, discontinued, company_id FROM computer;";

	public List<Computer> listComputers() {

		List<Computer> resultList = new ArrayList<>();
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(FIND_COMPUTERS_QUERY);
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				Computer computer = new Computer(resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getDate(3) != null ?
								resultSet.getDate(3).toLocalDate() : resultSet.getDate(3),
						resultSet.getDate(4) != null ?
								resultSet.getDate(4).toLocalDate() : resultSet.getDate(4),
						resultSet.getInt(5));

				resultList.add(computer);
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return resultList;
	}
}
