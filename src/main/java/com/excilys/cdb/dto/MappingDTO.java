package main.java.com.excilys.cdb.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.model.Computer;

public class MappingDTO {

	private DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
	private DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

		introduced = stringToLocalDate(computerDTO.getIntroduced(), formatterTime);

		discontinued = stringToLocalDate(computerDTO.getDiscontinued(), formatterTime);

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
	
	public Computer getComputerByIdDTOToComputerObject(ListComputerDTO computerDTO) {

		Company company = new Company();
		LocalDate introduced;
		LocalDate discontinued;		

		introduced = stringToLocalDate(computerDTO.getIntroduced(), formatterTime);

		discontinued = stringToLocalDate(computerDTO.getDiscontinued(), formatterTime);
		
		company.setName(computerDTO.getName());
		
		Computer computer = new Computer.ComputerBuilder()
				.name(computerDTO.getName())
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

		introduced = stringToLocalDate(computerDTO.getIntroduced(), formatterDate);

		discontinued = stringToLocalDate(computerDTO.getDiscontinued(), formatterDate);
		

		try {
			company.setId(Integer.parseInt(computerDTO.getCompany_id()));
		} catch (NumberFormatException e) {
			company.setId(0);
		}

		
		Computer computer = new Computer.ComputerBuilder()
				.name(computerDTO.getName())
				.introduced(introduced)
				.discontinued(discontinued)
				.company(company)
				.build();

		return computer;
	}
	
	public ListComputerDTO computerObjectToCreateComputerDTO(Computer computer) {
		ListComputerDTO computerDTO = new ListComputerDTO();
				
		computerDTO.setName(computer.getName());
		computerDTO.setIntroduced(computer.getIntroduced().toString());
		computerDTO.setDiscontinued(computer.getDiscontinued().toString());
		if(computer.getCompany().getId() == 0) {
			computerDTO.setCompany_id(null);
		} else {
			computerDTO.setCompany_id(Integer.toString(computer.getCompany().getId()));
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
