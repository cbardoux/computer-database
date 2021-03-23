package com.excilys.cdb.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.controller.session.EditComputerSession;
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

@Controller
public class ComputerController {

	private final int INDEX_MIN = 1;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerController.class);

	private MappingDTO mapping;
	private ComputerService instanceService;
	private Page<Computer> page;
	private ComputerService instanceComputer;
	private CompanyService instanceCompany;
	private ComputerValidator instanceValidator;
	private EditComputerSession editSession;

	public ComputerController(MappingDTO mapping, ComputerService instanceService, Page<Computer> page,
			ComputerService instanceComputer, CompanyService instanceCompany, ComputerValidator instanceValidator,
			EditComputerSession editSession) {
		this.mapping = mapping;
		this.instanceCompany = instanceCompany;
		this.editSession = editSession;
		this.instanceComputer = instanceComputer;
		this.instanceService = instanceService;
		this.instanceValidator = instanceValidator;
		this.page = page;
	}

	@GetMapping(value = "/home")
	public String home(Model model, @RequestParam(required = false) String index,
			@RequestParam(required = false) String search, @RequestParam(required = false) String limitPage,
			@RequestParam(required = false) String orderBy, @RequestParam(required = false) String errorMessage) {

		setIndex(index);
		setLimitPage(limitPage);
		setSearch(search);
		setOrderBy(orderBy);

		int numberOfRows = instanceService.countRows(page);
		int limit = page.getLimit();
		int indexMax = numberOfRows % limit == 0 ? numberOfRows / limit : numberOfRows / limit + 1;

		List<ComputerDTOForServlet> computerDTOList = instanceService.getComputersWithOffset(page).getContent().stream()
				.map(computer -> mapping.computerObjectToCreateComputerDTO(computer)).collect(Collectors.toList());

		model.addAttribute("rows", numberOfRows);
		model.addAttribute("computers", computerDTOList);
		model.addAttribute("indexLow", page.valueOfIndexLow(indexMax));
		model.addAttribute("indexHigh", page.valueOfIndexHigh(indexMax));
		model.addAttribute("indexMax", indexMax);
		model.addAttribute("errorMessage", errorMessage);

		return "dashboard";
	}

	@PostMapping(value = "/home")
	public String deleteComputer(Model model, @RequestParam(required = false) String selection) {
		if (selection != null) {
			List<String> deleteIdList = Arrays.asList(selection.split(","));
			try {
				for (String id : deleteIdList) {
					instanceService.deleteComputer(Integer.parseInt(id));
				}
			} catch (NumberFormatException e1) {
				model.addAttribute("errorMessage", "Not a valid format" + e1.getMessage());
			} catch (DAOException e) {
				model.addAttribute("errorMessage", e.getMessage());
			}
		}
		return "redirect:/home";
	}

	private void setIndex(String index) {
		if (index != null) {
			try {
				page.setIndex(Integer.parseInt(index));
			} catch (NumberFormatException numberFormatExceptoin) {
				LOGGER.error(numberFormatExceptoin.getMessage());
			}
		}
	}

	private void setLimitPage(String limit) {
		if (limit != null) {
			try {
				page.setLimit(Integer.parseInt(limit));
				page.setIndex(INDEX_MIN);
			} catch (NumberFormatException numberFormatExceptoin) {
				LOGGER.error(numberFormatExceptoin.getMessage());
			}
		}
	}

	private void setOrderBy(String orderBy) {
		if (orderBy != null) {
			page.setOrderBy(orderBy);
			page.setIndex(INDEX_MIN);
		}
	}

	private void setSearch(String search) {
		if (search != null) {
			page.setSearch(search);
			page.setIndex(INDEX_MIN);
		}
	}

	@GetMapping(value = "/home/edit")
	public String editHome(Model model, @RequestParam(required = false) String id, ComputerDTOForServlet computerDTO,
			@RequestParam(required = false) String errorMessage) {
		try {
			if (id != null) {
				computerDTO = mapping.objectToCreateDTOForEdit(instanceComputer.getComputerById(Integer.parseInt(id)));
				editSession.setId(Integer.parseInt(id));
				setComputerSession(computerDTO);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		model.addAttribute("errorMessage", errorMessage);
		model.addAttribute("companies", instanceCompany.getCompanies());
		model.addAttribute("computer", editSession);
		return "editComputer";
	}

	@PostMapping(value = "/home/edit")
	public String editComputer(Model model, ComputerDTOForServlet computerDTO) {
		try {
			setComputerSession(computerDTO);
			int companyExists = instanceCompany.isCompanyExists(Integer.parseInt(computerDTO.companyId));
			instanceValidator.validateComputer(computerDTO, companyExists);
			Computer computer = mapping.modifyComputerDTOToComputerObject(computerDTO);
			instanceComputer.modifyComputer(computer);
			return "redirect:/home";
		} catch (ValidatorException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/home/edit";
	}

	private void setComputerSession(ComputerDTOForServlet computerDTO) {
		editSession.setName(computerDTO.name);
		editSession.setIntroduced(computerDTO.introduced);
		editSession.setDiscontinued(computerDTO.discontinued);
		editSession.setCompanyId(computerDTO.companyId);
		editSession.setCompanyName(computerDTO.companyName);
	}

	@GetMapping(value = "/home/add")
	public String addHome(Model model) {
		model.addAttribute("companies", instanceCompany.getCompanies());
		return "addComputer";
	}

	@PostMapping(value = "/home/add")
	public String addComputer(Model model, ComputerDTOForServlet computerDTO) {
		try {
			int companyExists = instanceCompany.isCompanyExists(Integer.parseInt(computerDTO.companyId));
			instanceValidator.validateComputer(computerDTO, companyExists);
			Computer computer = mapping.createComputerDTOToComputerObject(computerDTO);
			instanceComputer.createComputer(computer);
			return "redirect:/home";
		} catch (ValidatorException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/home/add";
	}
}