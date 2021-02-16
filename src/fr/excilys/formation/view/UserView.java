package fr.excilys.formation.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import fr.excilys.formation.dao.CompanyDAO;
import fr.excilys.formation.dao.ComputerDAO;
import fr.excilys.formation.data.Company;
import fr.excilys.formation.data.Computer;

public class UserView {

	private CompanyDAO company = CompanyDAO.getInstance();
	private ComputerDAO computer = ComputerDAO.getInstance();
	
	public void launch() {
		while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter a number :");
			System.out.println("1 - Computers list");
			System.out.println("2 - Companies list");
			System.out.println("3 - Show computer details");
			System.out.println("4 - Create a computer");
			System.out.println("5 - Update a computer");
			System.out.println("6 - Delete a computer");
			try {
				int userNumber = sc.nextInt();
				switch (userNumber) {
				case 0:
					sc.close();
					System.exit(0);
				case 1:
					System.out.println("--- Computers list ---");
					List<Computer> listComputer = computer.listComputers();
					for(Computer computer: listComputer) {
						System.out.println(computer);
					}
					break;
				case 2:
					System.out.println("--- Companies list ---");
					List<Company> listCompany = company.listCompanies();
					for(Company companies: listCompany) {
						System.out.println(companies);
					}
					break;
				case 3:
					System.out.println("--- Show computer details ---");
					break;
				case 4:
					System.out.println("--- Create a computer ---");
					break;
				case 5:
					System.out.println("--- Update a computer ---");
					break;
				case 6:
					System.out.println("--- Delete a computer ---");
					break;
				default:
					System.out.println("Number not in the list");
				}
			}
			catch (InputMismatchException e)
			{
				System.out.println("Enter a number please");
			}
			
		}

	}
}
