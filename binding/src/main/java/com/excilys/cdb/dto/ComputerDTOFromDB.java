package com.excilys.cdb.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class ComputerDTOFromDB {

	@Id
	public int id;
	public String name;
	public String introduced;
	public String discontinued;
	
	@ManyToOne
	@JoinColumn(name = "company_id", nullable = true)
	public CompanyDTO company;
//
//	@Override
//	public String toString() {
//		return "ListComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
//				+ discontinued + ", company_id=" + companyId + ", company_name=" + companyName + "]";
//	}

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

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}
}
