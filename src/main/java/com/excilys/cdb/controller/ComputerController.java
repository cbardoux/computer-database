package main.java.com.excilys.cdb.controller;

import java.time.LocalDate;
import java.util.List;

import main.java.com.excilys.cdb.data.Computer;
import main.java.com.excilys.cdb.service.ComputerService;

public class ComputerController {
	private static ComputerController instanceController = null;
	private ComputerService computerService = ComputerService.getInstance();
	
	private ComputerController() {}
	
	public static ComputerController getInstance() {
		if (instanceController == null) {
			instanceController = new ComputerController();
		}
		return instanceController;
	}
	
	public List<Computer> getComputers() {
		return computerService.getComputers();
	}
	
	public Computer getComputerById(int id) {
		return computerService.getComputerById(id);
	}
	
	public long createComputer(String name, LocalDate introduced, LocalDate discontinued, int company_id) {
		return computerService.createComputer(name, introduced, discontinued, company_id);
	}
	
	public long modifyComputer(int id, String name, LocalDate introduced, LocalDate discontinued, int company_id) {
		return computerService.modifyComputer(id, name, introduced, discontinued, company_id);
	}
	
	public long deleteComputer(int id) {
		return computerService.deleteComputer(id);
	}
}