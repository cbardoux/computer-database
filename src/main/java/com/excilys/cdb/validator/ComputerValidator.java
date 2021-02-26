package main.java.com.excilys.cdb.validator;

import java.time.LocalDate;

import main.java.com.excilys.cdb.controller.CompanyController;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.exception.ValidatorException;
import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.model.Computer;

public class ComputerValidator {

	private static ComputerValidator instance;
	private CompanyController controllerInstance = CompanyController.getInstance();

	public static ComputerValidator getInstance() {
		if (instance == null) {
			instance = new ComputerValidator();
		}
		return instance;
	}

	private ComputerValidator() {
	}

	public void validateComputer(Computer Computer) throws ValidatorException {

		String name = Computer.getName();
		LocalDate introduced = Computer.getIntroduced();
		LocalDate discontinued = Computer.getDiscontinued();
		int company_id = Computer.getCompany_id();

		if (introduced.isAfter(discontinued)) {
			throw new ValidatorException("Introduced date must be before discontinnued date");
		}

		if (name == null) {
			throw new ValidatorException("Name must not be null");
		}

		boolean isCompanyIdExist = false;

		if (company_id == 0) {
			isCompanyIdExist = true;
		} else {
			try {
				for (Company userlist : controllerInstance.getCompanies()) {
					if (userlist.getId() == company_id) {
						isCompanyIdExist = true;
						break;
					}
				}
			} catch (DAOException e) {
				e.CompanyListError();
			}
		}

		if (!isCompanyIdExist) {
			throw new ValidatorException("The id of the company does not exists");
		}

	}
}
