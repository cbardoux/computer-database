package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.cdb.data.Computer;

public class ComputerDAO {
	private static ComputerDAO instanceComputer = null;
	private DBConnection instanceDB = null;

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
	private static final String FIND_COMPUTER_QUERY = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?;";
	private static final String CREATE_COMPUTER_QUERY = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private static final String MODIFY_COMPUTER_QUERY = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
	private static final String DELETE_COMPUTER_QUERY = "DELETE FROM computer WHERE id = ?;";

	public List<Computer> listComputers() {

		List<Computer> resultList = new ArrayList<>();
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(FIND_COMPUTERS_QUERY);
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				Computer computer = new Computer(resultSet.getInt(1), resultSet.getString(2),
						resultSet.getDate(3) != null ? resultSet.getDate(3).toLocalDate() : null,
						resultSet.getDate(4) != null ? resultSet.getDate(4).toLocalDate() : null, resultSet.getInt(5));

				resultList.add(computer);

			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	public Computer getComputerById(int id) {

		Computer computer = new Computer();
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(FIND_COMPUTER_QUERY)) {

			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				computer = new Computer(id, resultSet.getString(2),
						resultSet.getDate(3) != null ? resultSet.getDate(3).toLocalDate() : null,
						resultSet.getDate(4) != null ? resultSet.getDate(4).toLocalDate() : null, resultSet.getInt(5));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}

	public long createComputer(Computer computer) {
		long result = 0;
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(CREATE_COMPUTER_QUERY)) {

			statement.setString(1, computer.getName());
			statement.setDate(2, java.sql.Date.valueOf(computer.getIntroduced()));
			statement.setDate(3, java.sql.Date.valueOf(computer.getDiscontinued()));
			statement.setInt(4, computer.getCompany_id());

			result = statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public long modifyComputer(Computer computer) {
		long result = 0;
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(MODIFY_COMPUTER_QUERY)) {

			statement.setString(1, computer.getName());
			statement.setDate(2, java.sql.Date.valueOf(computer.getIntroduced()));
			statement.setDate(3, java.sql.Date.valueOf(computer.getDiscontinued()));
			statement.setInt(4, computer.getCompany_id());
			statement.setInt(5, computer.getId());

			result = statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public long deleteComputer(int id) {
		long result = 0;
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(DELETE_COMPUTER_QUERY)) {

			statement.setInt(1, id);
			result = statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
