package com.excilys.cdb.validator;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTOForServlet;
import com.excilys.cdb.exception.ValidatorException;


@Component
public class ComputerValidator {

	public void validateComputer(ComputerDTOForServlet computerDTO, int companyExists) throws ValidatorException {

		int companyId;
		LocalDate introduced;
		LocalDate discontinued;

		if (computerDTO.name.equals("")) {
			throw new ValidatorException("Name must not be null");
		}

		try {
			companyId = Integer.parseInt(computerDTO.companyId);
		} catch (Exception e) {
			companyId = 0;
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

		if (!computerDTO.companyId.equals("0") && !computerDTO.companyId.equals("") && companyExists == 0) {
			throw new ValidatorException("The id of the company does not exists");
		}
	}
}
