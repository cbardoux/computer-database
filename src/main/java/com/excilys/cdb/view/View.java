package main.java.com.excilys.cdb.view;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import main.java.com.excilys.cdb.controller.CompanyController;
import main.java.com.excilys.cdb.controller.ComputerController;
import main.java.com.excilys.cdb.dto.MappingDTO;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.exception.ServiceException;
import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.model.Page;

@Component
@Scope("prototype")
public class View {

	@Autowired
	private CompanyController companyController;

	@Autowired
	private ComputerController computerController;

	@Autowired
	private MappingDTO mapping;

	private Scanner inputUser = new Scanner(System.in);
	private static final Logger logger = LoggerFactory.getLogger(View.class);

	public void launch() {
		while (true) {

			System.out.println("Enter a number :");
			System.out.println("0 - QUIT");
			System.out.println("1 - Computers list");
			System.out.println("2 - Companies list");
			System.out.println("3 - Show computer details");
			System.out.println("4 - Create a computer");
			System.out.println("5 - Update a computer");
			System.out.println("6 - Delete a computer");
			System.out.println("7 - Delete a company");
			try {
				int userNumber = inputUser.nextInt();
				inputUser.nextLine();
				switch (userNumber) {
				case 0:
					inputUser.close();
					System.exit(0);
				case 1:
					listComputers();
					break;
				case 2:
					listCompanies();
					break;
				case 3:
					findComputerById();
					break;
				case 4:
					createComputer();
					break;
				case 5:
					modifyComputer();
					break;
				case 6:
					deleteComputer();
					break;
				case 7:
					deleteCompany();
					break;
				default:
					System.out.println("Enter a valid number");
				}
			} catch (InputMismatchException imputmInputMismatchException) {
				logger.info("Enter a number please");
			}

		}

	}

	private void deleteComputer() {
		System.out.println("--- Delete a computer ---");
		System.out.println("Enter an id to delete :");
		int deleteId = inputUser.nextInt();
		inputUser.nextLine();
		try {
			computerController.deleteComputer(deleteId);
		} catch (DAOException e) {
			logger.info("No computer with that id");
		}
		System.out.println("The computer with id " + deleteId + " has been deleted with success");
	}

	private void modifyComputer() {
		System.out.println("--- Update a computer ---");
		Computer computer = null;
		int modifyId = 0;
		while (true) {
			System.out.println("Enter a computer id :");
			modifyId = inputUser.nextInt();
			inputUser.nextLine();
			try {
				computer = computerController.getComputerById(modifyId);
				System.out.println(computer);
				break;
			} catch (ServiceException e1) {
				e1.WrongID();
			}
		}

		String modifyName = computer.getName();
		while (true) {
			System.out.println("Enter a name :");
			String inputName = inputUser.nextLine();
			if (!inputName.equals("")) {
				try {
					modifyName = inputName;
					break;
				} catch (Exception e) {
					logger.info("Please enter a valid name");
				}
			}
		}

		LocalDate modifyIntroduced = computer.getIntroduced();
		while (true) {
			System.out.println("Enter an introduced date :");
			String inputIntroduced = inputUser.nextLine();
			if (!inputIntroduced.equals("")) {
				try {
					modifyIntroduced = Date.valueOf(inputIntroduced).toLocalDate();
					break;
				} catch (Exception e) {
					logger.info("Please enter a valid date yyyy-mm-dd");
				}
			}
		}

		LocalDate modifyDiscontinued = computer.getDiscontinued();
		while (true) {
			System.out.println("Enter a discontinued date :");
			String inputDiscontinued = inputUser.nextLine();

			if (!inputDiscontinued.equals("")) {
				try {
					modifyDiscontinued = Date.valueOf(inputDiscontinued).toLocalDate();
					break;
				} catch (Exception e) {
					logger.info("Please enter a valid date yyyy-mm-dd");
				}
			}
		}

		int modifyCompanyId = computer.getCompany().getId();
		while (true) {
			System.out.println("Enter a company_id");
			String inputCompanyId = inputUser.nextLine();

			if (!inputCompanyId.equals("")) {
				try {
					modifyCompanyId = Integer.parseInt(inputCompanyId);
					break;
				} catch (Exception e) {
					logger.info("Please enter a valid company id");
				}
			}
		}
		Company company = new Company();
		company.setId(modifyCompanyId);

		computerController.modifyComputer(modifyId, modifyName, modifyIntroduced, modifyDiscontinued, company);
		try {
			System.out.println(computerController.getComputerById(modifyId));
		} catch (ServiceException e) {
			e.WrongID();
		}
	}

