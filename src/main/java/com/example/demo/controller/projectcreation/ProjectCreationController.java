package com.example.demo.controller.projectcreation;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.projectcreation.Counter;
import com.example.demo.model.projectcreation.ProjectModel;
import com.example.demo.model.projectcreation.RequirementModel;
import com.example.demo.constants.Constants;
import com.example.demo.service.projectcreation.ProjectService;



@RequestMapping("/api/v1")
@RestController
public class ProjectCreationController {
     
	
	@Autowired
	private ProjectService projectService;   
	
	//Project Controller
	@PostMapping("/project")	
	public String createProject(@RequestBody ProjectModel projectModel)
	{
		//System.out.println("test created");
//		Counter counter=projectService.uniqueValue(Constants.PROJECT_COUNTER_DOCUMENT_ID);
		projectModel.setId(Constants.PROJECT_PREFIX+String.valueOf(projectService.uniqueValue(Constants.PROJECT_COUNTER_DOCUMENT_ID)));
//		projectModel=projectService.setRequirementCount(projectModel, counter);
		projectModel.setRequirementsCount(0);
		return projectService.addProject(projectModel);
	}
	
	@GetMapping("/project")	
	public List<ProjectModel> allProjects()
	{
		return projectService.getAllProjects();
	}
	
	
	@GetMapping("/project/{id}")
	public ProjectModel projectByID(@PathVariable("id") String id){
		return projectService.getByProjectId(id);
		
	}
	
	@PutMapping("/project/{id}")	
	public String updateProject(@PathVariable("id") String id,@RequestBody ProjectModel projectModel)
	{
		return projectService.updateProject(projectModel,id);
	}
	
	
	
	   //Requirement controller

		@PostMapping("/project/requirement/{id}")
		public String createRequirement(@PathVariable("id") String id, @RequestBody List<RequirementModel> requirementModelList) {

			return projectService.addRequirement(requirementModelList, id);
		}

		@PutMapping("/project/requirement/{id}/{rid}")
		public String updateRequirement(@PathVariable("id") String id, @PathVariable("rid") String rid,
				@RequestBody RequirementModel requirementModel) {
			
			return projectService.updateRequirement(requirementModel, id, rid,false);
		}
		
		
		@DeleteMapping("/project/requirement/{id}/{rid}")
		public String deleteRequirement(@PathVariable("id") String id, @PathVariable("rid") String rid,
				@RequestBody RequirementModel requirementModel) {
			
			 projectService.updateRequirement(requirementModel, id, rid,true);
			 return "requirement Deleted";
		}
	
	
	
	
}
