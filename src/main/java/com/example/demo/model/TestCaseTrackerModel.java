package com.example.demo.model;

public class TestCaseTrackerModel {
	
	private String requirementId;
	private float percentage;
	public TestCaseTrackerModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TestCaseTrackerModel(String requirementId, float percentage) {
		super();
		this.requirementId = requirementId;
		this.percentage = percentage;
	}
	public String getRequirementId() {
		return requirementId;
	}
	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}
	public float getPercentage() {
		return percentage;
	}
	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	
	
	


}
