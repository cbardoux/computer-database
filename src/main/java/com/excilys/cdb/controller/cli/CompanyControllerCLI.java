package main.java.com.excilys.cdb.controller.cli;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;

import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.service.CompanyService;

@Controller
public class CompanyControllerCLI {

	private CompanyService companyService;
	
	public CompanyControllerCLI(CompanyService companyService) {
		this.companyService = companyService;
	}

	public List<Company> getCompanies() throws DAOException {
		return companyService.getCompanies();
	}

	public void deleteCompany(int id) throws DAOException, SQLException {
		companyService.deleteCompany(id);
	}
}
