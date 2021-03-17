package main.java.com.excilys.cdb.controller.springmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import main.java.com.excilys.cdb.dto.ComputerDTOForServlet;
import main.java.com.excilys.cdb.dto.MappingDTO;
import main.java.com.excilys.cdb.exception.ServiceException;
import main.java.com.excilys.cdb.exception.ValidatorException;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.service.CompanyService;
import main.java.com.excilys.cdb.service.ComputerService;
import main.java.com.excilys.cdb.validator.ComputerValidator;

@Controller
public class EditComputerController {

	@Autowired
	private MappingDTO mapping;

	@Autowired
	private ComputerService instanceComputer;

	@Autowired
	private CompanyService instanceCompany;

	@Autowired
	private ComputerValidator instanceValidator;

	@GetMapping(value = "/home/edit")
	public String editHome(Model model, @RequestParam(required = false) String id, ComputerDTOForServlet computerDTO,
			@RequestParam(required = false) String errorMessage) {
		try {
			computerDTO = mapping.objectToCreateDTOForEdit(instanceComputer.getComputerById(Integer.parseInt(id)));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		model.addAttribute("errorMessage", errorMessage);
		model.addAttribute("companies", instanceCompany.getCompanies());
		model.addAttribute("computer", computerDTO);
		return "editComputer";
	}

	@PostMapping(value = "/home/edit")
	public String editComputer(Model model, ComputerDTOForServlet computerDTO) {
		try {
			instanceValidator.validateComputer(computerDTO);
			Computer computer = mapping.modifyComputerDTOToComputerObject(computerDTO);
			instanceComputer.modifyComputer(computer);
			return "redirect:/home";
		} catch (ValidatorException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/home/edit?id=" + computerDTO.id;
	}
}
