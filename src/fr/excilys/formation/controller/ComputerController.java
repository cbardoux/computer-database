package fr.excilys.formation.controller;

import java.util.List;

import fr.excilys.formation.data.Computer;
import fr.excilys.formation.service.ComputerService;

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
}
