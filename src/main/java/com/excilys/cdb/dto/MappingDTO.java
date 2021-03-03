package main.java.com.excilys.cdb.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.model.Computer;

public class MappingDTO {

	private DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

	private static MappingDTO instance = null;

	public static MappingDTO getInstance() {
		if (instance == null) {
			instance = new MappingDTO();
		}
		return instance;
	}

	public Computer listComputerDTOToComputerObject(ListComputerDTO computerDTO) {

		Company company = new Company();
		LocalDate introduced;
		LocalDate discontinued;
		int company_id;
		String company_name;

		introduced = stringToLocalDate(computerDTO.introduced, formatterTime);

		discontinued = stringToLocalDate(computerDTO.discontinued, formatterTime);

		try {
			company_id = Integer.parseInt(computerDTO.company_id);
			company_name = computerDTO.company_name;
			company.setId(company_id);
			company.setName(company_name);
		} catch (Exception e) {
			company = null;
		}

		Computer computer = new Computer.ComputerBuilder()
				.id(computerDTO.id)
				.name(computerDTO.name)
				.introduced(introduced)
				.discontinued(discontinued)
				.company(company)
				.build();

		return computer;
	}

	public Computer getComputerByIdDTOToComputerObject(ListComputerDTO computerDTO) {

		Company company = new Company();
		LocalDate introduced;
		LocalDate discontinued;

		introduced = stringToLocalDate(computerDTO.introduced, formatterTime);

		discontinued = stringToLocalDate(computerDTO.discontinued, formatterTime);

		company.setName(computerDTO.name);

		Computer computer = new Computer.ComputerBuilder()
				.name(computerDTO.name)
				.introduced(introduced)
				.discontinued(discontinued)
				.company(company)
				.build();

		return computer;
	}

	public Computer createComputerDTOToComputerObject(ListComputerDTO computerDTO) {

		Company company = new Company();
		LocalDate introduced;
		LocalDate discontinued;

		
			try {
				introduced = Date.valueOf(computerDTO.introduced).toLocalDate();
			} catch (Exception e) {
				introduced = null;
			}
		
			try {
				discontinued = Date.valueOf(computerDTO.discontinued).toLocalDate();
			} catch (Exception e1) {
				discontinued = null;
			}
		

		try {
			company.setId(Integer.parseInt(computerDTO.company_id));
		} catch (NumberFormatException e) {
			company.setId(0);
		}

		Computer computer = new Computer
				.ComputerBuilder()
				.name(computerDTO.name)
				.introduced(introduced)
				.discontinued(discontinued)
				.company(company)
				.build();

		return computer;
	}

	public ComputerDTOForDB computerObjectToCreateComputerDTOForDB(Computer computer) {

		ComputerDTOForDB computerDTO = new ComputerDTOForDB();

		computerDTO.name = computer.getName();

		try {
			computerDTO.introduced = Date.valueOf(computer.getIntroduced());
		} catch (Exception e) {
			computerDTO.introduced = null;
		}
		
		try {
			computerDTO.discontinued = Date.valueOf(computer.getDiscontinued());
		} catch (Exception e) {
			computerDTO.discontinued = null;
		}
		
		if (computer.getCompany().getId() == 0) {
			computerDTO.company_id = null;
		} else {
			computerDTO.company_id = Integer.toString(computer.getCompany().getId());
		}


		return computerDTO;
	}
	
	public ListComputerDTO computerObjectToCreateComputerDTO(Computer computer) {

		ListComputerDTO computerDTO = new ListComputerDTO();


		computerDTO.id = computer.getId();
		computerDTO.name = computer.getName();
		
		try {
			computerDTO.introduced = computer.getIntroduced().toString();
		} catch (NullPointerException e) {
			computerDTO.introduced = null;
		}
		
		try {
			computerDTO.discontinued = computer.getDiscontinued().toString();
		} catch (NullPointerException e1) {
			computerDTO.discontinued = null;
		}
		
		try {
			computerDTO.company_name = computer.getCompany().getName();
		} catch (NullPointerException e) {
			computerDTO.company_name = null;
		}
		

		return computerDTO;
	}

	private LocalDate stringToLocalDate(String stringDate, DateTimeFormatter formatter) {
		LocalDate date;
		try {
			date = LocalDate.parse(stringDate, formatter);
		} catch (Exception e) {
			date = null;
		}
		return date;
	}
}
