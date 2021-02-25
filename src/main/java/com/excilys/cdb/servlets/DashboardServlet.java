package main.java.com.excilys.cdb.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.service.ComputerService;

@WebServlet("/home")
public class DashboardServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private ComputerService instance = ComputerService.getInstance();
	
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp");
		
		try {
			request.setAttribute("computers", instance.getComputers());
		} catch (DAOException e) {
			request.setAttribute("computers", "An error occured");
		}
		
		dispatcher.forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}