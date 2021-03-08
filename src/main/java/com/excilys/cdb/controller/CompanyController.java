package main.java.com.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.List;

import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.service.CompanyService;

public class CompanyController {

	private static CompanyController instanceController = null;
	private CompanyService companyService = CompanyService.getInstance();

	private CompanyController() {
	}

	public static CompanyController getInstance() {
		if (instanceController == null) {
			instanceController = new CompanyController();
		}
		return instanceController;
	}

	public List<Company> getCompaniesWithOffset(int page) throws DAOException {
		return companyService.getCompaniesWithOffset(page);
	}

	public List<Company> getCompanies() throws DAOException {
		return companyService.getCompanies();
	}

	public void deleteCompany(int id) throws DAOException, SQLException {
		companyService.deleteCompany(id);
	}
}
