package main.java.com.excilys.cdb.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.excilys.cdb.dto.ListComputerDTO;
import main.java.com.excilys.cdb.dto.MappingDTO;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.service.CompanyService;
import main.java.com.excilys.cdb.service.ComputerService;

@WebServlet("/home/add")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	ComputerService instanceComputerService = ComputerService.getInstance();
	CompanyService instanceCompany = CompanyService.getInstance();
	MappingDTO instanceMapping = MappingDTO.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp");

		try {
			request.setAttribute("companies", instanceCompany.getCompanies());
		} catch (DAOException e) {
			request.setAttribute("computers", "An error occured");
		}
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ListComputerDTO computerDTO = new ListComputerDTO();
		
		
		computerDTO.setName(request.getParameter("name"));
		computerDTO.setIntroduced(request.getParameter("introduced"));
		computerDTO.setDiscontinued(request.getParameter("discontinued"));
		computerDTO.setCompany_id(request.getParameter("company_id"));
		
		System.out.println(computerDTO);
		Computer computer = instanceMapping.createComputerDTOToComputerObject(computerDTO);
		System.out.println(computer);
		
		RequestDispatcher dispatcher = null;

		try {
			instanceComputerService.createComputer(computer);
			response.sendRedirect("/cdb/home");
		} catch (Exception e) {
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp");
			request.setAttribute("error", true);
			request.setAttribute("errorMessage", e.getMessage());
			dispatcher.forward(request, response);

		}

	}
}
