package main.java.com.excilys.cdb.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class ComputerDTOForServlet {

	@Id
	public int id;
	
	@Column(name = "name")
	public String name;
	
	@Column(name = "introduced")
	public String introduced;
	
	@Column(name = "discontinued")
	public String discontinued;
	
	@Column(name = "company_id")
	public String companyId;
	
	@Column(name = "company_name")
	public String companyName;

	@Override
	public String toString() {
		return "ListComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", company_id=" + companyId + ", company_name=" + companyName + "]";
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
