package fr.excilys.formation.service;

import java.util.ArrayList;
import java.util.List;

import fr.excilys.formation.dao.CompanyDAO;
import fr.excilys.formation.data.Company;

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
