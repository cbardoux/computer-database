package main.java.com.excilys.cdb.dto;

import java.sql.Date;

public class GetComputerByIdDTO {
	private String name;
	private Date introduced;
	private Date discontinued;
	private String company_id;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}
	public Date getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_name(String company_id) {
		this.company_id = company_id;
	}
}
