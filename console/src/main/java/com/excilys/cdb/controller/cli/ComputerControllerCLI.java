package com.excilys.cdb.controller.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.dto.ComputerDTOForServlet;
import com.excilys.cdb.dto.MappingDTO;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.view.View;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ComputerControllerCLI {

	private ComputerService computerService;
	private MappingDTO mapping;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerControllerCLI.class);

	public ComputerControllerCLI(ComputerService computerService, MappingDTO mapping) {
		this.computerService = computerService;
		this.mapping = mapping;
	}

	public Page<Computer> getComputersWithOffset(Page<Computer> page) {
		return computerService.getComputersWithOffset(page);
	}
	
	public List<Computer> getComputers(){
		Runtime runtime = Runtime.getRuntime();
		List<Computer> list = null;
		InputStream is;
		try {
			is = runtime.exec("curl -X GET localhost:8080/webapp/api/list").getInputStream();
			try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			return this.stringToComputer(jsonText);
		} finally {
			is.close();
		}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return list;
	}

	public Computer getComputerById(int id) throws ServiceException {
		return computerService.getComputerById(id);
	}

	public void createComputer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
		Computer computer = new Computer.ComputerBuilder().name(name).introduced(introduced).discontinued(discontinued)
				.company(company).build();
		computerService.createComputer(computer);
	}

	public void modifyComputer(int id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
		Computer computer = new Computer.ComputerBuilder().id(id).name(name).introduced(introduced)
				.discontinued(discontinued).company(company).build();
		computerService.modifyComputer(computer);
	}

	public void deleteComputer(int id) throws DAOException, IOException {
		String command = "curl -X DELETE localhost:8080/webapp/api/deleteComputer/" + id;
		Runtime.getRuntime().exec(command);

	}
	
	private String readAll(BufferedReader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}
	
	private List<Computer> stringToComputer(String stringComputers) {
		List<ComputerDTOForServlet> listComputerDTO = null;
		List<Computer> listComputers = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		try {
			listComputerDTO = mapper.readValue(stringComputers, new TypeReference<List<ComputerDTOForServlet>>(){});
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage());
		}
		for(ComputerDTOForServlet computerDTO : listComputerDTO) {
			listComputers.add(mapping.createComputerDTOToComputerObject(computerDTO));
		}
		return listComputers;
	}
}
