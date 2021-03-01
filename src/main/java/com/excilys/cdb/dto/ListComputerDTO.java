package main.java.com.excilys.cdb.dto;

public class ListComputerDTO {

	public int id;
	public String name;
	public String introduced;
	public String discontinued;
	public String company_id;
	public String company_name;
	
	@Override
	public String toString() {
		return "ListComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", company_id=" + company_id + ", company_name=" + company_name + "]";
	}

}
