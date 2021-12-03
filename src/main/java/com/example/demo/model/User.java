/**
	 * @author Sanjay	
*/
package com.example.demo.model;

import java.util.HashSet;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
public class User {

	@Transient
	public static final String SEQUENCE_NAME = "user_seq";

	@Id
	private String id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@NotBlank
	@Size(min = 10, max = 10)
	private String phonenumber;
	
	private boolean isUserStatusActive;

	@DBRef
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(String username, String password, String phonenumber, String email) {
		this.username = username;
		this.password = password;
		this.phonenumber = phonenumber;
		this.email = email;
	}

	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	public boolean getIsUserStatusActive() {
		return isUserStatusActive;
	}
	
	public void setIsuserStatusActive(boolean isUserStatusActive) {
		this.isUserStatusActive = isUserStatusActive;
	}
}