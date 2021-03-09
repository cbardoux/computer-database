package main.java.com.excilys.cdb.validator;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import main.java.com.excilys.cdb.dto.ComputerDTOForServlet;
import main.java.com.excilys.cdb.exception.ValidatorException;
import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.service.CompanyService;

@Component
@Scope("singleton")
public class ComputerValidator {

	@Autowired
	private CompanyService controllerInstance;

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

		if (!computerDTO.introduced.equals("")) {
			introduced = Date.valueOf(computerDTO.introduced).toLocalDate();
		} else {
			introduced = null;
		}

		if (!computerDTO.discontinued.equals("")) {
			discontinued = Date.valueOf(computerDTO.discontinued).toLocalDate();
		} else {
			discontinued = null;
		}

		if (introduced == null && discontinued != null) {
			throw new ValidatorException("Cannot enter a discontinued date with no introduced date");
		}

		if (introduced != null && discontinued != null && introduced.isAfter(discontinued)) {
			throw new ValidatorException("Introduced date must be before discontinued date");
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
