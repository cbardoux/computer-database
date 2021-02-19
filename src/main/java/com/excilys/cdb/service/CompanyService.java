package main.java.com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.cdb.dao.CompanyDAO;
import main.java.com.excilys.cdb.data.Company;

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

	public List<Company> getCompanies(int page) {
		List<Company> listCompanies = new ArrayList<>();
		try {
			int offset = (page - 1) * LIMIT_PAGINATION;
			listCompanies = companyDAO.listCompanies(offset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCompanies;
	}

}
