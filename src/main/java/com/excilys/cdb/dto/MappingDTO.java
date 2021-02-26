package main.java.com.excilys.cdb.dto;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

import main.java.com.excilys.cdb.model.Computer;

public class MappingDTO {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");

	public Computer computerDTOToComputerObject(ComputerDTO computerDTO) {

		stringToLocalDate(computerDTO.getIntroduced());

		stringToLocalDate(computerDTO.getDiscontinued());

		if (computerDTO != null) {
			try {
				Date.valueOf(introduced).toLocalDate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return computer;
	}

	private void stringToLocalDate(String date) {
		if (date != null) {
			try {
				Date.valueOf(date).toLocalDate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
