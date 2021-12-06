/**
	 * @author Sanjay	
*/
package com.example.demo.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import java.util.List;

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
import com.example.demo.model.PendingRequest;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.PendingRequestRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class AdminServices {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServices.class);

	@Autowired
	private MongoOperations mongoOperations;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	ProjectService projectService;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PendingRequestRepository pendingRequestRepository;
	
	/**
	 * Service that displays all the users in the application.
	 * @return MessageResponse with information about all the users.
	 */
	public Object displayAllUserDetail() {
		Query query = new Query();
		query.fields().exclude("password");
		List<User> allUsers = mongoTemplate.find(query, User.class);
		if (allUsers == null)
			return new MessageResponse("No user is available in the system");
		return allUsers;
	}
	
	/**
	 * Service that deletes an user from the user collection.
	 * 
	 * @param user id
	 * @return MessageResponse stating that the particular user has been successfully deleted.
	 */
	public MessageResponse deleteUser(String userid) {
		try {
			if(!userRepository.existsById(userid))
				return new MessageResponse(userid + "does not exist!"); 
			mongoTemplate.findAndRemove(new Query().addCriteria(Criteria.where("id").is(userid)), User.class);
			return new MessageResponse(userid+ " has been successfully removed from the system.");
		} catch(Exception e) {
			LOGGER.warn(e.getMessage());
			throw new BadRequestException("Request format is wrong!");
		}
	}
	
	/**
	 * Service that allows administrator to add requested roles to a specific user.
	 * 
	 * @param request id, user id and role id.
	 * @return User object.
	 */
	public Object addRoleToUser(String requestid, String userid, String requestedroleid) {
		try {
			if (!pendingRequestRepository.existsByRequestid(requestid)) {
				return new MessageResponse("Error: Request never made");
			}
			if (!roleRepository.existsById(requestedroleid)) {
				return new MessageResponse("Error: Role is not available");
			}
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(userid));
			Role role = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("id").is(requestedroleid)),
					Role.class);
			Update update = new Update().addToSet("roles", role);
			PendingRequest pendingRequest= mongoTemplate.findOne(new Query().addCriteria(Criteria.where("requestid").is(requestid)),
					PendingRequest.class);
			pendingRequest.setIsrequestgranted(true);
			mongoTemplate.save(pendingRequest, "pending_requests");
			return mongoOperations.findAndModify(query, update, options().returnNew(true).upsert(false), User.class);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
			throw new BadRequestException("Request format is wrong!");
		}
	}
	
	/**
	 * Service that allows administrator to remove a role from a specific user.
	 * 
	 * @param user id and role id.
	 * @return MessageResponse stating that the role is removed successfully from the particular user.
	 */
	public MessageResponse deleteRoleFromUser(String userid, String roleid) {
		try {
			Role role = mongoTemplate.findOne(
					new Query().addCriteria(Criteria.where("id").is(roleid)),
					Role.class);
			Query query = Query.query(Criteria.where("id").is(userid));
			Query query2 = Query.query(Criteria.where("$id").is(new ObjectId(roleid)));
			Update update = new Update().pull("roles", query2);
			mongoTemplate.updateMulti(query, update, User.class);
			return new MessageResponse(role.getName() + " Role has been successful removed from the user " + userid);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
			throw new BadRequestException("Request format is wrong!");
		}
	}
	
	/**
	 * Service that allows administrator to add new role to the application,
	 * 
	 * @param role name.
	 * @return MessageResponse stating the the new role has been add to the Role collection.
	 */
	public MessageResponse addNewRole(String rolename) {
		try {
			if (roleRepository.existsByName("ROLE_" + rolename.toUpperCase())) {
				return new MessageResponse("Error: Role is already in use!");
			}
			Role role = new Role("ROLE_" + rolename.toUpperCase());
			role.setId("ROLE_" + String.valueOf(projectService.uniqueValue(Role.SEQUENCE_NAME)));
			role.setIsRolestatusactive(true);
			mongoTemplate.save(role);
			return new MessageResponse("Role added successfully");
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
			throw new BadRequestException("Request format is wrong!");
		}
	}
	
	/**
	 * Service that allows administrator to delete role from the application.
	 * 
	 * @param role id.
	 * @return MessageResponse stating that the role is successfully set inactive.
	 */
	public MessageResponse deleteRole(String roleid) {
		if (!roleRepository.existsById(roleid)) {
			return new MessageResponse("Error: Role is not available");
		}
		Query query = Query.query(Criteria.where("_id").is(roleid));
		Role role = mongoTemplate.findOne(query, Role.class);
		role.setIsRolestatusactive(false);
		mongoTemplate.save(role);

		Query query2 = Query.query(Criteria.where("id").exists(true));
		Query query3 = Query.query(Criteria.where("$id").is(role.getId()));
		Update update = new Update().pull("roles", query3);
		mongoTemplate.updateMulti(query2, update, User.class);
		return new MessageResponse("Role is set inactive");
	}
	
	/**
	 * Service that allows administrator to update a role in the application.
	 * 
	 * @param role id, role name and role status.
	 * @return MessageResponse stating that the role has been updated successfully.
	 */
	public MessageResponse updateRole(String roleid, String rolename, Boolean rolestatus) {
		if (!roleRepository.existsById(roleid)) {
			return new MessageResponse("Error: Role is not available");
		}
		rolename = "ROLE_" + rolename.toUpperCase();
		Query query = new Query().addCriteria(Criteria.where("_id").is(roleid));
		Update update = new Update().set("name", rolename).set("isrolestatusactive", rolestatus);
		mongoTemplate.findAndModify(query, update, Role.class);
		return new MessageResponse("Role has been updated");
	}

	/**
	 * Service that allows administrator to delete role from the application.
	 * @return  List<Role>.
	 */
	public List<Role> displayAllRoleDetail() {
		try {
			return mongoTemplate.findAll(Role.class);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
			throw new BadRequestException("Request format is wrong!");
		}
	}

	/**
	 * Service that allows administrator to delete role from the application.
	 * @return List<Role>.
	 */
	public List<Role> displayAllActiveRoleDetail() {
		try {
			return mongoTemplate.find(new Query().addCriteria(Criteria.where("isrolestatusactive").is(true)),
					Role.class);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
			throw new BadRequestException("Request format is wrong!");
		}
	}
}