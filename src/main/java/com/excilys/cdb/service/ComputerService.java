package main.java.com.excilys.cdb.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import main.java.com.excilys.cdb.dao.ComputerDAO;
import main.java.com.excilys.cdb.data.Computer;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.exception.ServiceException;

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

	public List<Computer> getComputers(int page) throws DAOException {
		int offset = (page - 1) * LIMIT_PAGINATION;

		return computerDAO.listComputers(offset);

	}

	public Computer getComputerById(int id) throws ServiceException {
		Optional<Computer> optionalComputer = computerDAO.getComputerById(id);
		if (optionalComputer != null) {
			return optionalComputer.get();
		} else {
			throw new ServiceException();
		}

	}

	public void createComputer(String name, LocalDate introduced, LocalDate discontinued, int company_id) {
		Computer computer = new Computer();
		computer.setName(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		computer.setCompany_id(company_id);

		computerDAO.createComputer(computer);
	}

	public void modifyComputer(int id, String name, LocalDate introduced, LocalDate discontinued, int company_id) {
		Computer computer = new Computer();
		computer.setId(id);
		computer.setName(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		computer.setCompany_id(company_id);

		computerDAO.modifyComputer(computer);
	}

	public void deleteComputer(int id) {
		computerDAO.deleteComputer(id);
	}
}
