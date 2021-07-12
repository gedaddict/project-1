package com.example.project1.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Address {
	
	private String street;
	private String city;
	private String state;
	private String code;
	
	public Address() {
		
	}
	
	public Address(Address address) {
		this.street = address.getStreet();
		this.city = address.getCity();
		this.state = address.getState();
		this.code = address.getCode();
	}
	
	public Address (String street, String city, String state, String code) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.code = code;
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
