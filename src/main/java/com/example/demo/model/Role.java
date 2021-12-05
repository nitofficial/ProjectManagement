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

	private boolean isrolestatusactive;

	public Role(String id, String name, boolean isrolestatusactive) {
		super();
		this.id = id;
		this.name = name;
		this.isrolestatusactive = isrolestatusactive;
	}

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

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsRolestatusactive() {
		return isrolestatusactive;
	}

	public void setIsRolestatusactive(boolean isrolestatusactive) {
		this.isrolestatusactive = isrolestatusactive;
	}
}
