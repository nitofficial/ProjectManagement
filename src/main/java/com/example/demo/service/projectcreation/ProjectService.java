package com.example.demo.service.projectcreation;

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

import com.example.demo.model.projectcreation.Counter;
import com.example.demo.model.projectcreation.ProjectModel;
import com.example.demo.model.projectcreation.RequirementModel;
import com.example.demo.model.testcase.TestCaseModel;
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

	public String addProject(ProjectModel projectModel) {
		if (mongoTemplate.insert(projectModel) != null) {
			return "Project Created with ID " + projectModel.getId();
		} else {
			LOGGER.warn("PROJECT NOT CREATED");
			return "Project Not Created";
		}

	}

	public List<ProjectModel> getAllProjects() {
		try {
			return mongoTemplate.findAll(ProjectModel.class);
		} catch (Exception e) {
			LOGGER.warn("PROJECTS NOT FOUND");
			throw new BadRequestException("Projects Not Found");
		}

	}

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

	public String updateProject(ProjectModel projectModel, String id) {
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

	}

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

	public String addRequirement(List<RequirementModel> requirementModelList, String projectId) {

		ProjectModel projectModel = getByProjectId(projectId);

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

		return "requirements added";
	}

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

	private void updateTestcaseStatus(String requirementId, String projectId, String status) {
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

}
