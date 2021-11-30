package com.example.demo.service.projectcreation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.model.projectcreation.Counter;
import com.example.demo.model.projectcreation.ProjectModel;
import com.example.demo.model.projectcreation.RequirementModel;
import com.example.demo.constants.Constants;
import com.example.demo.utilities.ProjectUtility;

@Service
public class ProjectService {
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private MongoOperations mongoOperation;

	@Autowired
	public ProjectService(MongoTemplate mongoTemplate) {
		
		this.mongoTemplate = mongoTemplate;
	}
	
	
	public String addProject(ProjectModel projectModel)
	{
		if(mongoTemplate.insert(projectModel)!=null)
		{
			return "Project Created with ID "+projectModel.getId();
		}
		else {
			return "Project Not Created";
		}
		
	}
	
	public List<ProjectModel> getAllProjects()
	{
		return   mongoTemplate.findAll(ProjectModel.class);
	}
	public ProjectModel getByProjectId(String id) {
	    
		 Map<String,String> conditionsMap=new HashMap<String,String>();
		  conditionsMap.put("id", id);
		return mongoTemplate.findOne(ProjectUtility.getQueryByKeyValue(conditionsMap), ProjectModel.class);
	}
	
	public String updateProject(ProjectModel projectModel,String id) {
		ProjectModel requestedProject=getByProjectId(id);
		if(projectModel.getName()!=null)
		{
			requestedProject.setName(projectModel.getName());
		}
		if(projectModel.getDescription()!=null)
		{
			requestedProject.setDescription(projectModel.getDescription());
		}
		if(projectModel.getStartDate()!=null)
		{
			requestedProject.setStartDate(projectModel.getStartDate());
		}
		if(projectModel.getEndDate()!=null)
		{
			requestedProject.setEndDate(projectModel.getEndDate());
		}
		if(projectModel.getTargetedRelease()!=null)
		{
			requestedProject.setTargetedRelease(projectModel.getTargetedRelease());
		}
		requestedProject.addUpdateDate(requestedProject.getId()+" is Updated");
		 
		mongoTemplate.save(requestedProject);
		
		return "Project "+requestedProject.getId()+" Updated";
			
		
	}

	
	public int uniqueValue(String key)
	{
		 
		 Update update = new Update();
		  update.inc(Constants.PROJECT_COUNTER_DOCUMENT_SEQUENCE_COLUMN, 1);
		  FindAndModifyOptions options = new FindAndModifyOptions();
		  options.returnNew(true).upsert(true);
		  
		  Map<String,String> conditionsMap=new HashMap<String,String>();
		  conditionsMap.put("_id", key);
		  
		  Counter counter= mongoOperation.findAndModify(ProjectUtility.getQueryByKeyValue(conditionsMap), update, options, Counter.class);
		
		  mongoTemplate.save(counter);

		return counter.getSeq();
		
	}
	
	
//	public ProjectModel setRequirementCount(ProjectModel projectModel,Counter counter)
//	{
//		 List<RequirementModel> requirements =projectModel.getRequirements();
//		for(int reqIndex=0 ;reqIndex<projectModel.getRequirements().size();reqIndex++)
//		{
//			RequirementModel tempRequirement=requirements.get(reqIndex);
//			tempRequirement.setId(Constants.REQUIREMENT_PREFIX+String.valueOf(reqIndex+1));
//			requirements.set(reqIndex,tempRequirement);
//		}
//		counter.setRequirementCountByIndex(counter.getSeq()-1,counter.getRequirementCounter().get(counter.getSeq()-1)+projectModel.getRequirements().size() );
//		
//		mongoTemplate.save(counter);
//
//		return projectModel;
//	}

	public String addRequirement(List<RequirementModel> requirementModelList, String projectId) {
		
		  ProjectModel projectModel=getByProjectId(projectId);
		
//		 Counter counter=mongoTemplate.findOne(ProjectUtility.getQueryByKeyValue("_id", Constants.PROJECT_COUNTER_DOCUMENT_ID), Counter.class);
		  
		 for(int requirementIndex=0;requirementIndex<requirementModelList.size();requirementIndex++)
		 {
			 RequirementModel requirementModel=requirementModelList.get(requirementIndex);
			 requirementModel.setStatus(Constants.REQUIREMENT_VALID_STATUS);
			 requirementModel.setProjectId(projectId);
			 requirementModel.setRequirementId( Constants.REQUIREMENT_PREFIX+String.valueOf(projectModel.requirementCountIncrement()));
			 requirementModel.setTestCaseCount(0);
			 mongoTemplate.insert(requirementModel);
		 }
		  
//		 int requirementCounterIndex=Integer.valueOf(id.substring(4));
//		 int value=counter.getRequirementCounter().get(requirementCounterIndex-1);
//		 value+=1;
//		 counter.setRequirementCountByIndex(requirementCounterIndex-1, value);
//		 requirementModel.setId(Constants.REQUIREMENT_PREFIX+String.valueOf(value));
//		 projectModel.addRequirements(requirementModel);
		 mongoTemplate.save(projectModel);
//		 mongoTemplate.save(counter);
		 
		return "requirement added";
	}

	public String updateRequirement(RequirementModel requirementModel, String id, String rid, boolean remove) {

		ProjectModel projectModel = getByProjectId(id);
//		int requirementIndex = Integer.valueOf(rid.substring(4));
		
		Map<String,String> conditionsMap=new HashMap<String,String>();
		conditionsMap.put("projectId", id);
		conditionsMap.put("id", rid);
		
		RequirementModel requestedRequirement =mongoTemplate.findOne(ProjectUtility.getQueryByKeyValue(conditionsMap), RequirementModel.class);
		if (remove) {
			requestedRequirement.setStatus("Invalid");
			updateTestcaseStatus();
		} 

		if (requirementModel.getDescription() != null) {
			requestedRequirement.setDescription(requirementModel.getDescription());
		}
		if (requirementModel.getInputParameters() != null) {
			requestedRequirement.setInputParameters(requirementModel.getInputParameters());
		}
//		projectModel.updateRequirementbyIndex(requirementIndex - 1, requestedRequirement);
		projectModel.addUpdateDate("Requirement "+requestedRequirement.getRequirementId()+" of project Id "+projectModel.getId()+" is Updated");

		mongoTemplate.save(projectModel);
		mongoTemplate.save(requestedRequirement);

		return "Requirement Updated";
	}
	
	//
	private void updateTestcaseStatus()
	{
		
	}
	
	
}
