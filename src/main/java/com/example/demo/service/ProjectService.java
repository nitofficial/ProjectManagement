/**
	 * @author Sriram	
*/
package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.model.Counter;
import com.example.demo.model.ProjectModel;
import com.example.demo.model.RequirementModel;
import com.example.demo.model.TestCaseModel;
import com.example.demo.constants.Constants;
import com.example.demo.exception.BadRequestException;
import com.example.demo.utilities.ProjectUtility;

@Service
public class ProjectService {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private MongoOperations mongoOperation;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);

	/**
	 * Method to add Projects in the Mongo database
	 *
	 * @param ProjectModel which contains Project details
	 * @return Status and information of the Project Creation.
	 * 
	 */
	public String addProject(ProjectModel projectModel) {
		if (mongoTemplate.insert(projectModel) != null) {
			return "Project Created with ID " + projectModel.getId();
		} else {
			LOGGER.warn("PROJECT NOT CREATED");
			return "Project Not Created";
		}

	}

	/**
	 * Method to get every Projects from the Mongo database
	 * 
	 * @return List of Projects from the Mongo Database.
	 * @throws Handles Exception from Database read,write .
	 * 
	 */
	public List<ProjectModel> getAllProjects() {
		try {
			return mongoTemplate.findAll(ProjectModel.class);
		} catch (Exception e) {
			LOGGER.warn("PROJECTS NOT FOUND");
			throw new BadRequestException("Projects Not Found");
		}

	}

	/**
	 * Method to get specific Project by project Id from the Mongo Database
	 * 
	 * @param the Project id is passed.
	 * @return ProjectModel which contains Details of the project.
	 * @throws Handles Exception from Database read,write.
	 * 
	 */
	public ProjectModel getByProjectId(String id) {
		 try { 
			Map<String, String> conditionsMap = new HashMap<String, String>();
			conditionsMap.put("id", id);
			return mongoTemplate.findOne(ProjectUtility.getQueryByKeyValue(conditionsMap), ProjectModel.class);
		} catch (Exception e) {
			LOGGER.warn("PROJECT NOT FOUND");
			throw new BadRequestException("Project requested is not available");
		}

	}

	/**
	 * Method to update specific Project by project Id from the Mongo Database
	 * 
	 * @param the Project id and Projectmodel is passed.
	 * @return Status and information of the Project Update.
	 * @throws Handles Exception from Database read,write.
	 * 
	 */
	public String updateProject(ProjectModel projectModel, String id) {
		try {
			ProjectModel requestedProject = getByProjectId(id);
			if (projectModel.getName() != null) {
				requestedProject.setName(projectModel.getName());
			}
			if (projectModel.getDescription() != null) {
				requestedProject.setDescription(projectModel.getDescription());
			}
			if (projectModel.getStartDate() != null) {
				requestedProject.setStartDate(projectModel.getStartDate());
			}
			if (projectModel.getEndDate() != null) {
				requestedProject.setEndDate(projectModel.getEndDate());
			}
			if (projectModel.getTargetedRelease() != null) {
				requestedProject.setTargetedRelease(projectModel.getTargetedRelease());
			}
			requestedProject.addUpdateDate(requestedProject.getId() + " is Updated");

			if (mongoTemplate.save(requestedProject) != null) {
				LOGGER.info("PROJECT UPDATED SUCCESSFULLY");
				return "Project " + requestedProject.getId() + " Updated";
			} else {
				LOGGER.warn("PROJECT NOT UPDATED");
				throw new BadRequestException("Project could not be updated");
			}
		} catch (Exception e) {
			LOGGER.warn("PROJECT NOT UPDATED");
			throw new BadRequestException("Project could not be updated");
		}

	}

	/**
	 * Method to generate Unique value for Created Projects
	 * 
	 * @param the key of the Counter Document is passed.
	 * @return the unique Value of String dataType.
	 * @throws Handles Exception from Database read,write.
	 * 
	 */
	public int uniqueValue(String key) {
		try {
			Update update = new Update();
			update.inc(Constants.PROJECT_COUNTER_DOCUMENT_SEQUENCE_COLUMN, 1);
			FindAndModifyOptions options = new FindAndModifyOptions();
			options.returnNew(true).upsert(true);

			Map<String, String> conditionsMap = new HashMap<String, String>();
			conditionsMap.put("_id", key);

			Counter counter = mongoOperation.findAndModify(ProjectUtility.getQueryByKeyValue(conditionsMap), update,
					options, Counter.class);

			mongoTemplate.save(counter);

			return counter.getSeq();
		} catch (Exception e) {
			LOGGER.warn("PROJECT COUNTER NOT UPDATED");
			throw new BadRequestException("Project counter could not be updated");
		}

	}

	/**
	 * Method to add Requirements for the Project to the Mongo Database
	 * 
	 * @param the Project id and RequirementModel List is passed.
	 * @return status and information of the Added Requirement .
	 * @throws Handles Exception from Database read,write.
	 * 
	 */
	public String addRequirement(List<RequirementModel> requirementModelList, String projectId) {

		ProjectModel projectModel = getByProjectId(projectId);

		 try { 

		for (int requirementIndex = 0; requirementIndex < requirementModelList.size(); requirementIndex++) {
			RequirementModel requirementModel = requirementModelList.get(requirementIndex);
			requirementModel.setStatus(Constants.REQUIREMENT_VALID_STATUS);
			requirementModel.setProjectId(projectId);
			requirementModel.setRequirementId(
					Constants.REQUIREMENT_PREFIX + String.valueOf(projectModel.requirementCountIncrement()));
			requirementModel.setTestCaseCount(0);

			if (mongoTemplate.insert(requirementModel) == null) {
				LOGGER.warn("REQUIREMENT of id " + requirementModel.get_id() + " IS NOT INSERTED");
			}
		}

		if (mongoTemplate.save(projectModel) == null) {
			LOGGER.warn("REQUIREMENTS COULD NOT BE UPDATED");
		}
		
		  } catch(Exception e) { throw new
		  BadRequestException("there is no requirements found to be added"); }
		 

		return "requirements added";
	}

	/**
	 * Method to add Requirements for the Project to the Mongo Database
	 * 
	 * @param the Project id and RequirementModel List is passed.
	 * @return status and information of the Added Requirement .
	 * @throws Handles Exception from Database read,write.
	 * 
	 */
	public String updateRequirement(RequirementModel requirementModel, String id, String rid, boolean remove) {

		ProjectModel projectModel = getByProjectId(id);

		Map<String, String> conditionsMap = new HashMap<String, String>();
		conditionsMap.put("projectId", id);
		conditionsMap.put("id", rid);

		try {
			RequirementModel requestedRequirement = mongoTemplate
					.findOne(ProjectUtility.getQueryByKeyValue(conditionsMap), RequirementModel.class);
			if (remove) {
				requestedRequirement.setStatus(Constants.REQUIREMENT_INVALID_STATUS);
				updateTestcaseStatus(requestedRequirement.getRequirementId(), requestedRequirement.getProjectId(),
						Constants.TESTCASE_STATUS_ONHOLD);
			}

			if (requirementModel.getDescription() != null) {
				requestedRequirement.setDescription(requirementModel.getDescription());
			}

			if (requirementModel.getStatus() != null
					&& requirementModel.getStatus().equals(Constants.REQUIREMENT_VALID_STATUS)) {
				requestedRequirement.setStatus(requirementModel.getStatus());
				updateTestcaseStatus(requestedRequirement.getRequirementId(), requestedRequirement.getProjectId(),
						Constants.TESTCASE_STATUS_PASSED);
			}
			if (requirementModel.getInputParameters() != null) {
				requestedRequirement.setInputParameters(requirementModel.getInputParameters());
			}
			projectModel.addUpdateDate("Requirement " + requestedRequirement.getRequirementId() + " of project Id "
					+ projectModel.getId() + " is Updated");

			if (mongoTemplate.save(projectModel) == null) {
				LOGGER.warn("COULD NOT ADD UPDATE HISTORY FOR CHANGED REQUIREMENTS");
			}

			if (mongoTemplate.save(requestedRequirement) == null) {
				LOGGER.warn("COULD NOT SAVE THE  REQUIREMENT");
				throw new BadRequestException("could not save the requirement");
			} else {
				return "Requirement Updated";
			}

		} catch (Exception e) {
			LOGGER.warn("COULD NOT FIND THE REQUESTED REQUIREMENT");
			throw new BadRequestException("could not find the requested requirement");
		}

	}

	/**
	 * Method to Update TestCase Status when requirement Status is altered
	 * 
	 * @param the Project id , Requirement id is passed and status to be changed.
	 * @return Nothing .
	 * @throws Handles Exception from Database read,write.
	 * 
	 */
	public void updateTestcaseStatus(String requirementId, String projectId, String status) {
		Map<String, String> conditionsMap = new HashMap<String, String>();
		conditionsMap.put("projectId", projectId);
		conditionsMap.put("requirementId", requirementId);

		List<TestCaseModel> testCaseList = mongoTemplate.find(ProjectUtility.getQueryByKeyValue(conditionsMap),
				TestCaseModel.class);
		for (int testCaseIndex = 0; testCaseIndex < testCaseList.size(); testCaseIndex++) {
			TestCaseModel requestedTestCase = testCaseList.get(testCaseIndex);
			requestedTestCase.setStatus(status);
			if (mongoTemplate.save(requestedTestCase) == null) {
				LOGGER.warn("COULD NOT UPDATE " + requestedTestCase.get_id() + " TESTCASE");
			}
		}
	}

	/**
	 * Method to get all requirements from db
	 * 
	 * @return List<RequirementModel> with respective status and information.
	 * @throws BadRequestException handles Exception.
	 */
	public List<RequirementModel> getAllRequirements() {
		try {
			return mongoTemplate.findAll(RequirementModel.class);
		} catch (Exception e) {
			LOGGER.warn("REQUIREMENTS NOT FOUND");
			throw new BadRequestException("Requirements Not Found");
		}
	}

}
