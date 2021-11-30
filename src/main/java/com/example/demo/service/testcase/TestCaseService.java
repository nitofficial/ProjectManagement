package com.example.demo.service.testcase;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.demo.model.dashboard.IdOnly;
import com.example.demo.model.projectcreation.RequirementModel;
import com.example.demo.model.testcase.TestCaseModel;
import com.example.demo.constants.Constants;
import com.example.demo.exception.BadRequestException;
import com.example.demo.utilities.ProjectUtility;

@Service
public class TestCaseService {

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(TestCaseService.class);

	
	public List<IdOnly> getOpenTests() {
		Query q = new Query();
		q.addCriteria(Criteria.where("status").ne("Passed"));
		q.fields().include("id");
		List<IdOnly> a = mongoTemplate.find(q, IdOnly.class, Constants.TESTCASE_COLLECTION);
		System.out.print(a);
		return a;
	}

	public long getPassedTestsCount() {
		Query query = new Query();
		query.addCriteria(Criteria.where("status").is("Passed"));
		return mongoTemplate.count(query, TestCaseModel.class);
	}
	
	
	
	
	
	public TestCaseModel getByTestCaseId(String projectId, String requirementId, String testcaseId) {
		Map<String, String> conditionsMap = new HashMap<String, String>();
		conditionsMap.put("projectId", projectId);
		conditionsMap.put("requirementId", requirementId);
		conditionsMap.put("id", testcaseId);
		try {
			return mongoTemplate.findOne(ProjectUtility.getQueryByKeyValue(conditionsMap), TestCaseModel.class);
		} catch (Exception e) {
			LOGGER.warn("SPECIFIC TESTCASE NOT FOUND");
			throw new BadRequestException("Specific TestCase Not Found");
		}

	}

	public List<TestCaseModel> getAllTestCase() {
		try {
			return mongoTemplate.findAll(TestCaseModel.class);
		} catch (Exception e) {
			LOGGER.warn("TESTCASES NOT FOUND");
			throw new BadRequestException("TestCases Not Found");
		}

	}

	public String addTestCase(List<TestCaseModel> testcaseModelList) {

		for (int testcaseIndex = 0; testcaseIndex < testcaseModelList.size(); testcaseIndex++) {
			TestCaseModel testcaseModel = testcaseModelList.get(testcaseIndex);
			Map<String, String> conditionsMap = new HashMap<String, String>();
			conditionsMap.put("projectId", testcaseModel.getProjectId());
			conditionsMap.put("requirementId", testcaseModel.getRequirementId());

			testcaseModel.setStatus(Constants.TESTCASE_STATUS_PASSED);
			RequirementModel requirementModel = mongoTemplate.findOne(ProjectUtility.getQueryByKeyValue(conditionsMap),
					RequirementModel.class);
			if (requirementModel != null) {
				testcaseModel.setTestcaseId(
						Constants.TESTCASE_PREFIX + String.valueOf(requirementModel.incrementTestCaseCount()));

				if (mongoTemplate.insert(testcaseModel) == null) {
					LOGGER.warn("FEW TESTCASES ARE NOT INSERTED SUCCESSFULLY ");
				}
				if (mongoTemplate.save(requirementModel) == null) {
					LOGGER.warn("TESTCASE COUNT IS NOT UPDATED");
				}
			} else {
				LOGGER.warn("REQUIREMENT ID DOESNT EXIST");
				throw new BadRequestException("Requirement id Doesnt exist");
			}

		}
		return "Inserted";

	}

	public String updateProject(TestCaseModel testCaseModel, String projectId, String requirementId,
			String testcaseId) {
		TestCaseModel requestedTestCase = getByTestCaseId(projectId, requirementId, testcaseId);

		if (testCaseModel == null) {
			requestedTestCase.setStatus(Constants.TESTCASE_STATUS_ONHOLD);
			if (mongoTemplate.save(requestedTestCase) == null) {
				LOGGER.warn("TESTCASE COULD NOT BE DELETED");
				throw new BadRequestException("Testcase could not be deleted");
			}
			return "testCase Deleted";
		}

		if (testCaseModel.getName() != null) {
			requestedTestCase.setName(testCaseModel.getName());
		}
		if (testCaseModel.getDescription() != null) {
			requestedTestCase.setDescription(testCaseModel.getDescription());
		}
		if (testCaseModel.getExpectedResults() != null) {
			requestedTestCase.setExpectedResults(testCaseModel.getExpectedResults());
		}
		if (testCaseModel.getInputParameters() != null) {
			requestedTestCase.setInputParameters(testCaseModel.getInputParameters());
		}
		if (testCaseModel.getActualResults() != null) {
			requestedTestCase.setActualResults(testCaseModel.getActualResults());
		}
		if (testCaseModel.getStatus() != null && !testCaseModel.getStatus().equals(Constants.TESTCASE_STATUS_ONHOLD)) {
			requestedTestCase.setStatus(testCaseModel.getStatus());
		}

		if (mongoTemplate.save(requestedTestCase) == null) {
			LOGGER.warn("TESTCASE COULD NOT BE UPDATED");
			throw new BadRequestException("Testcase could not be updated");

		} else {
			return "TestCase " + requestedTestCase.getTestcaseId() + " Updated";
		}

	}

}
