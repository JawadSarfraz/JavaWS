package com.test.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "ADDRESS")
@DynamicInsert
@DynamicUpdate
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	
	///@OneToOne(mappedBy="address",orphanRemoval = true,fetch = FetchType.LAZY)
	@OneToOne(mappedBy="address",fetch = FetchType.LAZY)
	private User user;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
