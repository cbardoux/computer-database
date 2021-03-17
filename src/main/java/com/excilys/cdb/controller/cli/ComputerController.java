package main.java.com.excilys.cdb.controller.cli;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.exception.ServiceException;
import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.model.Page;
import main.java.com.excilys.cdb.service.ComputerService;

@Component
public class ComputerController {

	private ComputerService computerService;
	
	public ComputerController(ComputerService computerService) {
		this.computerService = computerService;
	}

	public Page<Computer> getComputersWithOffset(Page<Computer> page) {
		return computerService.getComputersWithOffset(page);
	}

	public Computer getComputerById(int id) throws ServiceException {
		return computerService.getComputerById(id);
	}

	public void createComputer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
		Computer computer = new Computer.ComputerBuilder()
				.name(name)
				.introduced(introduced)
				.discontinued(discontinued)
				.company(company)
				.build();
		computerService.createComputer(computer);
	}

	public void modifyComputer(int id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
		Computer computer = new Computer.ComputerBuilder()
				.id(id)
				.name(name)
				.introduced(introduced)
				.discontinued(discontinued)
				.company(company)
				.build();
		computerService.modifyComputer(computer);
	}

	public void deleteComputer(int id) throws DAOException {
		computerService.deleteComputer(id);
	}
}
