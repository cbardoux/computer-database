package main.java.com.excilys.cdb.validator;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.com.excilys.cdb.dao.CompanyDAO;
import main.java.com.excilys.cdb.dto.ComputerDTOForServlet;
import main.java.com.excilys.cdb.exception.ValidatorException;
import main.java.com.excilys.cdb.model.Company;

@Component
public class ComputerValidator {

	@Autowired
	private CompanyDAO companyDAOInstance;

	public void validateComputer(ComputerDTOForServlet computerDTO) throws ValidatorException {

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

		if (!computerDTO.companyId.equals("0") && companyDAOInstance.isCompanyExists(companyId) == 0) {
			throw new ValidatorException("The id of the company does not exists");
		}
	}
}
