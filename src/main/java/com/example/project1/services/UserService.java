package com.example.project1.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.example.project1.models.Book;
import com.example.project1.models.User;
import com.example.project1.repositories.UserRepository;

@Service
public class UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;
	@Autowired
	BookService bookService;
	
	private User userTemp;
	
	public List<User> getAllUsers() {
		log.info("inside UserService.getAllUsers");
		List<User> userList = new ArrayList<>();
		userRepository.findAll().forEach(userList::add);
		return userList;
	}
	
	public User getUser(int userId) {
		log.info("inside UserService.getUser");
		User user = userRepository.findById(userId).get();
		
		//if (user == null) throw new PersistenceException("User does not exists! Tx should rollback! ");
			
		return user;
	}
	
	//@Transactional(value = TxType.REQUIRES_NEW)
	public User addUser(User user) {
		log.info("inside UserService.addUser");
		userTemp = null;
		
		userTemp = userRepository.saveUser(user);
		if (user.getRole().equals("user")){
			throw new PersistenceException("Tx Rollback. ");
		}
			
		return userTemp;
	}
	
	public void addUserBook(Book book, User user) {
		log.info("inside UserService.addUserBook");
		//book.stream().forEach(b -> b.setUser(user));
		book.setUser(user);
		user.getBookList().add(book);
		userRepository.save(user);
	}
	
	public void updateUser(int userId, User user) {
		log.info("inside UserService.updateUser");
		userTemp = userRepository.findById(userId).map(User::new).get();
		userTemp.setUsername(user.getUsername());
		userTemp.setPassword(user.getPassword());
		userTemp.setRole(user.getRole());
		userTemp.setAddressList(user.getAddressList());
		userRepository.save(userTemp);
	}
	
	public void deleteUser(int userId) {
		userRepository.deleteById(userId);
	}
}
