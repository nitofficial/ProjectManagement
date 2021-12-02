package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.TestCaseModel;
import com.example.demo.service.ProjectService;
import com.example.demo.utilities.ProjectUtility;

@SpringBootTest
public class RequirementTests {

	@SpyBean
	private MongoTemplate mongoTemplate;

	@Autowired
	ProjectService service;

	@Test
	public void addRequirementTest() {

		Assertions.assertThrows(BadRequestException.class, () -> service.addRequirement(null, "project test id"));

	}

	@Test
	public void updateRequirementTest() {

		Assertions.assertThrows(BadRequestException.class, () -> service.updateRequirement(null, null, null, false));

	}

	@Test
	public void updateRequirementTest_2() {

		Assertions.assertThrows(BadRequestException.class, () -> service.updateRequirement(null, null, null, true));

	}

	@Test
	public void updateTestcaseStatusTest() {
		Map<String, String> conditionsMap = new HashMap<String, String>();
		conditionsMap.put("projectId", "");
		conditionsMap.put("requirementId", "");
		TestCaseModel testCaseModel = new TestCaseModel();
		testCaseModel.setStatus("Failed");
		when(mongoTemplate.find(ProjectUtility.getQueryByKeyValue(conditionsMap), TestCaseModel.class))
				.thenReturn(Stream.of(testCaseModel).collect(Collectors.toList()));
		when(mongoTemplate.save(testCaseModel)).thenReturn(null);
		service.updateTestcaseStatus("", "", "passed");
		assertEquals("passed", testCaseModel.getStatus());

	}

}
