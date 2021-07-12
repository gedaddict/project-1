package com.example.project1.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.project1.models.Book;

public class BookRepositoryImpl implements BookRepositoryCustom {
	
	private final static Logger log = LoggerFactory.getLogger(BookRepositoryImpl.class);

	@PersistenceContext
	EntityManager em;
	
	@Override
	public Book saveBook(Book book) {
		log.info("executing BookRepositoryImpl.saveBook");
		em.persist(book);
		
		return book;
	}

}
