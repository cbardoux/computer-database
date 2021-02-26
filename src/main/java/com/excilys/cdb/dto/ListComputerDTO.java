package main.java.com.excilys.cdb.dto;

public class ListComputerDTO {

	private int id;
	private String name;
	private String introduced;
	private String discontinued;
	private String company_id;
	private String company_name;
	
	@Override
	public String toString() {
		return "ListComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", company_id=" + company_id + ", company_name=" + company_name + "]";
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
	
	public String getIntroduced() {
		return introduced;
	}
	
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	
	public String getDiscontinued() {
		return discontinued;
	}
	
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	
	public String getCompany_id() {
		return company_id;
	}
	
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	
	public String getCompany_name() {
		return company_name;
	}
	
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
}
