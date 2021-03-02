package main.java.com.excilys.cdb.validator;

import java.sql.Date;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.excilys.cdb.controller.CompanyController;
import main.java.com.excilys.cdb.dto.ListComputerDTO;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.exception.ServiceException;
import main.java.com.excilys.cdb.exception.ValidatorException;
import main.java.com.excilys.cdb.model.Company;

public class ComputerValidator {

	private static ComputerValidator instance;
	private CompanyController controllerInstance = CompanyController.getInstance();
	ValidatorException validatorException = new ValidatorException();

	public static ComputerValidator getInstance() {
		if (instance == null) {
			instance = new ComputerValidator();
		}
		return instance;
	}

	private ComputerValidator() {
	}

	public void validateComputer(ListComputerDTO computerDTO) throws ValidatorException {

		int company_id;
		LocalDate introduced;
		LocalDate discontinued;

		if (computerDTO.name.equals("")) {
			validatorException.NoNameEntered();
			throw new ValidatorException("Name must not be null");
		}

		try {
			company_id = Integer.parseInt(computerDTO.company_id);
		} catch (Exception e) {
			company_id = 0;
		}

		try {
			introduced = Date.valueOf(computerDTO.introduced).toLocalDate();
			discontinued = Date.valueOf(computerDTO.discontinued).toLocalDate();
		} catch (Exception e1) {
			introduced = null;
			discontinued = null;
		}

		if (introduced != null && introduced.isAfter(discontinued)) {
			validatorException.IntroducedDateMustBeBeforeDiscontinued();
			throw new ValidatorException("Introduced date must be before discontinued date");
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
