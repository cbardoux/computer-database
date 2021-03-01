package main.java.com.excilys.cdb.dto;

import java.sql.Date;

public class ComputerDTOForDB {
	public String name;
	public Date introduced;
	public Date discontinued;
	public String company_id;
	
	@Override
	public String toString() {
		return "ComputerDTOForDB [name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company_id=" + company_id + "]";
	}
		
}
