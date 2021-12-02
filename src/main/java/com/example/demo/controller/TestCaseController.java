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

import com.example.demo.model.TestCaseModel;
import com.example.demo.service.TestCaseService;

@RequestMapping("/api/v1")
@RestController
public class TestCaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestCaseController.class);

	@Autowired
	private TestCaseService testCaseService;

	@PostMapping("/testcase")
	public String addTestCases(@RequestBody List<TestCaseModel> testCaseModelList) {
		LOGGER.info("IN ADD/CREATE TESTCASES");
		return testCaseService.addTestCase(testCaseModelList);
	}

	@GetMapping("/testcase")
	public List<TestCaseModel> allTestCase() {
		LOGGER.info("IN GETTING ALL TESTCASES");
		return testCaseService.getAllTestCase();
	}

	@GetMapping("/testcase/{pid}/{rid}/{tid}")
	public TestCaseModel testcaseByID(@PathVariable("pid") String projectId, @PathVariable("rid") String requirementId,
			@PathVariable("tid") String testcaseId) {
		LOGGER.info("IN GETTING SPECIFIC TESTCASE");
		return testCaseService.getByTestCaseId(projectId, requirementId, testcaseId);

	}

	@PutMapping("/testcase/{pid}/{rid}/{tid}")
	public String updateTestCase(@PathVariable("pid") String projectId, @PathVariable("rid") String requirementId,
			@PathVariable("tid") String testcaseId, @RequestBody TestCaseModel testCaseModel) {
		LOGGER.info("IN UPDATE TESTCASES");
		return testCaseService.updateTestCase(testCaseModel, projectId, requirementId, testcaseId);
	}

	@DeleteMapping("/testcase/{pid}/{rid}/{tid}")
	public String deleteTestCase(@PathVariable("pid") String projectId, @PathVariable("rid") String requirementId,
			@PathVariable("tid") String testcaseId) {
		LOGGER.info("IN DELETE TESTCASES");
		return testCaseService.updateTestCase(null, projectId, requirementId, testcaseId);

	}

}
