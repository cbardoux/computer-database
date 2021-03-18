package main.java.com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import main.java.com.excilys.cdb.dao.CompanyDAO;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Company;

@Service
public class CompanyService {

	private CompanyDAO companyDAO;

	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public List<Company> getCompanies() {
		return companyDAO.listCompanies();
	}

	public void deleteCompany(int id) throws DAOException, SQLException {
		companyDAO.deleteCompany(id);
	}

}
