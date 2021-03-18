package main.java.com.excilys.cdb.dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Company;

@Repository
public class CompanyDAO {

	private DataSource dataSource;

	public CompanyDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

	private static final String FIND_COMPANIES_QUERY = "SELECT id, name FROM company;";
	private static final String FIND_IF_COMPANY_EXISTS = "SELECT COUNT(id) FROM company WHERE id = :id;";
	private static final String DELETE_COMPANY_QUERY = "DELETE FROM company WHERE id = ?;";
	private static final String DELETE_COMPUTER_WITH_COMPANY_ID_QUERY = "DELETE FROM computer WHERE company_id = ?;";

	public List<Company> listCompanies() {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
		List<Company> resultList = jdbcTemplate.query(FIND_COMPANIES_QUERY, new CompanyRowMapper());
		return resultList;
	}

	@Transactional
	public void deleteCompany(int id) throws SQLException, DAOException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
		try {
			jdbcTemplate.update(DELETE_COMPUTER_WITH_COMPANY_ID_QUERY, id);
			jdbcTemplate.update(DELETE_COMPANY_QUERY, id);
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public int isCompanyExists(int id) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("id", id);
		return jdbcTemplate.queryForObject(FIND_IF_COMPANY_EXISTS, param, Integer.class);
	}
}
