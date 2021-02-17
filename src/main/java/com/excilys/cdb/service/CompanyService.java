package main.java.com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.cdb.dao.CompanyDAO;
import main.java.com.excilys.cdb.data.Company;

public class CompanyService {
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

	public List<Company> getCompanies() {
		List<Company> listCompanies = new ArrayList<>();
		try {
			listCompanies = companyDAO.listCompanies();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listCompanies;
	}

}
