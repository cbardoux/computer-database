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
			System.out.println("a" + request.getParameter("id"));
			computerDTO = mapping.objectToCreateDTOForEdit(
					instanceComputer.getComputerById(Integer.parseInt(request.getParameter("id"))));
			System.out.println("b" + computerDTO);
			computerDTO.id = Integer.parseInt(request.getParameter("id"));
			
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", e.getMessage());
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", e.getMessage());
		}

//		try {
//			request.setAttribute("companies", instanceCompany.getCompanies());
//			computer = instance.getComputerById(Integer.parseInt(request.getParameter("id")));
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

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

//		Computer computer = new Computer();
//		computer.setId(Integer.parseInt(request.getParameter("id")));
//		computer.setName(request.getParameter("name"));
//		computer.setIntroduced(
//				!request.getParameter("introduced").equals("") || !request.getParameter("introduced").equals(null)
//						? Date.valueOf(request.getParameter("introduced")).toLocalDate()
//						: null);
//		computer.setDiscontinued(
//				!request.getParameter("discontinued").equals("") || !request.getParameter("discontinued").equals(null)
//						? Date.valueOf(request.getParameter("discontinued")).toLocalDate()
//						: null);
//		computer.setCompany_id(Integer.parseInt(request.getParameter("company_id")));
//		RequestDispatcher dispatcher = null;
//
//		try {
//			instance.modifyComputer(computer.getId(), computer.getName(), computer.getIntroduced(),
//					computer.getDiscontinued(), computer.getCompany_id());
//			response.sendRedirect("/cdb/home");
//		} catch (Exception e) {
//			dispatcher = request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp");
//			request.setAttribute("errorMessage", e.getMessage());
//			request.setAttribute("id", Integer.parseInt(request.getParameter("id")));
//			request.setAttribute("name", computer.getName());
//			request.setAttribute("introduced", computer.getIntroduced());
//			request.setAttribute("discontinued", computer.getDiscontinued());
//			request.setAttribute("company_id", computer.getCompany_id());
//			dispatcher.forward(request, response);
//
//		}

	}
}
