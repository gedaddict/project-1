package com.example.project1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

import com.example.project1.models.Book;
import com.example.project1.models.User;
import com.example.project1.repositories.BookRepository;
import com.example.project1.services.BookService;

@SpringBootTest
class BookUnitTest {
	
	private Book book;
	private Book book1;
	private Book bookReturned;
	private List<Book> bookList;
	
	@InjectMocks
	BookService bookService;
	
	@Mock
	BookRepository bookRepository;
	
	@BeforeEach
	void init() {
		
		System.out.println("Initializing before running...");
		//bookRepository = Mockito.mock(BookRepository.class);
		//bookService = new BookService();
		//bookService.setBookRepository(bookRepository);
		User user = new User();
		user.setUserId(1);
		user.setUsername("admin");
		user.setPassword("dbadmin");
		user.setRole("admin");
		
		book = new Book(); //"RedQueen", "Octavia", "Bunker"
		book.setBookNumber(1);
		book.setAuthor("Raven");
		book.setName("SpaceWalker");
		book.setEdition("Alie");
		book.setUser(user);
		
		book1 = new Book(); //"RedQueen", "Octavia", "Bunker"
		book1.setBookNumber(2);
		book1.setAuthor("Lexa");
		book1.setName("Heda");
		book1.setEdition("Mt.Weather");
		book1.setUser(user);
		
		bookList  = new ArrayList<>();
		bookList.add(book);
		bookList.add(book1);
	}
	
	@AfterEach
	void cleanup() {
		System.out.println("Clean up after running...");
		bookReturned = null;
		bookList = null;
	}

	@Test
	@DisplayName(value = "ShouldReturnBookObjectUsingId")
	public void ShouldReturnBookObjectUsingId() {
		
		Mockito.when(bookRepository.findById(2)).thenReturn(Optional.of(book));
		bookReturned = bookService.getBook(2);
		
		assertNotNull(bookReturned);
		assertEquals(book, bookReturned);

	}
	
	@Test
	@DisplayName(value = "ShouldReturnUserAttributeUsingId")
	public void ShouldReturnUserAttributeUsingId() {
		
		Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(book));
		bookReturned = bookService.getBook(1);
		
		assertNotNull(bookReturned);
		assertEquals("SpaceWalker", bookReturned.getName());
		
	}
	
	@Test
	@DisplayName(value ="ShouldReturnAllBookList")
	public void ShouldReturnAllBookList() {
		
		Mockito.when(bookRepository.findAll()).thenReturn(bookList);
		List<Book> allBooks = bookService.getAllBooks();
		
		assertNotNull(allBooks);
		assertEquals(2, allBooks.size());
	}
	
	@Test
	@DisplayName(value = "ShouldThrowExceptionWithNonExistingId")
	public void ShouldReturnExceptionWithNonExistingId() {
		
		Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(book));
		
		assertThrows(NoSuchElementException.class, 
				() -> bookService.getBook(2));
	
	}
	
	@Test
	@DisplayName(value = "ShouldSaveObject")
	public void ShouldSaveObject() {
		
		//Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
		Mockito.when(bookRepository.save(book1)).thenReturn(book1);
		
		bookReturned = bookService.addBook(book1);
		
		assertNotNull(bookReturned);
		assertEquals(book1.getAuthor(), bookReturned.getAuthor());
		
	}
	
	@Test
	@DisplayName(value = "ShouldThrowExceptionWhenSavingNull")
	public void ShouldThrowExceptionWhenSavingNull() {
		
		Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
		
		assertThrows(NullPointerException.class, 
				() -> bookService.addBook(new Book()));
		
		assertThrows(NullPointerException.class, 
				() -> bookService.addBook(new Book(null)));
		book = null;
		assertThrows(NullPointerException.class, 
				() -> bookService.addBook(book));
		
		assertThrows(NullPointerException.class, 
				() -> bookService.addBook(null));
	}
	
}
