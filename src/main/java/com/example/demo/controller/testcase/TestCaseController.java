package com.example.demo.controller.testcase;

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

import com.example.demo.model.testcase.TestCaseModel;
import com.example.demo.service.testcase.TestCaseService;


@RequestMapping("/api/v1")
@RestController
public class TestCaseController {

	 
		@Autowired
		private TestCaseService testCaseService;
		
		@PostMapping("/testcase")	
		public String addTestCases(@RequestBody List<TestCaseModel> testCaseModelList)
		{
//			TestCaseCounter counter=testCaseService.uniqueValue(Constants.TESTCASE_COUNTER_DOCUMENT_ID);
//			testCaseModel.setId("Test-"+String.valueOf(counter.getSeq()));
			
			return testCaseService.addTestCase(testCaseModelList);
		}
		
		@GetMapping("/testcase")	
		public List<TestCaseModel> allTestCase()
		{
			return testCaseService.getAllTestCase();
		}
		
		//do here
		@GetMapping("/testcase/{pid}/{rid}/{tid}")
		public TestCaseModel testcaseByID(@PathVariable("pid") String projectId,@PathVariable("rid") String requirementId,@PathVariable("tid") String testcaseId){
			return testCaseService.getByTestCaseId(projectId,requirementId,testcaseId);
			
		}
		
		@PutMapping("/testcase/{pid}/{rid}/{tid}")	
		public String updateTestCase(@PathVariable("pid") String projectId,@PathVariable("rid") String requirementId,@PathVariable("tid") String testcaseId,@RequestBody TestCaseModel testCaseModel)
		{
			return testCaseService.updateProject(testCaseModel,projectId,requirementId,testcaseId);
		}
		
		@DeleteMapping("/testcase/{pid}/{rid}/{tid}")
		public String deleteTestCase(@PathVariable("pid") String projectId,@PathVariable("rid") String requirementId,@PathVariable("tid") String testcaseId) {
			
			return testCaseService.updateProject(null,projectId,requirementId,testcaseId);
			
		}
		
}

