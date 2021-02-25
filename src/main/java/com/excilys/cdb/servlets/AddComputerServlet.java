package main.java.com.excilys.cdb.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.excilys.cdb.data.Computer;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.service.CompanyService;
import main.java.com.excilys.cdb.service.ComputerService;

@WebServlet("/home/add")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	ComputerService instanceComputer = ComputerService.getInstance();
	CompanyService instanceCompany = CompanyService.getInstance();

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

		Computer computer = new Computer();
		computer.setName(request.getParameter("name"));
		computer.setIntroduced(!request.getParameter("introduced").equals("")
				? Date.valueOf(request.getParameter("introduced")).toLocalDate()
				: null);
		computer.setDiscontinued(!request.getParameter("discontinued").equals("")
				? Date.valueOf(request.getParameter("discontinued")).toLocalDate()
				: null);
		computer.setCompany_id(Integer.parseInt(request.getParameter("company_id")));
		RequestDispatcher dispatcher = null;

		try {
			instanceComputer.createComputer(computer.getName(), computer.getIntroduced(), computer.getDiscontinued(),
					computer.getCompany_id());
			response.sendRedirect("/cdb/home");
		} catch (Exception e) {
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp");
			request.setAttribute("error", true);
			request.setAttribute("errorMessage", e.getMessage());
			dispatcher.forward(request, response);

		}

	}
}
