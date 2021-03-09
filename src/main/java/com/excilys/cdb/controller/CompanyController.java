package main.java.com.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.service.CompanyService;

@Controller
@Scope("singleton")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

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
