package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pending_requests")
public class PendingRequest {

	private String username;
	private String roleRequested;
	private String requestType;

	public PendingRequest(String username, String roleRequested, String requestType) {
		super();
		this.username = username;
		this.roleRequested = roleRequested;
		this.requestType = requestType;
	}

	public PendingRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleRequested() {
		return roleRequested;
	}

	public void setRoleRequested(String roleRequested) {
		this.roleRequested = roleRequested;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

}