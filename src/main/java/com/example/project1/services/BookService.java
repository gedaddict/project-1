package com.example.project1.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.TransactionManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.transaction.TransactionalException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.reactive.TransactionSynchronizationManager;

import com.example.project1.models.Book;
import com.example.project1.models.BookList;
import com.example.project1.models.User;
import com.example.project1.repositories.BookRepository;
import com.example.project1.repositories.UserRepository;

@Service
public class BookService {
	
	private static final Logger log = LoggerFactory.getLogger(BookService.class);
	
	private Book bookTemp;

	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	UserService userService;
	
	public List<Book> getAllBooks() {
		log.info("inside BookService.getAllBooks");
		List<Book> books = new ArrayList<>();
		bookRepository.findAll().forEach(books::add);
		return books;
	}
	
	public BookList getBookList() {
		log.info("inside BookService.getBookList");
		List<Book> book = new ArrayList<>();
		bookRepository.findAll().forEach(book::add);
		BookList bookList = new BookList(book);
		return bookList;
	}
	
	@Transactional
	public Book addBook(Book book) {
		log.info("inside BookService.addBook");
		//log.info("currentTransactionStatus: " +TransactionAspectSupport.currentTransactionStatus().isNewTransaction());
		User user = null;
		if (book.getName().isEmpty()) throw new PersistenceException("Field cannot be null. Tx Rollback.");
		
		try {
			user = userService.addUser(book.getUser());
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			//throw new RuntimeException("Lets rollback.");
		}
		
		
		book.setUser(user);
		
		return bookRepository.save(book);
	}
	
	public Book getBook(int bookNumber) {
		log.info("inside BookService.getBook");
		try {
			bookTemp = bookRepository.findById(bookNumber).get();
		} catch (NoSuchElementException nse) {
			throw new NoSuchElementException();
		} 
		
		return bookTemp;
	}
	
	public void updateBook(int bookNumber, Book book) {
		log.info("inside BookService.updateBook");
		Book book2 = bookRepository.findById(bookNumber).map(Book::new).get();
		book2.setAuthor(book.getAuthor());
		book2.setEdition(book.getEdition());
		book2.setName(book.getName());
		bookRepository.save(book2);
	}
	
	public void deleteBook(int bookNumber) {
		log.info("inside BookService.deleteBook");
		bookRepository.deleteById(bookNumber);
	}
	
	public String testMock(int bookNumber) {
		return bookRepository.findById(bookNumber).get().getName();
	}

}
