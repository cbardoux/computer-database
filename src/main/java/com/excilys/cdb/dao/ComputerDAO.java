package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.com.excilys.cdb.dto.ComputerDTOForDB;
import main.java.com.excilys.cdb.dto.ListComputerDTO;
import main.java.com.excilys.cdb.dto.MappingDTO;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Computer;

public class ComputerDAO {
	private static ComputerDAO instanceComputer = null;
	private DBConnection instanceDB = null;
	private MappingDTO mappingDTO = MappingDTO.getInstance();

	private ComputerDAO() {
		instanceDB = DBConnection.getInstance();
	}

	public static ComputerDAO getInstance() {
		if (instanceComputer == null) {
			instanceComputer = new ComputerDAO();
		}
		return instanceComputer;
	}

	private static final String FIND_COMPUTERS_WITH_OFFSET_QUERY = "SELECT id, name, introduced, discontinued, company_id FROM computer LIMIT ?, 20;";
	private static final String FIND_COMPUTERS_QUERY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = computer.company_id;";
	private static final String FIND_COMPUTER_QUERY = "SELECT computer.name, computer.introduced, computer.discontinued, company.name FROM computer LEFT JOIN company ON company.id = computer.company_id WHERE computer.id = ?;";
	private static final String CREATE_COMPUTER_QUERY = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private static final String MODIFY_COMPUTER_QUERY = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
	private static final String DELETE_COMPUTER_QUERY = "DELETE FROM computer WHERE id = ?;";

//	public List<Computer> listComputersWithOffset(int offset) throws DAOException {
//
//		List<Computer> resultList = new ArrayList<>();
//		try (Connection connection = instanceDB.connection();
//				PreparedStatement statement = connection.prepareStatement(FIND_COMPUTERS_WITH_OFFSET_QUERY);) {
//
//			statement.setInt(1, offset);
//			ResultSet resultSet = statement.executeQuery();
//
//			while (resultSet.next()) {
//				Computer computer = new Computer(resultSet.getInt(1), resultSet.getString(2),
//						resultSet.getDate(3) != null ? resultSet.getDate(3).toLocalDate() : null,
//						resultSet.getDate(4) != null ? resultSet.getDate(4).toLocalDate() : null, resultSet.getInt(5));
//
//				resultList.add(computer);
//
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException();
//		}
//		return resultList;
//	}

	public List<Computer> listComputers() throws DAOException {
		ListComputerDTO computerDTO = new ListComputerDTO();
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
			throw new DAOException();
		}
		return resultList;
	}

	public Optional<Computer> getComputerById(int id) {

		ListComputerDTO computerDTO = new ListComputerDTO();
		Optional<Computer> optionalComputer;
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(FIND_COMPUTER_QUERY)) {

			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				computerDTO.name = resultSet.getString(1);
				computerDTO.introduced = resultSet.getString(2);
				computerDTO.discontinued = resultSet.getString(3);
				computerDTO.company_name = resultSet.getString(4);
				
				Computer computer = mappingDTO.getComputerByIdDTOToComputerObject(computerDTO);
				optionalComputer = Optional.of(computer);
				return optionalComputer;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public void createComputer(Computer computer) {
		
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(CREATE_COMPUTER_QUERY)) {
			
			System.out.println("b" + computer);
			ComputerDTOForDB computerDTO = mappingDTO.computerObjectToCreateComputerDTO(computer);
			System.out.println("c" + computerDTO);
			statement.setString(1, computerDTO.name);
			statement.setDate(2, computerDTO.introduced);
			statement.setDate(3, computerDTO.discontinued);
			statement.setString(4, computerDTO.company_id);

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
