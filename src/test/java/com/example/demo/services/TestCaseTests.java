/**
	 * @author Sriram	
*/
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
import com.example.demo.model.ProjectModel;
import com.example.demo.model.RequirementModel;
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
		conditionsMap.put("projectId","");
		conditionsMap.put("requirementId", "");
		conditionsMap.put("testcaseId", "");

		TestCaseModel testCaseModel=new TestCaseModel();

		when(mongoTemplate.findOne(ProjectUtility.getQueryByKeyValue(conditionsMap), TestCaseModel.class)).thenReturn(new TestCaseModel());
		assertEquals(testCaseModel.get_id(), service.getByTestCaseId("", "", "").get_id());
		}
	
	@Test
	public void getAllTestCaseTest() {
		
		
		when(mongoTemplate.findAll(TestCaseModel.class))
		.thenReturn(Stream.of(new TestCaseModel()).collect(Collectors.toList()));

               assertEquals(1,service.getAllTestCase().size());

	}
	
	
	  @Test 
	  public void addTestCaseTest() {
		  Assertions.assertThrows(BadRequestException.class, () -> service.addTestCase(null));
	 
	  }
	  @Test 
	  public void addTestCaseTest_2() {
		  
		  TestCaseModel testCaseModel=new TestCaseModel();
		  RequirementModel requirementModel=new RequirementModel();
		  Map<String, String> conditionsMap = new HashMap<String, String>();
			conditionsMap.put("projectId","");
			conditionsMap.put("requirementId", "");
		  when(mongoTemplate.findOne(ProjectUtility.getQueryByKeyValue(conditionsMap),
					RequirementModel.class))
			.thenReturn(requirementModel);
		  when(mongoTemplate.insert(testCaseModel))
			.thenReturn(testCaseModel);
		  when(mongoTemplate.save(requirementModel))
			.thenReturn(requirementModel);
		  
		  assertEquals("Inserted",service.addTestCase(Stream.of(new TestCaseModel()).collect(Collectors.toList())));
	 
	  }
	  
		
		  @Test 
		  public void updateTestCaseTest() {
			  assertEquals("TestCase",service.updateTestCase(new TestCaseModel(),null,null,null).substring(0, 8));
		 // Assertions.assertThrows(BadRequestException.class, () ->service.updateTestCase(new TestCaseModel(),null,null,null));
		  
		  }
		  
		  @Test 
		  public void updateTestCaseTest_3() {
			  TestCaseModel testCaseModel=new TestCaseModel();
			  testCaseModel.setName("test");
			  testCaseModel.setDescription("test");
			  testCaseModel.setExpectedResults("test");
			  testCaseModel.setInputParameters("test");
			  testCaseModel.setActualResults("test");
			  testCaseModel.setStatus("test");
			  when(mongoTemplate.save(testCaseModel))
				.thenReturn(testCaseModel);
			  assertEquals("TestCase",service.updateTestCase(testCaseModel, "Prj-test", "Req-test", "Test-test").substring(0, 8));
		 // Assertions.assertThrows(BadRequestException.class, () ->service.updateTestCase(new TestCaseModel(),null,null,null));
		  
		  }
		  
		 
	  
	  @Test 
	  public void updateTestCaseTest_2() {
		  assertEquals("testCase Deleted",service.updateTestCase(null,null,null,null));
		  //Assertions.assertThrows(BadRequestException.class, () -> service.updateTestCase(null,null,null,null));

	  }
	 
	
}
