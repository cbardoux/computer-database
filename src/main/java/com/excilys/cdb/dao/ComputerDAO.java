package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.com.excilys.cdb.data.Computer;
import main.java.com.excilys.cdb.exception.DAOException;

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

	private static final String FIND_COMPUTERS_QUERY = "SELECT id, name, introduced, discontinued, company_id FROM computer LIMIT ?, 20;";
	private static final String FIND_COMPUTER_QUERY = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?;";
	private static final String CREATE_COMPUTER_QUERY = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private static final String MODIFY_COMPUTER_QUERY = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
	private static final String DELETE_COMPUTER_QUERY = "DELETE FROM computer WHERE id = ?;";

	public List<Computer> listComputers(int offset) throws DAOException{

		List<Computer> resultList = new ArrayList<>();
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(FIND_COMPUTERS_QUERY);) {

			statement.setInt(1, offset);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Computer computer = new Computer(resultSet.getInt(1), resultSet.getString(2),
						resultSet.getDate(3) != null ? resultSet.getDate(3).toLocalDate() : null,
						resultSet.getDate(4) != null ? resultSet.getDate(4).toLocalDate() : null, resultSet.getInt(5));

				resultList.add(computer);

			}

		} catch (SQLException e) {
			throw new DAOException("Error while trying to display computer's list : " + e.getMessage());
		}
		return resultList;
	}

	public Optional<Computer> getComputerById(int id) {

		Computer computer = new Computer();
		Optional<Computer> optionalComputer = null;
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(FIND_COMPUTER_QUERY)) {

			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				computer = new Computer(id, resultSet.getString(2),
						resultSet.getDate(3) != null ? resultSet.getDate(3).toLocalDate() : null,
						resultSet.getDate(4) != null ? resultSet.getDate(4).toLocalDate() : null, resultSet.getInt(5));
				optionalComputer = Optional.of(computer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return optionalComputer;
	}

	public void createComputer(Computer computer) {
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(CREATE_COMPUTER_QUERY)) {

			statement.setString(1, computer.getName());
			statement.setDate(2,
					computer.getIntroduced() != null ? java.sql.Date.valueOf(computer.getIntroduced()) : null);
			statement.setDate(3,
					computer.getDiscontinued() != null ? java.sql.Date.valueOf(computer.getDiscontinued()) : null);
			statement.setString(4, computer.getCompany_id() != 0 ? String.valueOf(computer.getCompany_id()) : null);

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modifyComputer(Computer computer) {
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(MODIFY_COMPUTER_QUERY)) {

			statement.setString(1, computer.getName());
			statement.setDate(2,
					computer.getIntroduced() != null ? java.sql.Date.valueOf(computer.getIntroduced()) : null);
			statement.setDate(3,
					computer.getDiscontinued() != null ? java.sql.Date.valueOf(computer.getDiscontinued()) : null);
			statement.setString(4, computer.getCompany_id() != 0 ? String.valueOf(computer.getCompany_id()) : null);
			statement.setInt(5, computer.getId());

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteComputer(int id) {
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(DELETE_COMPUTER_QUERY)) {

			statement.setInt(1, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
