package com.example.demo.model.projectcreation;

import java.util.*;


import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo.constants.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection = Constants.PROJECT_COLLECION)
public class ProjectModel {
	private String id;
	private String name;
	private String description;
	
	 @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.DATE_FORMAT)
	private Date startDate;
	
	 @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.DATE_FORMAT)
	private Date endDate;
	
	 @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.DATE_FORMAT)
	private Date targetedRelease;

	private List<UpdateHistory> updateHistoryList;
	private int requirementsCount;
	
	public ProjectModel() {
		updateHistoryList=new ArrayList<UpdateHistory>();
	}
	
	
    
	public int getRequirementsCount() {
		return requirementsCount;
	}



	public void setRequirementsCount(int requirementsCount) {
		this.requirementsCount = requirementsCount;
	}



	public int requirementCountIncrement()
	{
		requirementsCount+=1;
		return requirementsCount;
	}

	/*
	 * public void addRequirements(RequirementModel requirementModel) {
	 * requirements.add(requirementModel); }
	 * 
	 * public void updateRequirementbyIndex(int index,RequirementModel
	 * requirementModel) { requirements.set(index, requirementModel); }
	 */
	
	public void addUpdateDate(String message)
	{
		updateHistoryList.add(new UpdateHistory(new Date(),message));
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getTargetedRelease() {
		return targetedRelease;
	}

	public void setTargetedRelease(Date targetedRelease) {
		this.targetedRelease = targetedRelease;
	}

	

	public List<UpdateHistory> getUpdateHistoryList() {
		return updateHistoryList;
	}

	public void setUpdateHistoryList(List<UpdateHistory> updateHistoryList) {
		this.updateHistoryList = updateHistoryList;
	}


	


}