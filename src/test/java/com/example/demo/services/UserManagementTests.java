package com.example.demo.services;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.demo.model.User;
import com.example.demo.service.AdminServices;

@SpringBootTest
public class UserManagementTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
//new comment
	@SpyBean
	private MongoTemplate mongoTemplate;

	@Autowired
	AdminServices adminServices;

	@Test
	public void displayAllUserDetailTest() throws IOException {

		assertTrue(adminServices.displayAllUserDetail() instanceof Object);

	}

}
