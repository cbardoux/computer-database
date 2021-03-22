package main.java.com.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import main.java.com.excilys.cdb.dto.ComputerDTOForDB;
import main.java.com.excilys.cdb.dto.ComputerDTOForServlet;
import main.java.com.excilys.cdb.dto.MappingDTO;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.model.Page;

@Repository
public class ComputerDAO {

	private DataSource dataSource;
	private MappingDTO mappingDTO;
	private SessionFactory factory;

	public ComputerDAO(DataSource dataSource, MappingDTO mappingDTO, SessionFactory factory) {
		this.dataSource = dataSource;
		this.mappingDTO = mappingDTO;
		this.factory = factory;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

	private static final String FIND_COMPUTERS_WITH_PAGE_QUERY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = computer.company_id WHERE computer.name LIKE ?";
	private static final String FIND_COMPUTER_QUERY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = computer.company_id WHERE computer.id = :id;";
	private static final String CREATE_COMPUTER_QUERY = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private static final String MODIFY_COMPUTER_QUERY = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
	private static final String DELETE_COMPUTER_QUERY = "DELETE FROM computer WHERE id = ?;";
	private static final String COUNT_ROWS = "SELECT COUNT(id) FROM computer WHERE name LIKE ?";

	public Page<Computer> listComputersWithOffset(Page<Computer> page) {
		String request = FIND_COMPUTERS_WITH_PAGE_QUERY + " ORDER BY " + "computer." + page.getOrderBy() + " LIMIT "
				+ (page.getIndex() - 1) * page.getLimit() + ", " + page.getLimit() + ";";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
		List<ComputerDTOForServlet> resultList = jdbcTemplate.query(request, new ComputerRowMapper(),
				"%" + page.getSearch() + "%");
		page.setContent(
				resultList.stream().map(mappingDTO::listComputerDTOToComputerObject).collect(Collectors.toList()));
		return page;
	}

	public Optional<Computer> getComputerById(int id) {

		Optional<Computer> computer = Optional.empty();
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("id", id);
		ComputerDTOForServlet computerDTO = jdbcTemplate.queryForObject(FIND_COMPUTER_QUERY, param,
				new ComputerRowMapper());
		computer = Optional.of(mappingDTO.getComputerByIdDTOToComputerObject(computerDTO));
		return computer;
	}

	public void createComputer(Computer computer) {
		ComputerDTOForDB computerDTO = mappingDTO.computerObjectToCreateComputerDTOForDB(computer);
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query q = session.createQuery("insert into ComputerDTOForDB (name, introduced, discontinued, companyId)"
					+ "select name, introduced, discontinued, companyId from ComputerDTOForDB");
			int result = q.executeUpdate();
//			
//			session.save(computerDTO);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
//		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
//		ComputerDTOForDB computerDTO = mappingDTO.computerObjectToCreateComputerDTOForDB(computer);
//		try {
//			jdbcTemplate.update(CREATE_COMPUTER_QUERY, computerDTO.name, computerDTO.introduced,
//					computerDTO.discontinued, computerDTO.companyId);
//		} catch (DataAccessException e) {
//			LOGGER.error(e.getMessage());
//		}
	}

	public void modifyComputer(Computer computer) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
		ComputerDTOForDB computerDTO = mappingDTO.computerObjectToCreateComputerDTOForDB(computer);
		jdbcTemplate.update(MODIFY_COMPUTER_QUERY, computerDTO.name, computerDTO.introduced, computerDTO.discontinued,
				computerDTO.companyId, computerDTO.id);
	}

	public void deleteComputer(int id) throws DAOException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
		try {
			jdbcTemplate.update(DELETE_COMPUTER_QUERY, id);
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public int countRows(Page<Computer> page) {
		String request = COUNT_ROWS + " ORDER BY " + page.getOrderBy() + ";";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
		return jdbcTemplate.queryForObject(request, Integer.class, "%" + page.getSearch() + "%");
	}
}
