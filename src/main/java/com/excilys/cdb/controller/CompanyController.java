package main.java.com.excilys.cdb.controller;

import java.util.List;

import main.java.com.excilys.cdb.data.Company;
import main.java.com.excilys.cdb.exception.DAOException;
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

	public List<Company> getCompanies(int page) throws DAOException{
		return companyService.getCompanies(page);
	}

}