	private void createComputer() {
		System.out.println("--- Create a computer ---");
		System.out.println("Enter a name :");
		String createName;
		while (true) {
			createName = inputUser.nextLine();
			if (createName.equals("")) {
				System.out.println("Enter a name please");
			} else {
				break;
			}
		}

		LocalDate createIntroduced = null;

		while (true) {
			System.out.println("Enter an introduced date yyyy-mm-dd :");
			String introduced = inputUser.nextLine();
			if (!introduced.equals("")) {
				try {
					createIntroduced = Date.valueOf(introduced).toLocalDate();
					break;
				} catch (Exception e) {
					logger.info("Please enter a valid date yyyy-mm-dd");
				}
			} else {
				break;
			}
		}

		LocalDate createDiscontinued = null;
		while (true) {
			System.out.println("Enter a discontinued date yyyy-mm-dd :");
			String discontinued = inputUser.nextLine();

			if (!discontinued.equals("")) {
				try {
					createDiscontinued = Date.valueOf(discontinued).toLocalDate();
					break;
				} catch (Exception e) {
					logger.info("Please enter a valid date yyyy-mm-dd");
				}
			} else {
				break;
			}
		}

		int companyId = 0;
		while (true) {
			try {
				System.out.println("Enter a company_id");
				String createCompanyId = inputUser.nextLine();

				if (!createCompanyId.equals("")) {
					companyId = Integer.parseInt(createCompanyId);
				}
				break;
			} catch (Exception e) {
				logger.info("Please enter a valid company id");
			}
		}
		Company company = new Company();
		company.setId(companyId);

		computerController.createComputer(createName, createIntroduced, createDiscontinued, company);
		System.out.println("The computer has been created with success");
	}

	private void findComputerById() {
		System.out.println("--- Show computer details ---");
		System.out.println("Enter an id to search for :");
		int id = inputUser.nextInt();
		inputUser.nextLine();
		try {
			Computer computer = computerController.getComputerById(id);
			computer.setId(id);
			System.out.println(computer);
		} catch (ServiceException e) {
			e.WrongID();
		}
	}

	private void listCompanies() {
		System.out.println("--- Companies list ---");
		int pageNumberCompany;
		while (true) {
			System.out.println("Enter 0 to quit or a page number :");
			try {
				pageNumberCompany = inputUser.nextInt();
			} catch (Exception e1) {
				pageNumberCompany = 0;
			}
			inputUser.nextLine();
			if (pageNumberCompany == 0) {
				break;
			} else {
				try {
					companyController.getCompaniesWithOffset(pageNumberCompany).stream().forEach(System.out::println);
				} catch (DAOException e) {
					e.WrongPageNumber();
				}
			}
		}
	}

	private void listComputers() {
		System.out.println("--- Computers list ---");
		int pageNumberComputer;
		while (true) {
			System.out.println("Enter 0 to quit or a page number :");
			pageNumberComputer = inputUser.nextInt();
			inputUser.nextLine();
			Page<Computer> page = new Page<>();
			page.setIndex(pageNumberComputer);
			page.setLimit(10);

			if (pageNumberComputer == 0) {
				break;
			} else {
				computerController.getComputersWithOffset(page).getContent().stream()
						.map(computer -> mapping.computerObjectToCreateComputerDTO(computer))
						.forEach(System.out::println);
			}
		}
	}

	public void deleteCompany() {
		System.out.println("--- Delete a company ---");
		System.out.println("Enter an id to delete :");
		int deleteId = inputUser.nextInt();
		inputUser.nextLine();
		try {
			companyController.deleteCompany(deleteId);
		} catch (DAOException e) {
			logger.info("No computer with that id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("The company with id " + deleteId + " has been deleted with success");
	}

}
