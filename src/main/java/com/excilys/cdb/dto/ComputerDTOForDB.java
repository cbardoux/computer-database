package main.java.com.excilys.cdb.dto;

import java.sql.Date;

public class ComputerDTOForDB {
	public int id;
	public String name;
	public Date introduced;
	public Date discontinued;
	public String companyId;
	
	@Override
	public String toString() {
		return "ComputerDTOForDB [name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company_id=" + companyId + "]";
	}
		
}
