package main.java.com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.cdb.dao.CompanyDAO;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Company;

public class CompanyService {
	private final int LIMIT_PAGINATION = 20;
	private static CompanyService instance = null;
	private CompanyDAO companyDAO = CompanyDAO.getInstance();

	private CompanyService() {
	}

	public static CompanyService getInstance() {
		if (instance == null) {
			instance = new CompanyService();
		}
		return instance;
	}

	public List<Company> getCompaniesWithOffset(int page) {
		List<Company> listCompanies = new ArrayList<>();

		int offset = (page - 1) * LIMIT_PAGINATION;
		listCompanies = companyDAO.listCompaniesWithOffset(offset);

		return listCompanies;
	}
	
	public List<Company> getCompanies() {
		return companyDAO.listCompanies();
	}

}
