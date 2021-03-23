package com.excilys.cdb.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.MappingDTO;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Company;

@Repository
public class CompanyDAO {

	private SessionFactory factory;
	private MappingDTO mappingDTO;

	public CompanyDAO(SessionFactory factory, MappingDTO mappingDTO) {
		this.factory = factory;
		this.mappingDTO = mappingDTO;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

	private static final String FIND_COMPANIES_QUERY = "from CompanyDTO";
	private static final String FIND_IF_COMPANY_EXISTS = "select count(id) from CompanyDTO where id = :id";
	private static final String DELETE_COMPANY_QUERY = "delete from CompanyDTO where id= :id";
	private static final String DELETE_COMPUTER_WITH_COMPANY_ID_QUERY = "delete from ComputerDTOForDB where company_id= :id";

	public List<Company> listCompanies() {
		Optional<List<Company>> companyList = Optional.empty();
		try (Session session = factory.openSession();) {
			List<CompanyDTO> companyDTOList = session.createQuery(FIND_COMPANIES_QUERY, CompanyDTO.class).list();
			companyList = Optional.of(companyDTOList.stream().map(mappingDTO::CompanyDTOtoCompany).collect(Collectors.toList()));
		}
		return companyList.get();
	}

	@Transactional
	public void deleteCompany(int id) throws SQLException, DAOException {
		Transaction tx = null;
		try (Session session = factory.openSession();) {
			tx = session.beginTransaction();
			Query queryComputer = session.createQuery(DELETE_COMPUTER_WITH_COMPANY_ID_QUERY).setParameter("id", id);
			Query queryCompany = session.createQuery(DELETE_COMPANY_QUERY).setParameter("id", id);
			queryComputer.executeUpdate();
			queryCompany.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			LOGGER.error(e.getMessage());
		}
	}

	public int isCompanyExists(int id) {
		int intResult = 0;
		try (Session session = factory.openSession();) {
			Query query = session.createQuery(FIND_IF_COMPANY_EXISTS).setParameter("id", id);
			Long longResult = (Long) query.uniqueResult();
			intResult = longResult.intValue();
		}
		return intResult;
	}
}
