package main.java.com.excilys.cdb.controller;

import java.time.LocalDate;
import java.util.List;

import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.exception.ServiceException;
import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.service.ComputerService;

public class ComputerController {
	private static ComputerController instanceController = null;
	private ComputerService computerService = ComputerService.getInstance();

	private ComputerController() {
	}

	public static ComputerController getInstance() {
		if (instanceController == null) {
			instanceController = new ComputerController();
		}
		return instanceController;
	}

//	public List<Computer> getComputersWithOffset(int page) throws DAOException {
//		return computerService.getComputersWithOffset(page);
//	}
	
	public List<Computer> getComputers() throws DAOException {
		return computerService.getComputers();
	}

	public Computer getComputerById(int id) throws ServiceException{
		return computerService.getComputerById(id);
	}

//	public void createComputer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
//		computerService.createComputer(name, introduced, discontinued, company);
//	}
//
//	public void modifyComputer(int id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
//		computerService.modifyComputer(id, name, introduced, discontinued, company);
//	}

	public void deleteComputer(int id) {
		computerService.deleteComputer(id);
	}
}
