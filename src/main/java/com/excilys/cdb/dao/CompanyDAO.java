package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Company;

@Repository
@Scope("singleton")
public class CompanyDAO {

	@Autowired
	private DBConnection instanceDB;
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	private static final String FIND_COMPANIES_WITH_OFFSET_QUERY = "SELECT id, name FROM company LIMIT ?, 20;";
	private static final String FIND_COMPANIES_QUERY = "SELECT id, name FROM company;";
	private static final String DELETE_COMPANY_QUERY = "DELETE FROM company WHERE id = ?;";
	private static final String DELETE_COMPUTER_WITH_COMPANY_ID_QUERY = "DELETE FROM computer WHERE company_id = ?;";

	public List<Company> listCompaniesWithOffset(int offset) {

		List<Company> resultList = new ArrayList<>();
		try (Connection connection = instanceDB.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_COMPANIES_WITH_OFFSET_QUERY);) {

			statement.setInt(1, offset);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Company company = new Company(resultSet.getInt(1), resultSet.getString(2));

				resultList.add(company);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return resultList;
	}

	public List<Company> listCompanies() {

		List<Company> resultList = new ArrayList<>();
		try (Connection connection = instanceDB.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_COMPANIES_QUERY);) {

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Company company = new Company(resultSet.getInt(1), resultSet.getString(2));

				resultList.add(company);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return resultList;
	}

	public void deleteCompany(int id) throws SQLException, DAOException {
		Connection connection = this.instanceDB.getConnection();
		try (PreparedStatement statementDeleteComputer = connection.prepareStatement(DELETE_COMPUTER_WITH_COMPANY_ID_QUERY);
				PreparedStatement statementDeleteCompany = connection
						.prepareStatement(DELETE_COMPANY_QUERY);) {
			
			connection.setAutoCommit(false);

			statementDeleteComputer.setInt(1, id);
			statementDeleteComputer.executeUpdate();

			statementDeleteCompany.setInt(1, id);
			statementDeleteCompany.executeUpdate();

			connection.commit();
		} catch (SQLException errorSQL) {
			try {
				connection.rollback();
			} catch (SQLException errorRollBack) {
				logger.error(errorRollBack.getMessage());
			}
			logger.error(errorSQL.getMessage());
			throw new DAOException(errorSQL.getMessage());
		} finally {
			connection.close();
		}
	}
}
