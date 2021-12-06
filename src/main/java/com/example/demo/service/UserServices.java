/**
	 * @author Sanjay	
*/
package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.PendingRequest;
import com.example.demo.model.User;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.UserDetailsImpl;

@Service
public class UserServices {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	ProjectService projectService;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * Service to add new user to the application.
	 * 
	 * @param user name, password and email.
	 * @return MessageResponse stating that the user has been successfully registered.
	 */
	public MessageResponse registerUser(String username, String password, String email) {
		if (userRepository.existsByUsername(username)) {
			return new MessageResponse("Error: Username is already in use!");
		}

		if (userRepository.existsByEmail(email)) {
			return new MessageResponse("Error: Email is already in use!");
		}

		// Create new user's account
		User user = new User(username, encoder.encode(email), password);
		user.setId("USR_" + String.valueOf(projectService.uniqueValue(User.SEQUENCE_NAME)));
		user.setIsuserStatusActive(true);
		userRepository.save(user);
		return new MessageResponse("User registered successfully!");
	}
	
	/**
	 * MService to generate Bearer token for user to sign in the application.
	 * 
	 * @param user name and password.
	 * @return JWTResponse with user-name and access token.
	 */
	public JwtResponse userSignIn(String username, String password) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return new JwtResponse(jwt, username, roles);
	}
	
	/**
	 * Service that allow users to request roles.
	 * 
	 * @param user id and requested role name.
	 * @return MessageResponse stating that the request has been initiated successfully.
	 */
	public MessageResponse addRoletoUser(String userid, String roleRequested) {

		PendingRequest pendingRequest = new PendingRequest(userid, roleRequested);
		pendingRequest.setRequestid("REQ_" + String.valueOf(projectService.uniqueValue(PendingRequest.SEQUENCE_NAME)));
		mongoTemplate.save(pendingRequest);
		return new MessageResponse("Request has been sent to the admin");
	}
	
	/**
	 * Service to display the information of the current user
	 * 
	 * @param user id.
	 * @return MessageResponse with the details of the currently logged in user.
	 */
	public User displayUserDetail(String userid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(userid));
		query.fields().exclude("password");
		return mongoTemplate.findOne(query, User.class);
	}
	
	/**
	 * Service to update current user information in the database.
	 * 
	 * @param user name and User object.
	 * @return MessageResponse with the updated user object.
	 */
	public User updateUserDetails(String username, User user) {
		Query query = Query.query(Criteria.where("username").is(username));
		Update update = new Update();
		update.set("email", user.getEmail());
		update.set("phonenumber", user.getPhonenumber());
		mongoTemplate.updateMulti(query, update, User.class);
		return user;
	}
}