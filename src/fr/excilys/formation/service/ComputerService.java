package fr.excilys.formation.service;

import java.util.List;

import fr.excilys.formation.dao.ComputerDAO;
import fr.excilys.formation.data.Computer;

public class ComputerService {
	private static ComputerService instance = null;
	private ComputerDAO computerDAO = ComputerDAO.getInstance();

	private ComputerService() {}

	public static ComputerService getInstance() {
		if (instance == null) {
			instance = new ComputerService();
		}
		return instance;
	}

	public List<Computer> getComputers() {
		List<Computer> listCompanies = computerDAO.listComputers();
		return listCompanies;
	}
	
	public Computer getComputerById(int id) {
		return computerDAO.getComputerById(id);
	}
}
