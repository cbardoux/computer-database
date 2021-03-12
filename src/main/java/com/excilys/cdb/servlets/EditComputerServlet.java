package main.java.com.excilys.cdb.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import main.java.com.excilys.cdb.dto.ComputerDTOForServlet;
import main.java.com.excilys.cdb.dto.MappingDTO;
import main.java.com.excilys.cdb.exception.ServiceException;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.service.CompanyService;
import main.java.com.excilys.cdb.service.ComputerService;
import main.java.com.excilys.cdb.validator.ComputerValidator;

@Controller
@WebServlet("/home/edit")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ComputerService instanceComputer;

	@Autowired
	private CompanyService instanceCompany;

	@Autowired
	private ComputerValidator instanceValidator;

	@Autowired
	private MappingDTO mapping;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp");

		ComputerDTOForServlet computerDTO = new ComputerDTOForServlet();
		try {
			computerDTO = mapping.objectToCreateDTOForEdit(
					instanceComputer.getComputerById(Integer.parseInt(request.getParameter("id"))));
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", e.getMessage());
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", e.getMessage());
		}

		request.setAttribute("companies", instanceCompany.getCompanies());
		request.setAttribute("computer", computerDTO);

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

		try {
			computerDTO.id = Integer.parseInt(request.getParameter("id"));

			computerDTO.name = request.getParameter("name");
			computerDTO.introduced = request.getParameter("introduced");
			computerDTO.discontinued = request.getParameter("discontinued");
			computerDTO.companyId = request.getParameter("companyId");

			instanceValidator.validateComputer(computerDTO);

			Computer computer = mapping.modifyComputerDTOToComputerObject(computerDTO);

			instanceComputer.modifyComputer(computer);
			response.sendRedirect("/cdb/home");
		} catch (Exception e) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp");
			request.setAttribute("errorMessage", e.getMessage());
			dispatcher.forward(request, response);
		}
	}
}
