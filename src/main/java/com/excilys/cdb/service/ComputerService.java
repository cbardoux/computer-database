package main.java.com.excilys.cdb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import main.java.com.excilys.cdb.dao.ComputerDAO;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.exception.ServiceException;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.model.Page;

@Service
public class ComputerService {

	private ComputerDAO computerDAO;

	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}

	public Page<Computer> getComputersWithOffset(Page<Computer> page) {
		return computerDAO.listComputersWithOffset(page);
	}

	public List<Computer> getComputers() {
		return computerDAO.listComputers();
	}

	public Computer getComputerById(int id) throws ServiceException {
		return computerDAO.getComputerById(id)
				.orElseThrow(() -> new ServiceException("No computer found with this id"));
	}

	public void createComputer(Computer computer) {
		computerDAO.createComputer(computer);
	}

	public void modifyComputer(Computer computer) {
		computerDAO.modifyComputer(computer);
	}

	public void deleteComputer(int id) throws DAOException {
		computerDAO.deleteComputer(id);
	}

	public int countRows() {
		return computerDAO.countRows();
	}
}
