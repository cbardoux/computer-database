package main.java.com.excilys.cdb.controller.springmvc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import main.java.com.excilys.cdb.dto.ComputerDTOForServlet;
import main.java.com.excilys.cdb.dto.MappingDTO;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.model.Page;
import main.java.com.excilys.cdb.service.ComputerService;
import main.java.com.excilys.cdb.servlets.AddComputerServlet;

@Controller
@SessionAttributes("page")
public class DashboardController {

	private final int INDEX_MIN = 1;
	private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerServlet.class);

	@Autowired
	private MappingDTO mapping;

	@Autowired
	private ComputerService instanceService;

	@Autowired
	private Page<Computer> page;

	@GetMapping(value = "/home", produces = MediaType.TEXT_PLAIN_VALUE)
	public String home(Model model, @RequestParam(required = false) String index,
			@RequestParam(required = false) String search, @RequestParam(required = false) String limitPage,
			@RequestParam(required = false) String orderBy, @RequestParam(required = false) String errorMessage) {
		setIndex(index);
		setLimitPage(limitPage);
		setSearch(search);
		setOrderBy(orderBy);
		int numberOfRows = instanceService.countRows();

		int limit = page.getLimit();
		int indexMax = numberOfRows % limit == 0 ? numberOfRows / limit : numberOfRows / limit + 1;

		List<ComputerDTOForServlet> computerDTOList = instanceService.getComputersWithOffset(page).getContent().stream()
				.map(computer -> mapping.computerObjectToCreateComputerDTO(computer)).collect(Collectors.toList());
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
			try {
				page.setOrderBy(orderBy);
				page.setIndex(INDEX_MIN);
			} catch (NumberFormatException numberFormatExceptoin) {
				LOGGER.error(numberFormatExceptoin.getMessage());
			}
		}
	}

	private void setSearch(String search) {
		if (search != null) {
			page.setSearch(search);
			page.setIndex(INDEX_MIN);
		}
	}
}