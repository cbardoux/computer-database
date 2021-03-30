package com.excilys.cdb.controller.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.ComputerDTOForServlet;
import com.excilys.cdb.dto.MappingDTO;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.exception.ValidatorException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerValidator;

@RestController
@RequestMapping("/api/computer/")
public class ComputerRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerRestController.class);
	ComputerService serviceComputer;
	CompanyService serviceCompany;
	Page<Computer> page;
	MappingDTO mapping;
	ComputerValidator instanceValidator;

	public ComputerRestController(ComputerService serviceComputer, Page<Computer> page, MappingDTO mapping,
			CompanyService serviceCompany, ComputerValidator instanceValidator) {
		this.mapping = mapping;
		this.page = page;
		this.serviceComputer = serviceComputer;
		this.serviceCompany = serviceCompany;
		this.instanceValidator = instanceValidator;
	}

	@GetMapping(value = "/list", produces = "application/json")
	public ResponseEntity<List<ComputerDTOForServlet>> listComputers() {
		try {
			return new ResponseEntity<List<ComputerDTOForServlet>>(serviceComputer.getComputersWithOffset(page)
					.getContent().stream().map(computer -> mapping.computerObjectToCreateComputerDTO(computer))
					.collect(Collectors.toList()), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
	}

	@GetMapping(value = "/get/{id}", produces = "application/json")
	public ResponseEntity<ComputerDTOForServlet> getComputer(@PathVariable int id) {
		try {
			return new ResponseEntity<ComputerDTOForServlet>(mapping.objectToCreateDTOForEdit(serviceComputer.getComputerById(id)), HttpStatus.OK);
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@GetMapping(value = "/count", produces = "application/json")
	public ResponseEntity<Integer> count() {
		try {
			return new ResponseEntity<Integer>(serviceComputer.countRows(page), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping(value = "/add")
	public ResponseEntity<Boolean> addComputer(@RequestBody ComputerDTOForServlet computerDTO) {
		int companyExists = serviceCompany.isCompanyExists(Integer.parseInt(computerDTO.companyId));
		try {
			instanceValidator.validateComputer(computerDTO, companyExists);
			Computer computer = mapping.createComputerDTOToComputerObject(computerDTO);
			serviceComputer.createComputer(computer);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (ValidatorException e) {
			LOGGER.error(e.getMessage());
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/delete/{ids}")
	public ResponseEntity<Boolean> deleteComputer(@PathVariable int[] ids) {
		try {
			for (int id : ids) {
				serviceComputer.deleteComputer(id);
			}
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (DAOException e) {
			LOGGER.error(e.getMessage());
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/edit")
	public ResponseEntity<Boolean> editComputer(@RequestParam int id, @RequestBody ComputerDTOForServlet computerDTO) {
		int companyExists = serviceCompany.isCompanyExists(Integer.parseInt(computerDTO.companyId));
		computerDTO.setId(id);
		try {
			instanceValidator.validateComputer(computerDTO, companyExists);
			Computer computer = mapping.modifyComputerDTOToComputerObject(computerDTO);
			serviceComputer.modifyComputer(computer);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (ValidatorException e) {
			LOGGER.error(e.getMessage());
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
}
