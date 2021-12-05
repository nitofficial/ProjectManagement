package com.example.demo.services;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.demo.schedulers.ScheduledTasks;

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
		assertEquals("Scheduler Invoked", scheduledTasks.scheduleTaskWithFixedRate());
	}

	@Test
	public void testHistoryTest() {

		assertEquals("Scheduler Invoked", scheduledTasks.testHistory());

	}

}
