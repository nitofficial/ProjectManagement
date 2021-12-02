package com.example.demo.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo.constants.Constants;

@Document(collection = Constants.DEFECT_COLLECTION)
public class Defect {
	@Id
	private String id;
	@NotNull(message = "Description field should not be left blank")
	@Size(max = 100, message = "The Description should not exceed 100 characters")
	private String desc;
	private String userId;
	private String projectId;
	@NotNull(message = "The Expected Results field should not be left blank")
	@Size(max = 100, message = "The Expected results should not exceed 100 characters")
	private String expResults;
	private String status;
	@Range(min = 1, max = 3)
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
