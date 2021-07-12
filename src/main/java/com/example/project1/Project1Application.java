package com.example.project1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.reactive.TransactionSynchronizationManager;

import com.example.project1.controllers.BookController;
import com.example.project1.models.Address;
import com.example.project1.models.Book;
import com.example.project1.models.User;
import com.example.project1.repositories.BookRepository;
import com.example.project1.repositories.UserRepository;
import com.example.project1.services.BookService;

@SpringBootApplication
//@EnableTransactionManagement
public class Project1Application implements CommandLineRunner{
	
	private static final Logger log = LoggerFactory.getLogger(Project1Application.class);
	

	public static void main(String[] args) {
		SpringApplication.run(Project1Application.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
		
		// load initial test data
		//loadInitialData();
		
	}
	
	/*
	public void loadInitialData() {
		
		user.setUsername("admin");
		user.setPassword("dbadmin");
		user.setRole("admin");
		Collection<Address> addressList = new ArrayList<>(Arrays.asList(
					new Address("gold", "Anchorage", "Alaska", "1524"),
					new Address("platinum", "Ohio", "Kentucky", "5234")));
		user.setAddressList(addressList);
		
		Collection<Book> bookList = new ArrayList<>(Arrays.asList(
				new Book("Raven", "SpaceWalker", "Alie"),
				new Book("Lexa", "Heda", "Mt. Weather"),
				new Book("Maddy", "Natblida", "Eden")
				));
		
		//bookList.stream().forEach(b -> b.setUser(user));
		
		//user.setBook(bookList);
		
		userRepository.save(user);
		
		
		book.setAuthor("Wanheda");
		book.setEdition("Grounders");
		book.setName("Clarke");
		book.setUser(user);
		bookRepository.save(book);
		
	}

	*/

}
