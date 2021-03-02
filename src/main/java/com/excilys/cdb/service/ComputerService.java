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
	private final int LIMIT_PAGINATION = 20;

	private ComputerService() {
	}

	public static ComputerService getInstance() {
		if (instance == null) {
			instance = new ComputerService();
		}
		return instance;
	}

	public Page<Computer> getComputersWithOffset(Page<Computer> page) throws DAOException {
		return computerDAO.listComputersWithOffset(page);
	}

	public List<Computer> getComputers() throws DAOException {
		return computerDAO.listComputers();
	}

	public Computer getComputerById(int id) throws ServiceException {
		Optional<Computer> optionalComputer = computerDAO.getComputerById(id);
		if (optionalComputer.isPresent()) {
			return optionalComputer.get();
		} else {
			throw new ServiceException();
		}

	}

	public void createComputer(Computer computer) {
		System.out.println("a" + computer);
		computerDAO.createComputer(computer);
	}

//	public void modifyComputer(int id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
//		Computer computer = new Computer.ComputerBuilder()
//				.id(id).name(name)
//				.introduced(introduced)
//				.discontinued(discontinued)
//				.company(company)
//				.build();
//
//		computerDAO.modifyComputer(computer);
//	}

	public void deleteComputer(int id) {
		computerDAO.deleteComputer(id);
	}

	public int countRows() throws DAOException {
		return computerDAO.countRows();
	}
}
