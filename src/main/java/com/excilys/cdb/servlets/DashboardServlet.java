package main.java.com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.java.com.excilys.cdb.dto.ComputerDTOForServlet;
import main.java.com.excilys.cdb.dto.MappingDTO;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.model.Page;
import main.java.com.excilys.cdb.service.ComputerService;

@WebServlet("/home")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ComputerService instanceService = ComputerService.getInstance();
	private MappingDTO mapping = MappingDTO.getInstance();
	private final int INDEX_MIN = 1;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp");
		
		HttpSession session = request.getSession();
		Page<Computer> page = (Page<Computer>) session.getAttribute("page");
		if (page == null) {
			page = new Page<Computer>();
			session.setAttribute("page", page);
		}

		ArrayList<ComputerDTOForServlet> computerDTO = new ArrayList<>();
		int numberOfRows = instanceService.countRows();
		int limit = page.getLimit();
		int indexMax = numberOfRows % limit == 0 ? numberOfRows / limit : numberOfRows / limit + 1;
		
		if (request.getParameter("index") != null) {
			try {
				page.setIndex(Integer.parseInt(request.getParameter("index")));
			} catch (NumberFormatException numberFormatException) {
				request.setAttribute("errorMessage", numberFormatException);
			}
		} else if (request.getParameter("limit") != null) {
			try {
				page.setLimit(Integer.parseInt(request.getParameter("limit")));
				page.setIndex(INDEX_MIN);
			} catch (NumberFormatException numberFormatException) {
				request.setAttribute("errorMessage", numberFormatException);
			}
		}

		for (Computer computer : instanceService.getComputersWithOffset(page).getContent()) {
			computerDTO.add(mapping.computerObjectToCreateComputerDTO(computer));
		}
		
		request.setAttribute("rows", instanceService.countRows());
		request.setAttribute("computers", computerDTO);
		request.setAttribute("indexLow", page.valueOfIndexLow(indexMax));
		request.setAttribute("indexHigh", page.valueOfIndexHigh(indexMax));
		request.setAttribute("indexMax", indexMax);

		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}