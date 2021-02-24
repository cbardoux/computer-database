package main.java.com.excilys.cdb.controller;

import java.time.LocalDate;
import java.util.List;

import main.java.com.excilys.cdb.data.Computer;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.exception.ServiceException;
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

	public List<Computer> getComputersWithOffset(int page) throws DAOException {
		return computerService.getComputersWithOffset(page);
	}

	public Computer getComputerById(int id) throws ServiceException{
		return computerService.getComputerById(id);
	}

	public void createComputer(String name, LocalDate introduced, LocalDate discontinued, int company_id) {
		computerService.createComputer(name, introduced, discontinued, company_id);
	}

	public void modifyComputer(int id, String name, LocalDate introduced, LocalDate discontinued, int company_id) {
		computerService.modifyComputer(id, name, introduced, discontinued, company_id);
	}

	public void deleteComputer(int id) {
		computerService.deleteComputer(id);
	}
}
