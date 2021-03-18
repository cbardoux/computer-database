package main.java.com.excilys.cdb.controller.cli;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.service.CompanyService;

@Component
public class CompanyController {

	private CompanyService companyService;
	
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	public List<Company> getCompanies() throws DAOException {
		return companyService.getCompanies();
	}

	public void deleteCompany(int id) throws DAOException, SQLException {
		companyService.deleteCompany(id);
	}
}
