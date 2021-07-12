package com.example.project1.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.models.Book;
import com.example.project1.services.BookService;

@RestController
public class BookController {
	
	private static final Logger log = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	BookService bookService;

	@GetMapping ("/books")
	public List<Book> getAllBooks() {
		log.info("inside @GetMapping /books");
		return bookService.getAllBooks();
	}
	
	@PostMapping ("/books")
	public String addBook(@RequestBody Book book) {
		log.info("inside @PostMapping /books");
		Book addBook = bookService.addBook(book);
		return "New Book " +addBook.getName() + " added";
	}
	
	@GetMapping ("/books/{bookNumber}")
	public Book getBook(@PathVariable("bookNumber") int bookNumber) {
		return bookService.getBook(bookNumber);
	}

	@PutMapping ("/books/{bookNumber}")
	public String updateBook(@PathVariable("bookNumber") int bookNumber, @RequestBody Book book) {
		bookService.updateBook(bookNumber, book);
		return "Book updated!";
	}
	
	@DeleteMapping("/books/{bookNumber}")
	public String deleteBook(@PathVariable("bookNumber") int bookNumber) {
		bookService.deleteBook(bookNumber);
		return "Book deleted!";
	}
}
