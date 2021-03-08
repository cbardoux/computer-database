package main.java.com.excilys.cdb.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.excilys.cdb.dao.ComputerDAO;
import main.java.com.excilys.cdb.dto.ComputerDTOForServlet;
import main.java.com.excilys.cdb.dto.MappingDTO;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.exception.ValidatorException;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.service.CompanyService;
import main.java.com.excilys.cdb.service.ComputerService;
import main.java.com.excilys.cdb.validator.ComputerValidator;

@WebServlet("/home/add")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	ComputerService instanceComputerService = ComputerService.getInstance();
	CompanyService instanceCompany = CompanyService.getInstance();
	MappingDTO instanceMapping = MappingDTO.getInstance();
	ComputerValidator instanceValidator = ComputerValidator.getInstance();
	private static final Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp");

		request.setAttribute("companies", instanceCompany.getCompanies());

		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ComputerDTOForServlet computerDTO = new ComputerDTOForServlet();

		computerDTO.name = request.getParameter("name");
		computerDTO.introduced = request.getParameter("introduced");
		computerDTO.discontinued = request.getParameter("discontinued");
		computerDTO.company_id = request.getParameter("company_id");

		try {
			instanceValidator.validateComputer(computerDTO);

			Computer computer = instanceMapping.createComputerDTOToComputerObject(computerDTO);

			instanceComputerService.createComputer(computer);
			response.sendRedirect("/cdb/home");
		} catch (Exception e) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp");
			request.setAttribute("errorMessage", e.getMessage());
			dispatcher.forward(request, response);
			logger.error(e.getMessage());
		}

	}
}
