package main.java.com.excilys.cdb.validator;

import java.sql.Date;
import java.time.LocalDate;

import main.java.com.excilys.cdb.dto.ComputerDTOForServlet;
import main.java.com.excilys.cdb.exception.ValidatorException;
import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.service.CompanyService;

public class ComputerValidator {

	private static ComputerValidator instance;
	private CompanyService controllerInstance = CompanyService.getInstance();

	public static ComputerValidator getInstance() {
		if (instance == null) {
			instance = new ComputerValidator();
		}
		return instance;
	}

	private ComputerValidator() {
	}

	public void validateComputer(ComputerDTOForServlet computerDTO) throws ValidatorException {

		int company_id;
		LocalDate introduced;
		LocalDate discontinued;

		if (computerDTO.name.equals("")) {
			throw new ValidatorException("Name must not be null");
		}

		try {
			company_id = Integer.parseInt(computerDTO.company_id);
		} catch (Exception e) {
			company_id = 0;
		}

		try {
			introduced = Date.valueOf(computerDTO.introduced).toLocalDate();
		} catch (Exception e) {
			introduced = null;
		}
		
		try {
			discontinued = Date.valueOf(computerDTO.discontinued).toLocalDate();
		} catch (Exception e) {
			discontinued = null;
		}

		if (introduced != null && introduced.isAfter(discontinued)) {
			throw new ValidatorException("Introduced date must be before discontinued date");
		}

		if (introduced == null && discontinued != null) {
			throw new ValidatorException("Cannot enter a discontinued date with no introduced date");
		}

		boolean isCompanyIdExist = false;

		if (company_id == 0) {
			isCompanyIdExist = true;
		} else {
			for (Company userlist : controllerInstance.getCompanies()) {
				if (userlist.getId() == company_id) {
					isCompanyIdExist = true;
					break;
				}
			}
		}

		if (!isCompanyIdExist) {
			throw new ValidatorException("The id of the company does not exists");
		}

	}
}
