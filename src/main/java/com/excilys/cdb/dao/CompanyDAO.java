package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.cdb.data.Company;

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

	private static final String FIND_COMPANIES_QUERY = "SELECT id, name FROM company LIMIT ?, 20;";

	public List<Company> listCompanies(int offset) {

		List<Company> resultList = new ArrayList<>();
		try (Connection connection = instanceDB.connection();
				PreparedStatement statement = connection.prepareStatement(FIND_COMPANIES_QUERY);) {

			statement.setInt(1, offset);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Company company = new Company(resultSet.getInt(1), resultSet.getString(2));

				resultList.add(company);
			}

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}
}
