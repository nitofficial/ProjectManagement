/**
	 * @author Sanjay	
*/
package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Role")
public class Role {

	@Transient
	public static final String SEQUENCE_NAME = "role_seq";

	@Id
	private String id;
	private String name;

	public Role() {
	}

	public Role(String rolename) {
		this.name = rolename;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
}
