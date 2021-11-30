package com.example.demo.model.testcase;

import org.springframework.data.mongodb.core.mapping.Document;


import com.example.demo.constants.Constants;

@Document(collection =Constants.TESTCASE_COLLECTION)
public class TestCaseModel {
	private String _id;
	private String projectId;
	private String projectname;
	private String requirementId;
	private String testcaseId;
	private String name;
	private String description;
	private String inputParameters;
	private String expectedResults;
	private String actualResults;
	private String status;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
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

	public String getDescription() {
		return description;
	}