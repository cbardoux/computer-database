package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.excilys.cdb.dto.ComputerDTOForDB;
import main.java.com.excilys.cdb.dto.ComputerDTOForServlet;
import main.java.com.excilys.cdb.dto.MappingDTO;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.model.Page;

public class ComputerDAO {
	private static ComputerDAO instanceComputer = null;
	private DBConnection instanceDB = null;
	private MappingDTO mappingDTO = MappingDTO.getInstance();
	private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	private ComputerDAO() {
		instanceDB = DBConnection.getInstance();
	}

	public static ComputerDAO getInstance() {
		if (instanceComputer == null) {
			instanceComputer = new ComputerDAO();
		}
		return instanceComputer;
	}

	private static final String FIND_COMPUTERS_WITH_PAGE_QUERY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = computer.company_id LIMIT ?, ?;";
	private static final String FIND_COMPUTERS_QUERY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = computer.company_id;";
	private static final String FIND_COMPUTER_QUERY = "SELECT computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = computer.company_id WHERE computer.id = ?;";
	private static final String CREATE_COMPUTER_QUERY = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private static final String MODIFY_COMPUTER_QUERY = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
	private static final String DELETE_COMPUTER_QUERY = "DELETE FROM computer WHERE id = ?;";
	private static final String COUNT_ROWS = "SELECT COUNT(id) FROM computer;";

	public Page<Computer> listComputersWithOffset(Page<Computer> page) {

		ComputerDTOForServlet computerDTO = new ComputerDTOForServlet();
		List<Computer> resultList = new ArrayList<>();
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(FIND_COMPUTERS_WITH_PAGE_QUERY);) {

			statement.setInt(1, (page.getIndex() - 1) * page.getLimit());
			statement.setInt(2, page.getLimit());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				computerDTO.id = resultSet.getInt(1);
				computerDTO.name = resultSet.getString(2);
				computerDTO.introduced = resultSet.getString(3);
				computerDTO.discontinued = resultSet.getString(4);
				computerDTO.company_id = resultSet.getString(5);
				computerDTO.company_name = resultSet.getString(6);

				Computer computer = mappingDTO.listComputerDTOToComputerObject(computerDTO);

				resultList.add(computer);

			}
			page.setContent(resultList);

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return page;
	}

	public List<Computer> listComputers() {
		ComputerDTOForServlet computerDTO = new ComputerDTOForServlet();
		List<Computer> resultList = new ArrayList<>();
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(FIND_COMPUTERS_QUERY);) {

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				computerDTO.id = resultSet.getInt(1);
				computerDTO.name = resultSet.getString(2);
				computerDTO.introduced = resultSet.getString(3);
				computerDTO.discontinued = resultSet.getString(4);
				computerDTO.company_id = resultSet.getString(5);
				computerDTO.company_name = resultSet.getString(6);

				Computer computer = mappingDTO.listComputerDTOToComputerObject(computerDTO);
				
				resultList.add(computer);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return resultList;
	}

	public Optional<Computer> getComputerById(int id) {

		ComputerDTOForServlet computerDTO = new ComputerDTOForServlet();
		Optional<Computer> optionalComputer;
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(FIND_COMPUTER_QUERY)) {

			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				computerDTO.name = resultSet.getString(1);
				computerDTO.introduced = resultSet.getString(2);
				computerDTO.discontinued = resultSet.getString(3);
				computerDTO.company_id = resultSet.getString(4);
				computerDTO.company_name = resultSet.getString(5);

				Computer computer = mappingDTO.getComputerByIdDTOToComputerObject(computerDTO);

				optionalComputer = Optional.of(computer);

				return optionalComputer;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return optionalComputer = Optional.empty();
	}

	public void createComputer(Computer computer) {

		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(CREATE_COMPUTER_QUERY)) {

			ComputerDTOForDB computerDTO = mappingDTO.computerObjectToCreateComputerDTOForDB(computer);

			statement.setString(1, computerDTO.name);
			statement.setDate(2, computerDTO.introduced);
			statement.setDate(3, computerDTO.discontinued);
			statement.setString(4, computerDTO.company_id);

			statement.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	public void modifyComputer(Computer computer) {
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(MODIFY_COMPUTER_QUERY)) {

			ComputerDTOForDB computerDTO = mappingDTO.computerObjectToModifyComputerDTOForDB(computer);

			statement.setString(1, computerDTO.name);
			statement.setDate(2, computerDTO.introduced);
			statement.setDate(3, computerDTO.discontinued);
			statement.setString(4, computerDTO.company_id);
			statement.setInt(5, computerDTO.id);

			statement.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	public void deleteComputer(int id) throws DAOException {
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(DELETE_COMPUTER_QUERY)) {

			statement.setInt(1, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException("No computer with this id");
		}
	}

	public int countRows() {
		int countRows = 0;
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(COUNT_ROWS)) {

			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			countRows = resultSet.getInt(1);

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return countRows;
	}

}
