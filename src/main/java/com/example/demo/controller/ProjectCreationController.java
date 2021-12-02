package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ProjectModel;
import com.example.demo.model.RequirementModel;
import com.example.demo.service.ProjectService;
import com.example.demo.constants.Constants;

@RequestMapping("/api/v1")
@RestController
public class ProjectCreationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectCreationController.class);

	@Autowired
	private ProjectService projectService;

	/**
	 * Method to create project
	 *
	 * 
	 * @param ProjectModel
	 * @return FileModel with respective status and information.
	 */
	@PostMapping("/project")
	public String createProject(@RequestBody ProjectModel projectModel) {
		LOGGER.info("IN CREATE PROJECT");
		projectModel.setId(Constants.PROJECT_PREFIX
				+ String.valueOf(projectService.uniqueValue(Constants.PROJECT_COUNTER_DOCUMENT_ID)));

		projectModel.setRequirementsCount(0);
		return projectService.addProject(projectModel);
	}

	
	@GetMapping("/project")
	public List<ProjectModel> allProjects() {
		LOGGER.info("IN REQUESTING ALL PROJECTS");
		return projectService.getAllProjects();
	}

	@GetMapping("/project/{id}")
	public ProjectModel projectByID(@PathVariable("id") String id) {
		LOGGER.info("IN REQUESTING SPECIFIC PROJECT");
		return projectService.getByProjectId(id);

	}

	@PutMapping("/project/{id}")
	public String updateProject(@PathVariable("id") String id, @RequestBody ProjectModel projectModel) {
		LOGGER.info("IN UPDATING PROJECT");
		return projectService.updateProject(projectModel, id);
	}

	// Requirement controller

	@PostMapping("/project/requirement/{id}")
	public String createRequirement(@PathVariable("id") String id,
			@RequestBody List<RequirementModel> requirementModelList) {
		LOGGER.info("IN CREATE/ADD REQUIREMENTS");
		return projectService.addRequirement(requirementModelList, id);
	}

	@PutMapping("/project/requirement/{id}/{rid}")
	public String updateRequirement(@PathVariable("id") String id, @PathVariable("rid") String rid,
			@RequestBody RequirementModel requirementModel) {
		LOGGER.info("IN UPDATE REQUIREMENTS");
		return projectService.updateRequirement(requirementModel, id, rid, false);
	}

	@DeleteMapping("/project/requirement/{id}/{rid}")
	public String deleteRequirement(@PathVariable("id") String id, @PathVariable("rid") String rid,
			@RequestBody RequirementModel requirementModel) {
		LOGGER.info("IN DELETE REQUIREMENTS");
		projectService.updateRequirement(requirementModel, id, rid, true);
		return "requirement Deleted";
	}

}
