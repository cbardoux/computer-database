package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;

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
