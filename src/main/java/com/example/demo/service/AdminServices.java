/**
	 * @author Sanjay	
*/
package com.example.demo.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.payload.response.MessageResponse;

@Service
public class AdminServices {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServices.class);

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	ProjectService projectService;

	// Service that allows administrator to add requested roles to a specific user
	public User addRoleToUser(String username, String roleRequested) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("username").is(username));

			Role role = mongoTemplate.findOne(
					new Query().addCriteria(Criteria.where("name").is("ROLE_" + roleRequested.toUpperCase())), Role.class);
			Update update = new Update().addToSet("roles", role);

			return mongoOperations.findAndModify(query, update, options().returnNew(true).upsert(false), User.class);
		}catch(Exception e) {
			LOGGER.warn(e.getMessage());
			throw new BadRequestException(e.getMessage());
		}
	}

	// Service that allows administrator to remove a role from a specific user
	public MessageResponse deleteRoleFromUser(String username, String roleRequested) {
		Role role = mongoTemplate.findOne(
				new Query().addCriteria(Criteria.where("name").is("ROLE_" + roleRequested.toUpperCase())), Role.class);
		Query query = Query.query(Criteria.where("username").is(username));
		Query query2 = Query.query(Criteria.where("$id").is(new ObjectId(role.getId())));
		Update update = new Update().pull("roles", query2);
		mongoTemplate.updateMulti(query, update, User.class);
		return new MessageResponse(roleRequested + " Role has been successful removed from the user " + username);
	}

	// Service that allows administrator to add new role to the application
	public MessageResponse addNewRole(String rolename) {
		Role role = new Role("ROLE_"+rolename.toUpperCase());
		role.setId("ROLE_"+String.valueOf(projectService.uniqueValue(Role.SEQUENCE_NAME)));
		mongoTemplate.save(role);
		return new MessageResponse("Role added successfully");
	}
}