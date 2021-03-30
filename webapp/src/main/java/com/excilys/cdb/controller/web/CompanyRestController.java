package com.excilys.cdb.controller.web;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;

@RestController
@RequestMapping("/api/company/")
public class CompanyRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerRestController.class);
	CompanyService serviceCompany;

	public CompanyRestController(CompanyService serviceCompany) {
		this.serviceCompany = serviceCompany;
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<Boolean> deleteCompany(@RequestParam int id) {
		try {
			serviceCompany.deleteCompany(id);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (DAOException e) {
			LOGGER.error(e.getMessage());
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_GATEWAY);
		}
	}

	@GetMapping(value = "/list")
	public ResponseEntity<List<Company>> getCompanies() {
		try {
			return new ResponseEntity<List<Company>>(serviceCompany.getCompanies(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}

	}
}
