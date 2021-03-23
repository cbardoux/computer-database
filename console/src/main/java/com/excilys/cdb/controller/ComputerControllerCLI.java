package com.excilys.cdb.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@Controller
public class ComputerControllerCLI {

	private ComputerService computerService;
	
	public ComputerControllerCLI(ComputerService computerService) {
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
