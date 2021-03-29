package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.ComputerDTOForServlet;
import com.excilys.cdb.dto.MappingDTO;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.exception.ValidatorException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerValidator;

@RestController
@RequestMapping("/api")
public class ApiController {

	ComputerService serviceComputer;
	CompanyService serviceCompany;
	Page<Computer> page;
	MappingDTO mapping;
	ComputerValidator instanceValidator;

	public ApiController(ComputerService serviceComputer, Page<Computer> page, MappingDTO mapping,
			CompanyService serviceCompany, ComputerValidator instanceValidator) {
		this.mapping = mapping;
		this.page = page;
		this.serviceComputer = serviceComputer;
		this.serviceCompany = serviceCompany;
		this.instanceValidator = instanceValidator;
	}

	@GetMapping(value = "/list", produces = "application/json")
	public List<ComputerDTOForServlet> listcomputers() {
		List<ComputerDTOForServlet> computerDTOList = serviceComputer.getComputersWithOffset(page).getContent().stream()
				.map(computer -> mapping.computerObjectToCreateComputerDTO(computer)).collect(Collectors.toList());

		return computerDTOList;
	}

	@GetMapping(value = "/count", produces = "application/json")
	public int count() {
		return serviceComputer.countRows(page);
	}

	@PostMapping(value = "/add")
	public void addComputer(@RequestBody ComputerDTOForServlet computerDTO) {
		int companyExists = serviceCompany.isCompanyExists(Integer.parseInt(computerDTO.companyId));
		try {
			instanceValidator.validateComputer(computerDTO, companyExists);
			Computer computer = mapping.createComputerDTOToComputerObject(computerDTO);
			serviceComputer.createComputer(computer);
		} catch (ValidatorException e) {
			e.printStackTrace();
		}
	}

	@PostMapping(value = "/deleteComputer")
	public void deleteComputer(@RequestParam int id) {
		try {
			serviceComputer.deleteComputer(id);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	@PostMapping(value = "/deleteCompany")
	public void deleteCompany(@RequestParam int id) {
		try {
			serviceCompany.deleteCompany(id);
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@PutMapping(value = "/edit")
	public void editComputer(@RequestParam int id, @RequestBody ComputerDTOForServlet computerDTO) {
		int companyExists = serviceCompany.isCompanyExists(Integer.parseInt(computerDTO.companyId));
		computerDTO.setId(id);
		try {
			instanceValidator.validateComputer(computerDTO, companyExists);
			Computer computer = mapping.modifyComputerDTOToComputerObject(computerDTO);
			serviceComputer.modifyComputer(computer);
		} catch (ValidatorException e) {
			e.printStackTrace();
		}
	}
}
