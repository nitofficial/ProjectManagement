package com.example.demo.services;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.ProjectModel;
import com.example.demo.service.ProjectService;
import com.example.demo.utilities.ProjectUtility;

@SpringBootTest
public class ProjectTests {

	@SpyBean
	private MongoTemplate mongoTemplate;

	@Autowired
	ProjectService service;

	@Test
	public void addprojectTest() {

		ProjectModel projectModel = new ProjectModel();

		when(mongoTemplate.insert(projectModel)).thenReturn(projectModel);

		assertEquals("Project Created with ID ", service.addProject(projectModel).substring(0, 24));
	}

	@Test
	public void addprojectTest_2() {

		ProjectModel projectModel = new ProjectModel();

		when(mongoTemplate.insert(projectModel)).thenReturn(null);

		assertEquals("Project Not Created", service.addProject(projectModel));
	}

	@Test
	public void getAllProjectsTest() {

		when(mongoTemplate.findAll(ProjectModel.class))
				.thenReturn(Stream.of(new ProjectModel()).collect(Collectors.toList()));

		assertEquals(1, service.getAllProjects().size());

	}

	@Test
	public void getByProjectIdTest() {

		Map<String, String> conditionsMap = new HashMap<String, String>();
		conditionsMap.put("id", "Prj-1");

		ProjectModel projectModel = new ProjectModel();

		when(mongoTemplate.findOne(ProjectUtility.getQueryByKeyValue(conditionsMap), ProjectModel.class))
				.thenReturn(new ProjectModel());
		assertEquals(projectModel.getId(), service.getByProjectId("Prj-1").getId());
	}

	@Test
	public void updateProjectTest() {

		ProjectModel projectModel = new ProjectModel();

		Map<String, String> conditionsMap = new HashMap<String, String>();

		when(mongoTemplate.save(projectModel)).thenReturn(null);
		when(mongoTemplate.findOne(ProjectUtility.getQueryByKeyValue(conditionsMap), ProjectModel.class))
				.thenReturn(new ProjectModel());

		Assertions.assertThrows(BadRequestException.class, () -> service.updateProject(projectModel, "Prd-1"));
	}

	@Test
	public void uniqueValueTest() {

		assertTrue(service.uniqueValue("tester") != -1);
	}

}
