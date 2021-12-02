package com.example.demo.model;

import java.util.List;

public class DashReqModel {
	private String requirementId;
	private String description;
	private List<DashTestModel> testCases;
	
	public DashReqModel() {
		super();
	}

	public DashReqModel(String requirementId, String description, List<DashTestModel> testCases) {
		super();
		this.requirementId = requirementId;
		this.description = description;
		this.testCases = testCases;
	}

	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<DashTestModel> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<DashTestModel> testCases) {
		this.testCases = testCases;
	}
	
	
	

}
