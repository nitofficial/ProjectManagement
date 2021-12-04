/**
	 * @author Sanjay	
*/
package com.example.demo.repository;

import java.util.Optional;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Role;
import com.example.demo.model.User;

public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	
	default Boolean existByRoles(Role role, User user) {
		for (Object object : user.getRoles()) {
			if(object == role)
				return true;
		}
		return false;
	};
}
