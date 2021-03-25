package com.excilys.cdb.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.dto.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User getUserByUsername(String username);
}