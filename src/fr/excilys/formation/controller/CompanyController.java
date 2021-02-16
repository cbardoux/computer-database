package fr.excilys.formation.controller;

import java.util.List;

import fr.excilys.formation.data.Company;
import fr.excilys.formation.service.CompanyService;

public class CompanyController {

	private static CompanyController instanceController = null;
	private CompanyService companyService = CompanyService.getInstance();

	private CompanyController() {
	}

	public static CompanyController getInstance() {
		if (instanceController == null) {
			instanceController = new CompanyController();
		}
		return instanceController;
	}

	public List<Company> getCompanies(){
		return companyService.getCompanies();
	}

}
