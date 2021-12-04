package com.example.demo.services;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.demo.schedulers.ScheduledTasks;
import com.example.demo.service.DashboardService;

@SpringBootTest
public class SchedulersTests {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	
	@SpyBean
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private ScheduledTasks scheduledTasks;
	
	
	@Test
	public void scheduleTaskWithFixedRateTest() {
		scheduledTasks.scheduleTaskWithFixedRate();
	}
	
	
	@Test
	public void testHistoryTest() {
		scheduledTasks.testHistory();
	}
		

}
