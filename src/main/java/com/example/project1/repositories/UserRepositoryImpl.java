package com.example.project1.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.project1.models.User;

public class UserRepositoryImpl implements UserRepositoryCustom {

	private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public User saveUser(User user) {
		log.info("executing UserRepositoryImpl.saveUser");
		em.persist(user);
		return user;
	}

}
