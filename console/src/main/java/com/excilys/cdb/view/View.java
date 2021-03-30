package com.excilys.cdb.view;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.controller.cli.CompanyControllerCLI;
import com.excilys.cdb.controller.cli.ComputerControllerCLI;
import com.excilys.cdb.dto.MappingDTO;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component
@Scope("prototype")
public class View {

	private CompanyControllerCLI companyController;
	private ComputerControllerCLI computerController;

	public View(CompanyControllerCLI companyController, ComputerControllerCLI computerController) {
		this.companyController = companyController;
		this.computerController = computerController;
	}

	private Scanner inputUser = new Scanner(System.in);
	private static final Logger LOGGER = LoggerFactory.getLogger(View.class);

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
				LOGGER.info("Enter a number please");
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
				e1.wrongID();
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
					LOGGER.info("Please enter a valid name");
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
					LOGGER.info("Please enter a valid date yyyy-mm-dd");
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
					LOGGER.info("Please enter a valid date yyyy-mm-dd");
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
					LOGGER.info("Please enter a valid company id");
				}
			}
		}
		Company company = new Company();
		company.setId(modifyCompanyId);

		computerController.modifyComputer(modifyId, modifyName, modifyIntroduced, modifyDiscontinued, company);
		try {
			System.out.println(computerController.getComputerById(modifyId));
		} catch (ServiceException e) {
			e.wrongID();
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
					LOGGER.info("Please enter a valid date yyyy-mm-dd");
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
					LOGGER.info("Please enter a valid date yyyy-mm-dd");
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
				LOGGER.info("Please enter a valid company id");
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
			e.wrongID();
		}
	}

	private void listCompanies() {
		System.out.println("--- Companies list ---");
		try {
			companyController.getCompanies().stream().forEach(System.out::println);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	private void listComputers() {
		System.out.println("--- Computers list ---");
		System.out.println(computerController.getComputers());
	}

	public void deleteCompany() {
		System.out.println("--- Delete a company ---");
		System.out.println("Enter an id to delete :");
		int deleteId = inputUser.nextInt();
		inputUser.nextLine();
		try {
			companyController.deleteCompany(deleteId);
		} catch (DAOException e) {
			LOGGER.info("No computer with that id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("The company with id " + deleteId + " has been deleted with success");
	}

}
