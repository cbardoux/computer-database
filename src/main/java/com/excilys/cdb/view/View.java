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
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter a number :");
			System.out.println("0 - QUIT");
			System.out.println("1 - Computers list");
			System.out.println("2 - Companies list");
			System.out.println("3 - Show computer details");
			System.out.println("4 - Create a computer");
			System.out.println("5 - Update a computer");
			System.out.println("6 - Delete a computer");
			try {
				int userNumber = sc.nextInt();
				sc.nextLine();
				switch (userNumber) {
				case 0:
					sc.close();
					System.exit(0);
				case 1:
					System.out.println("--- Computers list ---");
					for (Computer computers : computerController.getComputers()) {
						System.out.println(computers);
					}
					break;
				case 2:
					System.out.println("--- Companies list ---");
					for (Company companies : companyController.getCompanies()) {
						System.out.println(companies);
					}
					break;
				case 3:
					System.out.println("--- Show computer details ---");
					System.out.println("Enter an id to search for :");
					int id = sc.nextInt();
					sc.nextLine();
					System.out.println(computerController.getComputerById(id));

					break;
				case 4:
					System.out.println("--- Create a computer ---");
					System.out.println("Enter a name :");
					String createName = sc.nextLine();
					System.out.println("Enter an introduced date :");
					LocalDate createIntroduced = Date.valueOf(sc.nextLine()).toLocalDate();
					System.out.println("Enter a discontinued date :");
					LocalDate createDiscontinued = Date.valueOf(sc.nextLine()).toLocalDate();
					System.out.println("Enter a company_id");
					int createCompanyId = sc.nextInt();
					sc.nextLine();
					System.out.println(computerController.createComputer(createName, createIntroduced,
							createDiscontinued, createCompanyId));

					break;
				case 5:
					System.out.println("--- Update a computer ---");
					System.out.println("Enter a computer id :");
					int modifyId = sc.nextInt();
					sc.nextLine();
					System.out.println("Enter a name :");
					String modifyName = sc.nextLine();
					System.out.println("Enter an introduced date :");
					LocalDate modifyIntroduced = Date.valueOf(sc.nextLine()).toLocalDate();
					System.out.println("Enter a discontinued date :");
					LocalDate modifyDiscontinued = Date.valueOf(sc.nextLine()).toLocalDate();
					System.out.println("Enter a company_id");
					int modifyCompanyId = sc.nextInt();
					sc.nextLine();
					System.out.println(computerController.modifyComputer(modifyId, modifyName, modifyIntroduced,
							modifyDiscontinued, modifyCompanyId));
					break;
				case 6:
					System.out.println("--- Delete a computer ---");
					System.out.println("Enter an id to delete :");
					int deleteId = sc.nextInt();
					sc.nextLine();
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
