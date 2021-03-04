package main.java.com.excilys.cdb.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.excilys.cdb.dto.ComputerDTOForServlet;
import main.java.com.excilys.cdb.dto.MappingDTO;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.exception.ServiceException;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.service.CompanyService;
import main.java.com.excilys.cdb.service.ComputerService;
import main.java.com.excilys.cdb.validator.ComputerValidator;

@WebServlet("/home/edit")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ComputerService instanceComputer = ComputerService.getInstance();
	CompanyService instanceCompany = CompanyService.getInstance();
	ComputerValidator instanceValidator = ComputerValidator.getInstance();
	MappingDTO mapping = MappingDTO.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp");

		ComputerDTOForServlet computerDTO = new ComputerDTOForServlet();
		try {
			computerDTO = mapping.objectToCreateDTOForEdit(
					instanceComputer.getComputerById(Integer.parseInt(request.getParameter("id"))));
			computerDTO.id = Integer.parseInt(request.getParameter("id"));

		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", e.getMessage());
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", e.getMessage());
		}

		request.setAttribute("companies", instanceCompany.getCompanies());
		request.setAttribute("computer", computerDTO);

		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ComputerDTOForServlet computerDTO = new ComputerDTOForServlet();

		try {
			computerDTO.id = Integer.parseInt(request.getParameter("id"));

			computerDTO.name = request.getParameter("name");
			computerDTO.introduced = request.getParameter("introduced");
			computerDTO.discontinued = request.getParameter("discontinued");
			computerDTO.company_id = request.getParameter("company_id");
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
