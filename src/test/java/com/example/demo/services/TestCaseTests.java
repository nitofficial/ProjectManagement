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
import com.example.demo.service.TestCaseService;
import com.example.demo.utilities.ProjectUtility;

@SpringBootTest
public class TestCaseTests {

	@SpyBean
	private MongoTemplate mongoTemplate;

	@Autowired
	TestCaseService service;

	@Test
	public void getByTestCaseIdTest() {

		Map<String, String> conditionsMap = new HashMap<String, String>();
		conditionsMap.put("projectId", "");
		conditionsMap.put("requirementId", "");
		conditionsMap.put("id", "");

		TestCaseModel testCaseModel = new TestCaseModel();

		when(mongoTemplate.findOne(ProjectUtility.getQueryByKeyValue(conditionsMap), TestCaseModel.class))
				.thenReturn(new TestCaseModel());
		assertEquals(testCaseModel.get_id(), service.getByTestCaseId("", "", "").get_id());
	}

	@Test
	public void getAllTestCaseTest() {

		when(mongoTemplate.findAll(TestCaseModel.class))
				.thenReturn(Stream.of(new TestCaseModel()).collect(Collectors.toList()));

		assertEquals(1, service.getAllTestCase().size());

	}

	@Test
	public void addprojectTest() {
		Assertions.assertThrows(BadRequestException.class, () -> service.addTestCase(null));

	}

	@Test
	public void updateTestCaseTest() {
		Assertions.assertThrows(BadRequestException.class,
				() -> service.updateTestCase(new TestCaseModel(), null, null, null));

	}

	@Test
	public void updateTestCaseTest_2() {
		Assertions.assertThrows(BadRequestException.class, () -> service.updateTestCase(null, null, null, null));

	}

}
