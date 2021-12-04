package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.demo.model.DashRTMModel;
import com.example.demo.model.FileModel;
import com.example.demo.service.DashboardService;

@SpringBootTest
public class DashboardTests {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
//new comment
	@SpyBean
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private DashboardService dashboardservice;
	
	
	@Test
	public void getRTMTest() {
		dashboardservice.getRTM();
		
	}
	
	

}
