package main.java.com.excilys.cdb.service;

import java.time.LocalDate;
import java.util.List;

import main.java.com.excilys.cdb.dao.ComputerDAO;
import main.java.com.excilys.cdb.data.Computer;

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

	public List<Computer> getComputers(int page) {
		int offset = (page - 1) * 20;
		List<Computer> listCompanies = computerDAO.listComputers(offset);
		return listCompanies;
	}

	public Computer getComputerById(int id) {
		return computerDAO.getComputerById(id);
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
		Computer computerInBase = computerDAO.getComputerById(id);
		Computer computer = new Computer();
		computer.setId(id);
		computer.setName(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		computer.setCompany_id(company_id);

		if (computer.getName() == null) {
			computer.setName(computerInBase.getName());
		}

		computerDAO.modifyComputer(computer);
	}

	public void deleteComputer(int id) {
		computerDAO.deleteComputer(id);
	}
}
