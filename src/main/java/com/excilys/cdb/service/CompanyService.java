package main.java.com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import main.java.com.excilys.cdb.dao.CompanyDAO;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Company;

@Service
@Scope("singleton")
public class CompanyService {
	private final int LIMIT_PAGINATION = 20;
	
	@Autowired
	private CompanyDAO companyDAO;

	public List<Company> getCompaniesWithOffset(int page) {
		List<Company> listCompanies = new ArrayList<>();

		int offset = (page - 1) * LIMIT_PAGINATION;
		listCompanies = companyDAO.listCompaniesWithOffset(offset);

		return listCompanies;
	}
	
	public List<Company> getCompanies() {
		return companyDAO.listCompanies();
	}
	
	public void deleteCompany(int id) throws DAOException, SQLException {
		companyDAO.deleteCompany(id);
	}

}
