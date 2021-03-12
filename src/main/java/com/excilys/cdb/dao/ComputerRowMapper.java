package main.java.com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import main.java.com.excilys.cdb.dto.ComputerDTOForServlet;

public class ComputerRowMapper implements RowMapper<ComputerDTOForServlet> {

	@Override
	public ComputerDTOForServlet mapRow(ResultSet rs, int rowNum) throws SQLException {

		ComputerDTOForServlet computerDTO = new ComputerDTOForServlet();
    	computerDTO.id = rs.getInt("computer.id");
		computerDTO.name = rs.getString("computer.name");
		computerDTO.introduced = rs.getString("computer.introduced");
		computerDTO.discontinued = rs.getString("computer.discontinued");
    	computerDTO.companyId = rs.getString("computer.company_id");
		computerDTO.companyName = rs.getString("company.name");
		return computerDTO;
	}

}
