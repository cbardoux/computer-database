package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Company;

public class CompanyDAO {
	private static CompanyDAO instanceCompany = null;
	private DBConnection instanceDB = null;

	private CompanyDAO() {
		this.instanceDB = DBConnection.getInstance();
	}

	public static CompanyDAO getInstance() {
		if (instanceCompany == null) {
			instanceCompany = new CompanyDAO();
		}
		return instanceCompany;
	}

	private static final String FIND_COMPANIES_WITH_OFFSET_QUERY = "SELECT id, name FROM company LIMIT ?, 20;";
	private static final String FIND_COMPANIES_QUERY = "SELECT id, name FROM company;";

	public List<Company> listCompaniesWithOffset(int offset) throws DAOException {

		List<Company> resultList = new ArrayList<>();
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(FIND_COMPANIES_WITH_OFFSET_QUERY);) {

			statement.setInt(1, offset);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Company company = new Company(resultSet.getInt(1), resultSet.getString(2));

				resultList.add(company);
			}

		} catch (SQLException e) {
			throw new DAOException();
		}
		return resultList;
	}
	
	public List<Company> listCompanies() throws DAOException {

		List<Company> resultList = new ArrayList<>();
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(FIND_COMPANIES_QUERY);) {

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Company company = new Company(resultSet.getInt(1), resultSet.getString(2));

				resultList.add(company);
			}

		} catch (SQLException e) {
			throw new DAOException();
		}
		return resultList;
	}
}
