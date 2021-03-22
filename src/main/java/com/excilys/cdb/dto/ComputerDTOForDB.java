package main.java.com.excilys.cdb.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class ComputerDTOForDB {
	
	@Id
	public int id;
	
	@Column(name = "name")
	public String name;
	
	@Column(name = "introduced")
	public Date introduced;
	
	@Column(name = "discontinued")
	public Date discontinued;
	
	@Column(name = "company_id")
	public String companyId;

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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "ComputerDTOForDB [name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company_id=" + companyId + "]";
	}

}
