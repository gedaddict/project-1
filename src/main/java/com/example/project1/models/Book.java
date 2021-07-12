package com.example.project1.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="Books")
public class Book {
	
	@Id
	@GeneratedValue(generator = "increment-gen")
	@GenericGenerator(name = "increment-gen", strategy = "increment")	
	@Column(nullable=false, updatable=false)
	private int bookNumber;
	private String name;
	private String author;
	private String edition;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="userID")
	@JsonBackReference
	private User user;
	
	public Book() {
		
	}
	
	public Book (Book book) {
		this.bookNumber = book.getBookNumber();
		this.name = book.getName();
		this.author = book.getAuthor();
		this.edition = book.getEdition();
		this.user = book.getUser();
	}
	
	
	public Book (String name, String author, String edition) {
		this.name = name;
		this.author = author;
		this.edition = edition;
	}
	
	public void setBookNumber(int bookNumber) {
		this.bookNumber = bookNumber;
	}

	public int getBookNumber() {
		return bookNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Book [bookNumber=" + bookNumber + ", name=" + name + ", author=" + author + ", edition=" + edition
				+ ", user=" + user + "]";
	}

}
