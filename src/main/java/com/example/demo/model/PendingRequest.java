package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pending_requests")
public class PendingRequest {
	
	@Transient
	public static final String SEQUENCE_NAME = "request_seq";
	
	@Id
	private String requestid;
	private String userid;
	private String requestedroleid;
	private boolean isrequestgranted;
	
	public PendingRequest(String requestid, String userid, String requestedroleid, boolean isrequestgranted) {
		super();
		this.requestid = requestid;
		this.userid = userid;
		this.requestedroleid = requestedroleid;
		this.isrequestgranted = isrequestgranted;
	}

	public PendingRequest(String userid, String requestedroleid) {
		super();
		this.userid = userid;
		this.requestedroleid = requestedroleid;
		this.isrequestgranted = false;
	}

	public PendingRequest() {
		super();
	}

	public String getRequestid() {
		return requestid;
	}

	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRequestedroleid() {
		return requestedroleid;
	}

	public void setRequestedroleid(String requestedroleid) {
		this.requestedroleid = requestedroleid;
	}

	public boolean isIsrequestgranted() {
		return isrequestgranted;
	}

	public void setIsrequestgranted(boolean isrequestgranted) {
		this.isrequestgranted = isrequestgranted;
	}
}