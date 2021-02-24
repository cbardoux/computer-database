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
import main.java.com.excilys.cdb.exception.ServiceException;
import main.java.com.excilys.cdb.service.ComputerService;

@WebServlet("/home/edit")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static boolean base = true;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	ComputerService instance = ComputerService.getInstance();
	//ResaService resaService = ResaService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp");
		

		Computer computer = new Computer();
		computer.setId(Integer.parseInt(request.getParameter("id")));

				try {
					computer = instance.getComputerById(Integer.parseInt(request.getParameter("id")));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		request.setAttribute("id", Integer.parseInt(request.getParameter("id")));
		request.setAttribute("name", computer.getName());
		request.setAttribute("introduced", computer.getIntroduced());
		request.setAttribute("discontinued", computer.getDiscontinued());
		request.setAttribute("company_id", computer.getCompany_id());
		
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		
		Computer computer = new Computer();
		computer.setId(Integer.parseInt(request.getParameter("id")));
		computer.setName(request.getParameter("name"));
		computer.setIntroduced(Date.valueOf(request.getParameter("introduced")).toLocalDate());
		computer.setDiscontinued(Date.valueOf(request.getParameter("discontinued")).toLocalDate());
		computer.setCompany_id(Integer.parseInt(request.getParameter("company_id")));
			RequestDispatcher dispatcher = null;
			
			try {
				instance.modifyComputer(computer.getId(), computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getCompany_id());
				response.sendRedirect("/rentmanager/users");
			} catch (Exception e) {
				dispatcher = request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp");
				request.setAttribute("errorMessage", e.getMessage());
				request.setAttribute("id", Integer.parseInt(request.getParameter("id")));
				request.setAttribute("name", computer.getName());
				request.setAttribute("introduced", computer.getIntroduced());
				request.setAttribute("discontinued", computer.getDiscontinued());
				request.setAttribute("company_id", computer.getCompany_id());
				dispatcher.forward(request,  response);
				
			}
			
	}
}
