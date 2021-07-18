package com.example.project1.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.project1.models.Book;
import com.example.project1.models.BookList;
import com.example.project1.services.BookService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.JsonPathResultMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(BookController.class)
@TestInstance(value = Lifecycle.PER_CLASS)
class BookControllerUnitTest {
	
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private BookService bookService;
	
	private List<Book> bookList;
	private String expectedJsonResponse;
	private Book book;
	private int bookNumber;
	private BookList bList = new BookList();
	
	@BeforeAll
	void intialSetup() {
		
		bookList  = new ArrayList<Book>();
		bookList.add(new Book(1, "Space walker", "Raven", "The 100: season 3"));
		bookList.add(new Book(2, "Wanheda", "Clarke", "The 100: season 1"));
		bookList.add(new Book(3, "Skyripper", "Octavia", "The 100: season 4"));
		bookList.add(new Book(4, "Heda", "Lexa", "The 100: season 2"));
		bookList.add(new Book(5, "Natblida", "Luna", "The 100: season 5"));
		
		expectedJsonResponse = "[{\"bookNumber\":1,\"name\":\"Space walker\",\"author\":\"Raven\",\"edition\":\"The 100: season 3\"},{\"bookNumber\":2,\"name\":\"Wanheda\",\"author\":\"Clarke\",\"edition\":\"The 100: season 1\"},{\"bookNumber\":3,\"name\":\"Skyripper\",\"author\":\"Octavia\",\"edition\":\"The 100: season 4\"},{\"bookNumber\":4,\"name\":\"Heda\",\"author\":\"Lexa\",\"edition\":\"The 100: season 2\"},{\"bookNumber\":5,\"name\":\"Natblida\",\"author\":\"Luna\",\"edition\":\"The 100: season 5\"}]";
	
		bookNumber = 3;
		
		book = new Book(bookNumber, "Skyripper", "Octavia", "The 100: season 4");
		
		bList.setBookList(bookList);
	
	}

	@Test
	void testGetBookList() throws Exception {
		when(bookService.getBookList()).thenReturn(bList);
		
		MvcResult response = mock.perform(get("/books"))
			.andDo(print())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			//.andExpect(content().json(expectedJsonResponse))
			.andExpect(jsonPath("$.bookList.[2].author").value("Octavia"))
			.andExpect(jsonPath("$.bookList.[4].edition").value("The 100: season 5"))
			.andExpect(jsonPath("$.bookList.[1].bookNumber").value(2))
			.andExpect(jsonPath("$.bookList.[3].name").value("Heda"))
			.andExpect(jsonPath("$.bookList.[0].edition").value("The 100: season 3"))
			.andExpect(status().isOk())
			.andReturn();
		/*
		List<Book> bookListTemp = objectMapper.readValue(response.getResponse().getContentAsString(), 
									new TypeReference<List<Book>>(){});
		
		List<Book> b2 = objectMapper.readValue(response.getResponse().getContentAsString(), 
								objectMapper.getTypeFactory().constructCollectionType(List.class, Book.class));
		
		assertEquals(5, bookListTemp.size());
		assertEquals(5, b2.size());
		*/
		BookList bookListTemp = objectMapper.readValue(response.getResponse().getContentAsString(), BookList.class);
		
		assertEquals(5, bookListTemp.getBookList().size());

	}

	@Test
	void testAddBook() throws Exception {
		when(bookService.addBook(Mockito.any(Book.class))).thenReturn(book);
		
		mock.perform(post("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(book).getBytes(StandardCharsets.UTF_8)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.bookNumber").value(3))
			.andExpect(jsonPath("$.name").value("Skyripper"))
			.andExpect(jsonPath("$.author").value("Octavia"))
			.andExpect(jsonPath("$.edition").value("The 100: season 4"));
	}

	@Test
	void testGetBook() throws Exception {
		when(bookService.getBook(bookNumber)).thenReturn(book);
		
		mock.perform(get("/books/"+bookNumber))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.bookNumber").value(bookNumber))
			.andExpect(jsonPath("$.name").value("Skyripper"))
			.andExpect(jsonPath("$.author").value("Octavia"))
			.andExpect(jsonPath("$.edition").value("The 100: season 4"));
		
		/*
		Book tempBook = objectMapper.readValue(response.getResponse().getContentAsString(), Book.class);
		//"Skyripper", "Octavia", "The 100: season 4"
		assertEquals("Heda", tempBook.getName());
		assertEquals("The 100: season 2", tempBook.getEdition());
		assertEquals(4, tempBook.getBookNumber());
		assertEquals("Lexa", tempBook.getAuthor());
		*/
	}

	@Test
	@Disabled
	void testUpdateBook() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testDeleteBook() {
		fail("Not yet implemented");
	}

}
