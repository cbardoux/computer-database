package com.excilys.cdb.dao;

import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.dto.User;

@Repository
public class UserDAO implements UserRepository {

	private SessionFactory sessionFactory;

	public UserDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public User getUserByUsername(String username) {
		try (Session session = sessionFactory.openSession();) {
			return (User) session.createQuery("from User where username=:username", User.class)
					.setParameter("username", username).getSingleResult();
		}
	}

	public void modifyUser(User user) {
		Transaction tx = null;
		try (Session session = sessionFactory.openSession();) {
			tx = session.beginTransaction();
			Query query = session.createQuery("update User set password= :password where id = :id")
					.setParameter("password", user.getPassword()).setParameter("id", user.getId());
			query.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
		}
	}

	@Override
	public <S extends User> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<User> findAllById(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(User entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends User> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}
}