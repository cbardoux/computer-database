package com.excilys.cdb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.dto.CdbUserDetails;
import com.excilys.cdb.dto.User;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	

    private UserDAO userRepository;
    
    public UserDetailsServiceImpl(UserDAO userRepository) {
    	this.userRepository = userRepository;
	}
    
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return new CdbUserDetails(user);
    }
}