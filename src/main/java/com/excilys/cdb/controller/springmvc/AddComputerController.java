package main.java.com.excilys.cdb.controller.springmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import main.java.com.excilys.cdb.dto.ComputerDTOForServlet;
import main.java.com.excilys.cdb.dto.MappingDTO;
import main.java.com.excilys.cdb.exception.ValidatorException;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.service.CompanyService;
import main.java.com.excilys.cdb.service.ComputerService;
import main.java.com.excilys.cdb.validator.ComputerValidator;

@Controller
@SessionAttributes("page")
public class AddComputerController {
	
	@Autowired
	private MappingDTO mapping;

	@Autowired
	private ComputerService instanceComputer;
	
	@Autowired
	private CompanyService instanceCompany;

	@Autowired
	private ComputerValidator instanceValidator;

	@GetMapping(value = "/home/add")
	public String addHome(Model model) {
		model.addAttribute("companies", instanceCompany.getCompanies());
		return "addComputer";
	}
	
	@PostMapping(value = "/home/add")
	public String addComputer(Model model, ComputerDTOForServlet computerDTO) {
			try {
				instanceValidator.validateComputer(computerDTO);
				Computer computer = mapping.createComputerDTOToComputerObject(computerDTO);
				instanceComputer.createComputer(computer);		
				return "redirect:/home";
			} catch (ValidatorException e) {
				model.addAttribute("errorMessage", e.getMessage());
			}
			return "redirect:/home/add";
	}
}