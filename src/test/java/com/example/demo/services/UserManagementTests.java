package com.example.demo.services;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AdminServices;
import com.example.demo.service.UserServices;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserManagementTests {

	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException thrown = ExpectedException.none();
//new comment
	@Autowired
	UserServices userServices;

	@MockBean
	private UserRepository userRepository;

	@Autowired
	AdminServices adminServices;
	
//	@MockBean
//	@Autowired
//	AdminServices adminServicesMock;

	@SpyBean
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Test
	public void registerUserTest() throws IOException {
		User user = new User("TESTUSR_SANJAY", "12345678", "TEST1@GMAIL.COM", "1234567890");
		assertTrue(userServices.registerUser(user.getUsername(), user.getPassword(), user.getEmail()) instanceof MessageResponse);
	}
	
	public void userSignInTest() throws IOException {
		User user = new User("TESTUSR_SANJAY", "12345678", "TEST1@GMAIL.COM", "1234567890");
		assertTrue(userServices.userSignIn(user.getUsername(), user.getPassword()) instanceof JwtResponse);
	}
	
	@Test
	public void updateUserDetailsTest() throws IOException {
		User user = new User("TESTUSR_SANJAY", "12345678", "TEST1@GMAIL.COM", "1234567890");
		assertTrue(userServices.updateUserDetails(user.getUsername(),user) instanceof User);
	}
	
	@Test
	public void displayUserDetailTest() throws IOException {
		String userid = "TESTUSR_1";
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(userid));
		query.fields().exclude("password");
		Mockito.when(mongoTemplate.findOne(query, User.class))
		.thenReturn((new User("TESTUSR_1","TESTUSR_SANJAY", "12345678", "TEST1@GMAIL.COM", "1234567890")));
		assertTrue(userServices.displayUserDetail(userid) instanceof User);
	}

	// JUnit test for displayAllUserDetail service
	@SuppressWarnings("unchecked")
	@Test
	public void displayAllUserDetailTest() throws IOException {
		Query query = new Query();
		query.fields().exclude("password");
		Mockito.when(mongoTemplate.find(query, User.class))
				.thenReturn(Stream
						.of(new User("TESTUSR_SANJAY", "12345678", "TEST1@GMAIL.COM", "1234567890"),
								new User("TESTUSR_SRIRAM", "87654321", "TEST2@GMAIL.COM", "0987654321"),
								new User("TESTUSR_Nithish", "87654321", "TEST3@GMAIL.COM", "0987654321"))
						.collect(Collectors.toList()));
		assertEquals(3, ((List<User>) adminServices.displayAllUserDetail()).size());
	}
	
	
	@Test
	public void displayAllRoleDetailTest() throws IOException {
		Mockito.when(mongoTemplate.findAll(Role.class))
				.thenReturn(Stream.of(new Role("TESTROLE_1", "TESTROLE_BATMAN", true),
						new Role("TESTROLE_2", "TESTROLE_SUPERMAN", false),
						new Role("TESTROLE_3", "TESTROLE_IRONMAN", false)).collect(Collectors.toList()));
		assertEquals(3, adminServices.displayAllRoleDetail().size());
	}

	@Test
	public void displayAllActiveRoleDetailTest() throws IOException {
		Query query = new Query().addCriteria(Criteria.where("isrolestatusactive").is(true));
		Mockito.when(mongoTemplate.find(query, Role.class)).thenReturn(Stream
				.of(new Role("TESTROLE_1", "TESTROLE_BATMAN", true))
				.collect(Collectors.toList()));
		assertEquals(1, adminServices.displayAllActiveRoleDetail().size());
	}

	@Test
	public void addNewRoleTest() throws IOException {
		Role role = new Role("TESTROLE_1", "TESTROLE_THUNDERMAN", true);
		Mockito.when(mongoTemplate.save(role)).thenReturn(new Role("TESTROLE_2", "TESTROLE_SUPERMAN", true));
		assertTrue(adminServices.addNewRole(role.getName()) instanceof MessageResponse);
	}

	@Test
	public void deleteRoleTest() throws IOException {
		Role role = new Role("TESTROLE_1", "TESTROLE_BATMAN", true);
		Mockito.when(mongoTemplate.save(role)).thenReturn(role);
		assertTrue(adminServices.deleteRole(role.getId()) instanceof MessageResponse);
	}
	
	@Test
	public void updateRoleTest() throws IOException {
		Role role = new Role("TESTROLE_1", "TESTROLE_BATMAN", true);
		Mockito.when(mongoTemplate.save(role)).thenReturn(role);
		assertTrue(adminServices.updateRole(role.getId(),role.getName(), role.getIsRolestatusactive()) instanceof MessageResponse);
	}
}
