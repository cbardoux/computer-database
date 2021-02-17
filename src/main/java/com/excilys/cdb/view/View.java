package main.java.com.excilys.cdb.view;

import java.sql.Date;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import main.java.com.excilys.cdb.controller.CompanyController;
import main.java.com.excilys.cdb.controller.ComputerController;
import main.java.com.excilys.cdb.data.Company;
import main.java.com.excilys.cdb.data.Computer;

public class View {

	private CompanyController companyController = CompanyController.getInstance();
	private ComputerController computerController = ComputerController.getInstance();

	public void launch() {
		while (true) {
			Scanner inputUser = new Scanner(System.in);
			System.out.println("Enter a number :");
			System.out.println("0 - QUIT");
			System.out.println("1 - Computers list");
			System.out.println("2 - Companies list");
			System.out.println("3 - Show computer details");
			System.out.println("4 - Create a computer");
			System.out.println("5 - Update a computer");
			System.out.println("6 - Delete a computer");
			try {
				int userNumber = inputUser.nextInt();
				inputUser.nextLine();
				switch (userNumber) {
				case 0:
					inputUser.close();
					System.exit(0);
				case 1:
					System.out.println("--- Computers list ---");
					boolean loopPageComputer = true;
					int pageNumberComputer;
					while (loopPageComputer) {
						System.out.println("Enter a page number :");
						pageNumberComputer = inputUser.nextInt();
						inputUser.nextLine();
						System.out.println("--- Page number " + pageNumberComputer + " ---");
						for (Computer computers : computerController.getComputers(pageNumberComputer)) {
							System.out.println(computers);
						}
					}
					break;
				case 2:
					System.out.println("--- Companies list ---");
					boolean loopPageCompany = true;
					int pageNumberCompany;
					while (loopPageCompany) {
						System.out.println("Enter a page number :");
						pageNumberCompany = inputUser.nextInt();
						inputUser.nextLine();
						System.out.println("--- Page number " + pageNumberCompany + " ---");
						for (Company companies : companyController.getCompanies(pageNumberCompany)) {
							System.out.println(companies);
						}
					}

					break;
				case 3:
					System.out.println("--- Show computer details ---");
					System.out.println("Enter an id to search for :");
					int id = inputUser.nextInt();
					inputUser.nextLine();
					System.out.println(computerController.getComputerById(id));

					break;
				case 4:
					System.out.println("--- Create a computer ---");
					System.out.println("Enter a name :");
					String createName = inputUser.nextLine();
					System.out.println("Enter an introduced date :");
					String introduced = inputUser.nextLine();
					LocalDate createIntroduced = null;
					if (!introduced.equals("")) {
						try {
							createIntroduced = Date.valueOf(introduced).toLocalDate();
						} catch (Exception e) {
							System.out.println("Please enter a valid date or press Enter");
						}
					}
					System.out.println("Enter a discontinued date :");
					String discontinued = inputUser.nextLine();
					LocalDate createDiscontinued = null;
					if (!discontinued.equals("")) {
						try {
							createDiscontinued = Date.valueOf(discontinued).toLocalDate();
						} catch (Exception e) {
							System.out.println("Please enter a valid date or press Enter");
						}
					}
					System.out.println("Enter a company_id");
					
					String createCompanyId = inputUser.nextLine();
					int companyId = 0;

					if (!createCompanyId.equals("")) {
						try {
							companyId = Integer.parseInt(createCompanyId);
						} catch (NumberFormatException e) {
							System.out.println("Please enter a valid number or press Enter");
						}
					}
					System.out.println(companyId);
					System.out.println(computerController.createComputer(createName, createIntroduced,
							createDiscontinued, companyId));

					break;
				case 5:
					System.out.println("--- Update a computer ---");
					System.out.println("Enter a computer id :");
					int modifyId = inputUser.nextInt();
					inputUser.nextLine();
					System.out.println("Enter a name :");
					String modifyName = inputUser.nextLine();
					System.out.println("Enter an introduced date :");
					LocalDate modifyIntroduced = Date.valueOf(inputUser.nextLine()).toLocalDate();
					System.out.println("Enter a discontinued date :");
					LocalDate modifyDiscontinued = Date.valueOf(inputUser.nextLine()).toLocalDate();
					System.out.println("Enter a company_id");
					int modifyCompanyId = inputUser.nextInt();
					inputUser.nextLine();
					System.out.println(computerController.modifyComputer(modifyId, modifyName, modifyIntroduced,
							modifyDiscontinued, modifyCompanyId));
					break;
				case 6:
					System.out.println("--- Delete a computer ---");
					System.out.println("Enter an id to delete :");
					int deleteId = inputUser.nextInt();
					inputUser.nextLine();
					System.out.println(computerController.deleteComputer(deleteId));
					break;
				default:
					System.out.println("Number not in the list");
				}
			} catch (InputMismatchException imputmInputMismatchException) {
				System.out.println("Enter a valid number please");
			}

		}

	}
}
