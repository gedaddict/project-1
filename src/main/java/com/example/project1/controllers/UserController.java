package com.example.project1.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.models.Book;
import com.example.project1.models.User;
import com.example.project1.services.BookService;
import com.example.project1.services.UserService;

@RestController
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	@Autowired
	BookService bookService;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		log.info("inside UserController.getAllUsers");
		return userService.getAllUsers();
	}
	
	@GetMapping("/users/{userId}")
	public User getUser(@PathVariable("userId") int userId) {
		log.info("inside UserController.getUser");
		return userService.getUser(userId);
	}
	
	@PostMapping("/users")
	public String addUser(@RequestBody User user) {
		log.info("inside UserController.addUser");
		userService.addUser(user);
		return "new user added!";
	}
	
	@PostMapping("/users/{userId}")
	public String addUserBook(@RequestBody Book book, @PathVariable ("userId") int userId) {
		log.info("inside UserController.addUserBook");
		User user = userService.getUser(userId);
		if (user != null)
			userService.addUserBook(book, user);
		return "new book added to user!";
	}	
	
	@PutMapping("/users/{userId}")
	public String updateUser(@PathVariable("userId") int userId, @RequestBody User user) {
		log.info("inside UserController.updateUser");
		userService.updateUser(userId, user);
		return "user updated!";
	}
	
	@DeleteMapping("/users/{userId}")
	public String deleteUser(@PathVariable("userId") int userId) {
		log.info("inside UserController.deleteUser");
		userService.deleteUser(userId);
		return "user deleted!";
	}
}
