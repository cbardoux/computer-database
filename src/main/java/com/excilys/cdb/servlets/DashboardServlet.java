package main.java.com.excilys.cdb.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.excilys.cdb.controller.ComputerController;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.model.Page;
import main.java.com.excilys.cdb.service.ComputerService;

@WebServlet("/home")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ComputerService instanceService = ComputerService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp");
		
		try {
			request.setAttribute("rows", instanceService.countRows());
			int indexPage = 1;
			if (request.getParameter("index") != null) {
				try {
					indexPage = Integer.parseInt(request.getParameter("index"));
				} catch (NumberFormatException numberFormatExceptoin) {
					request.setAttribute("errorMessage", "An error occured");
				}
			}
			request.setAttribute("computers", instanceService.getComputersWithOffset(new Page<Computer>(indexPage, 10)).getContent());
			//request.setAttribute("index", indexPage);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
//			request.setAttribute("rows", instanceService.countRows());
//			request.setAttribute("computers", instanceService.getComputers());
//		} catch (DAOException e) {
//			request.setAttribute("errorMessage", "An error occured");
//		}

		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}