package main.java.com.excilys.cdb.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import main.java.com.excilys.cdb.dao.ComputerDAO;
import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.model.Computer;

public class MappingDTO {

	// private DateTimeFormatter formatter =
	// DateTimeFormatter.ofPattern("yyyy-mm-dd");
	
	private static MappingDTO instance = null;
	
	public static MappingDTO getInstance() {
		if (instance == null) {
			instance = new MappingDTO();
		}
		return instance;
	}

	public Computer computerDTOToComputerObject(ComputerDTO computerDTO) {

		Company company = new Company();
		LocalDate introduced;
		LocalDate discontinued;
		int company_id;
		String company_name;

		introduced = stringToLocalDate(computerDTO.getIntroduced());

		discontinued = stringToLocalDate(computerDTO.getDiscontinued());

		try {
			company_id = Integer.parseInt(computerDTO.getCompany_id());
			company_name = computerDTO.getCompany_name();
			company.setId(company_id);
			company.setName(company_name);
		} catch (Exception e) {
			company = null;
		}

		Computer computer = new Computer.ComputerBuilder()
				.id(computerDTO.getId())
				.name(computerDTO.getName())
				.introduced(introduced)
				.discontinued(discontinued)
				.company(company)
				.build();

		return computer;
	}

	private LocalDate stringToLocalDate(String stringDate) {
		LocalDate date;
		try {
			date = Date.valueOf(stringDate).toLocalDate();
		} catch (Exception e) {
			date = null;
		}

		return date;
	}
	
}
