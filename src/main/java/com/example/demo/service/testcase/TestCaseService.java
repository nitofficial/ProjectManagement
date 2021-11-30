package com.example.demo.service.testcase;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.model.projectcreation.RequirementModel;
import com.example.demo.model.testcase.TestCaseModel;
import com.example.demo.constants.Constants;
import com.example.demo.utilities.ProjectUtility;

@Service
public class TestCaseService {
	
	@Autowired
	private MongoTemplate mongoTemplate;


	/*
	 * public TestCaseService(MongoTemplate mongoTemplate) { this.mongoTemplate =
	 * mongoTemplate; }
	 */
	
	public TestCaseModel getByTestCaseId(String projectId,String requirementId,String testcaseId) {
		Map<String,String> conditionsMap=new HashMap<String,String>();
		conditionsMap.put("projectId",projectId );
		conditionsMap.put("requirementId",requirementId );
		conditionsMap.put("id",testcaseId );
		return mongoTemplate.findOne(ProjectUtility.getQueryByKeyValue(conditionsMap), TestCaseModel.class);
	}

	public List<TestCaseModel> getAllTestCase() {

		return mongoTemplate.findAll(TestCaseModel.class);
	}

	public String addTestCase(List<TestCaseModel> testcaseModelList) {
         
		
		 for(int testcaseIndex=0;testcaseIndex<testcaseModelList.size();testcaseIndex++)
		 {
			 TestCaseModel testcaseModel=testcaseModelList.get(testcaseIndex);
			 Map<String,String> conditionsMap=new HashMap<String,String>();
			 conditionsMap.put("projectId", testcaseModel.getProjectId());
			 conditionsMap.put("requirementId", testcaseModel.getRequirementId());
			 
			 testcaseModel.setStatus(Constants.TESTCASE_STATUS_PASSED);
			 RequirementModel requirementModel=mongoTemplate.findOne(ProjectUtility.getQueryByKeyValue(conditionsMap), RequirementModel.class);
			 if(requirementModel!=null)
			 {
				 testcaseModel.setTestcaseId(Constants.TESTCASE_PREFIX+String.valueOf(requirementModel.incrementTestCaseCount()));
				 mongoTemplate.insert(testcaseModel);
				 mongoTemplate.save(requirementModel);
			 }

		 }
		return "Inserted";
//		if (mongoTemplate.insert(testcaseModel) != null)
//			return " Inserted";
//		else
//			return "Not Inserted";

	}

//	public TestCaseCounter uniqueValue(String key) {
//
//		Update update = new Update();
//		update.inc(Constants.TESTCASE_COUNTER_DOCUMENT_SEQUENCE_COLUMN, 1);
//		FindAndModifyOptions options = new FindAndModifyOptions();
//		options.returnNew(true).upsert(true);
//		TestCaseCounter counter = mongoOperation.findAndModify(TestCaseUtility.getQueryByKeyValue("_id", key), update,
//				options, TestCaseCounter.class);
//
//		return counter;
//
//	}

	public String updateProject(TestCaseModel testCaseModel,String projectId,String requirementId,String testcaseId) {
		TestCaseModel requestedTestCase = getByTestCaseId(projectId,requirementId,testcaseId);

		if (testCaseModel == null) {
			requestedTestCase.setStatus(Constants.TESTCASE_STATUS_ONHOLD);
			mongoTemplate.save(requestedTestCase);
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

		mongoTemplate.save(requestedTestCase);

		return "TestCase " + requestedTestCase.getTestcaseId() + " Updated";

	}

}
