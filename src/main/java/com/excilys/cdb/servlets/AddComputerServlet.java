package main.java.com.excilys.cdb.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import main.java.com.excilys.cdb.dto.ComputerDTOForServlet;
import main.java.com.excilys.cdb.dto.MappingDTO;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.service.CompanyService;
import main.java.com.excilys.cdb.service.ComputerService;
import main.java.com.excilys.cdb.validator.ComputerValidator;

@Controller
@WebServlet("/home/add")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ComputerService instanceComputerService;

	@Autowired
	private CompanyService instanceCompany;

	@Autowired
	private ComputerValidator instanceValidator;

	@Autowired
	private MappingDTO instanceMapping;

	private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp");

		request.setAttribute("companies", instanceCompany.getCompanies());

		dispatcher.forward(request, response);

	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
		super.init(config);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ComputerDTOForServlet computerDTO = new ComputerDTOForServlet();

		computerDTO.name = request.getParameter("name");
		computerDTO.introduced = request.getParameter("introduced");
		computerDTO.discontinued = request.getParameter("discontinued");
		computerDTO.companyId = request.getParameter("companyId");

		try {
			instanceValidator.validateComputer(computerDTO);

			Computer computer = instanceMapping.createComputerDTOToComputerObject(computerDTO);

			instanceComputerService.createComputer(computer);
			response.sendRedirect("/cdb/home");
		} catch (Exception e) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp");
			request.setAttribute("errorMessage", e.getMessage());
			dispatcher.forward(request, response);
			LOGGER.error(e.getMessage());
		}

	}
}
