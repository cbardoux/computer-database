package main.java.com.excilys.cdb.dao;

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

import main.java.com.excilys.cdb.dto.ComputerDTOForDB;
import main.java.com.excilys.cdb.dto.ComputerDTOFromDB;
import main.java.com.excilys.cdb.dto.MappingDTO;
import main.java.com.excilys.cdb.exception.DAOException;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.model.Page;

@Repository
public class ComputerDAO {

	private MappingDTO mappingDTO;
	private SessionFactory factory;

	public ComputerDAO(MappingDTO mappingDTO, SessionFactory factory) {
		this.mappingDTO = mappingDTO;
		this.factory = factory;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

	private static final String FIND_COMPUTERS_WITH_PAGE_QUERY = "from ComputerDTOFromDB WHERE name like :name order by ";
	private static final String FIND_COMPUTER_QUERY = "from ComputerDTOFromDB where id= :id";
	private static final String MODIFY_COMPUTER_QUERY = "update ComputerDTOForDB set name= :name, introduced= :introduced, discontinued= :discontinued, company_id= :companyId where id= :id";
	private static final String DELETE_COMPUTER_QUERY = "delete from ComputerDTOForDB where id= :id";
	private static final String COUNT_ROWS = "select count(id) from ComputerDTOFromDB where name like :name";

	public Page<Computer> listComputersWithOffset(Page<Computer> page) {
		String request = FIND_COMPUTERS_WITH_PAGE_QUERY + page.getOrderBy();
		try (Session session = factory.openSession();) {
			List<ComputerDTOFromDB> computerDTOList = session.createQuery(request, ComputerDTOFromDB.class)
					.setParameter("name", "%" + page.getSearch() + "%").setFirstResult(page.getIndex())
					.setMaxResults(page.getLimit()).list();
			page.setContent(computerDTOList.stream().map(mappingDTO::listComputerDTOToComputerObject)
					.collect(Collectors.toList()));
		}
		return page;
	}

	public Optional<Computer> getComputerById(int id) {

		Optional<Computer> computer = Optional.empty();
		try (Session session = factory.openSession();) {
			ComputerDTOFromDB computerDTO = (ComputerDTOFromDB) session.createQuery(FIND_COMPUTER_QUERY)
					.setParameter("id", id).getSingleResult();
			computer = Optional.of(mappingDTO.listComputerDTOToComputerObject(computerDTO));
		}
		return computer;
	}

	public void createComputer(Computer computer) {
		ComputerDTOForDB computerDTO = mappingDTO.computerObjectToCreateComputerDTOForDB(computer);
		Transaction tx = null;
		try (Session session = factory.openSession();) {
			tx = session.beginTransaction();
			session.save(computerDTO);
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			LOGGER.error(e.getMessage());
		}
	}

	public void modifyComputer(Computer computer) {
		ComputerDTOForDB computerDTO = mappingDTO.computerObjectToModifyComputerDTOForDB(computer);
		Transaction tx = null;
		try (Session session = factory.openSession();) {
			tx = session.beginTransaction();
			Query query = session.createQuery(MODIFY_COMPUTER_QUERY).setParameter("id", computerDTO.id)
					.setParameter("name", computerDTO.name).setParameter("introduced", computerDTO.introduced)
					.setParameter("discontinued", computerDTO.discontinued)
					.setParameter("companyId", computerDTO.companyId);
			query.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			LOGGER.error(e.getMessage());
		}
	}

	public void deleteComputer(int id) throws DAOException {
		Transaction tx = null;
		try (Session session = factory.openSession();) {
			tx = session.beginTransaction();
			Query query = session.createQuery(DELETE_COMPUTER_QUERY);
			query.setParameter("id", id);
			query.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			LOGGER.error(e.getMessage());
		}
	}

	public int countRows(Page<Computer> page) {
		int intResult = 0;
		try (Session session = factory.openSession();) {
			Query query = session.createQuery(COUNT_ROWS).setParameter("name", "%" + page.getSearch() + "%");
			Long longResult = (Long) query.uniqueResult();
			intResult = longResult.intValue();
		}
		return intResult;
	}
}
