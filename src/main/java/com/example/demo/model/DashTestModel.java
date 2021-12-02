package com.example.demo.model;

public class DashTestModel {
	
	private String testcaseId;
	private String name;
	private String status;
	
	
	public DashTestModel() {
		super();
	}
	
	public DashTestModel(String testcaseId, String name, String status) {
		super();
		this.testcaseId = testcaseId;
		this.name = name;
		this.status = status;
	}
	public String getTestcaseId() {
		return testcaseId;
	}
	public void setTestcaseId(String testcaseId) {
		this.testcaseId = testcaseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
