package main.java.com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import main.java.com.excilys.cdb.dao.ComputerDAO;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.exception.ServiceException;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.model.Page;

public class ComputerService {
	private static ComputerService instance = null;
	private ComputerDAO computerDAO = ComputerDAO.getInstance();

	private ComputerService() {
	}

	public static ComputerService getInstance() {
		if (instance == null) {
			instance = new ComputerService();
		}
		return instance;
	}

	public Page<Computer> getComputersWithOffset(Page<Computer> page) {
		return computerDAO.listComputersWithOffset(page);
	}

	public List<Computer> getComputers() {
		return computerDAO.listComputers();
	}

	public Computer getComputerById(int id) throws ServiceException {
		Optional<Computer> optionalComputer = Optional.empty();

		optionalComputer = computerDAO.getComputerById(id);

		if (optionalComputer.isPresent()) {
			return optionalComputer.get();
		} else {
			throw new ServiceException("No computer found with this id");
		}
	}

	public void createComputer(Computer computer) {
		computerDAO.createComputer(computer);
	}

	public void modifyComputer(Computer computer) {
		computerDAO.modifyComputer(computer);
	}

	public void deleteComputer(int id) {
		computerDAO.deleteComputer(id);
	}

	public int countRows() {
		return computerDAO.countRows();
	}
}
