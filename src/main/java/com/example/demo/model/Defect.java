package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo.constants.Constants;

@Document(collection = Constants.DEFECT_COLLECTION)
public class Defect {
	private String id;
	private String desc;
	private String userId;
	private String projectId;
	//private String testcaseId;
	private String expResults;
	private String status;
	private int severity;
	
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
//	public String getTestcaseId() {
//		return testcaseId;
//	}
//	public void setTestcaseId(String testcaseId) {
//		this.testcaseId = testcaseId;
//	}
	public String getExpResults() {
		return expResults;
	}
	public void setExpResults(String expResults) {
		this.expResults = expResults;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSeverity() {
		return severity;
	}
	public void setSeverity(int severity) {
		this.severity = severity;
	}
}
