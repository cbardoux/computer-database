package main.java.com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer {

	public static class ComputerBuilder {

		private int id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company;

		public ComputerBuilder() {

		}

		public ComputerBuilder id(int id) {
			this.id = id;
			return this;
		}

		public ComputerBuilder name(String name) {
			this.name = name;
			return this;
		}

		public ComputerBuilder introduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerBuilder discontinued(LocalDate discontinued) {

			this.discontinued = discontinued;
			return this;
		}

		public ComputerBuilder company(Company company) {
			this.company = company;
			return this;
		}

		public Computer build() {
			Computer computerTestBuilder = new Computer();
			computerTestBuilder.id = this.id;
			computerTestBuilder.name = this.name;
			computerTestBuilder.introduced = this.introduced;
			computerTestBuilder.discontinued = this.discontinued;
			computerTestBuilder.company = this.company;
			return computerTestBuilder;
		}

	}

	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;

	private Computer() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "id : " + id + " | Computer : " + name + " | Introduced : " + introduced + " | Discontinued : "
				+ discontinued + " | ID Company : " + company;
	}

}
