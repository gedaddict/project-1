package com.example.project1.models;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class BookList {

	private List<Book> bookList;
	
	public BookList() {
		
	}
	
	public BookList (List<Book> book) {
		this.bookList = book;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
	
}
