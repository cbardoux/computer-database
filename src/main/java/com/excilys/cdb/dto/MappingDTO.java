package main.java.com.excilys.cdb.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.model.Computer;

@Component
public class MappingDTO {

	private DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

	public Computer listComputerDTOToComputerObject(ComputerDTOForServlet computerDTO) {

		Company company = new Company();
		LocalDate introduced;
		LocalDate discontinued;
		int companyId;
		String companyName;

		try {
			introduced = stringToLocalDate(computerDTO.introduced, formatterTime);
		} catch (Exception e1) {
			introduced = null;
		}

		try {
			discontinued = stringToLocalDate(computerDTO.discontinued, formatterTime);
		} catch (Exception e1) {
			discontinued = null;
		}

		try {
			companyId = Integer.parseInt(computerDTO.companyId);
			companyName = computerDTO.companyName;
			company.setId(companyId);
			company.setName(companyName);
		} catch (Exception e) {
			company = null;
		}

		Computer computer = new Computer.ComputerBuilder().id(computerDTO.id).name(computerDTO.name)
				.introduced(introduced).discontinued(discontinued).company(company).build();

		return computer;
	}

	public Computer getComputerByIdDTOToComputerObject(ComputerDTOForServlet computerDTO) {

		Company company = new Company();
		LocalDate introduced;
		LocalDate discontinued;

		try {
			introduced = stringToLocalDate(computerDTO.introduced, formatterTime);
		} catch (Exception e) {
			introduced = null;
		}

		try {
			discontinued = stringToLocalDate(computerDTO.discontinued, formatterTime);
		} catch (Exception e) {
			discontinued = null;
		}

		try {
			company.setId(Integer.parseInt(computerDTO.companyId));
		} catch (NumberFormatException e) {
			company.setId(0);
		}

		company.setName(computerDTO.companyName);

		Computer computer = new Computer.ComputerBuilder().id(computerDTO.id).name(computerDTO.name).introduced(introduced)
				.discontinued(discontinued).company(company).build();

		return computer;
	}

	public Computer createComputerDTOToComputerObject(ComputerDTOForServlet computerDTO) {

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
			company.setId(Integer.parseInt(computerDTO.companyId));
		} catch (NumberFormatException e) {
			company.setId(0);
		}

		Computer computer = new Computer.ComputerBuilder().name(computerDTO.name).introduced(introduced)
				.discontinued(discontinued).company(company).build();

		return computer;
	}

	public Computer modifyComputerDTOToComputerObject(ComputerDTOForServlet computerDTO) {

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
			company.setId(Integer.parseInt(computerDTO.companyId));
		} catch (NumberFormatException e) {
			company.setId(0);
		}

		Computer computer = new Computer.ComputerBuilder().id(computerDTO.id).name(computerDTO.name)
				.introduced(introduced).discontinued(discontinued).company(company).build();

		return computer;
	}

	public ComputerDTOForDB computerObjectToCreateComputerDTOForDB(Computer computer) {

		ComputerDTOForDB computerDTO = new ComputerDTOForDB();

		computerDTO.id = computer.getId();
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
			computerDTO.companyId = null;
		} else {
			computerDTO.companyId = Integer.toString(computer.getCompany().getId());
		}

		return computerDTO;
	}

	public ComputerDTOForDB computerObjectToModifyComputerDTOForDB(Computer computer) {

		ComputerDTOForDB computerDTO = new ComputerDTOForDB();

		computerDTO.id = computer.getId();

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
			computerDTO.companyId = null;
		} else {
			computerDTO.companyId = Integer.toString(computer.getCompany().getId());
		}

		return computerDTO;
	}

	public ComputerDTOForServlet computerObjectToCreateComputerDTO(Computer computer) {

		ComputerDTOForServlet computerDTO = new ComputerDTOForServlet();

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
			computerDTO.companyName = computer.getCompany().getName();
		} catch (NullPointerException e) {
			computerDTO.companyName = null;
		}

		return computerDTO;
	}

	public ComputerDTOForServlet objectToCreateDTOForEdit(Computer computer) {

		ComputerDTOForServlet computerDTO = new ComputerDTOForServlet();

		computerDTO.id = computer.getId();
		computerDTO.name = computer.getName();

		if (computer.getIntroduced() != null) {
			computerDTO.introduced = computer.getIntroduced().toString();
		} else {
			computerDTO.introduced = null;
		}

		if (computer.getDiscontinued() != null) {
			computerDTO.discontinued = computer.getDiscontinued().toString();
		} else {
			computerDTO.discontinued = null;
		}

		try {
			computerDTO.companyId = Integer.toString(computer.getCompany().getId());
			computerDTO.companyName = computer.getCompany().getName();
		} catch (NullPointerException e) {
			computerDTO.companyId = null;
			computerDTO.companyName = null;
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
