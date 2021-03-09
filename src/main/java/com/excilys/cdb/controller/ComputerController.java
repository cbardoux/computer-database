package main.java.com.excilys.cdb.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.exception.ServiceException;
import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.model.Page;
import main.java.com.excilys.cdb.service.ComputerService;

@Controller
@Scope("singleton")
public class ComputerController {

	@Autowired
	private ComputerService computerService;

	public Page<Computer> getComputersWithOffset(Page<Computer> page) {
		return computerService.getComputersWithOffset(page);
	}
	
	public List<Computer> getComputers() throws DAOException {
		return computerService.getComputers();
	}

	public Computer getComputerById(int id) throws ServiceException{
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
