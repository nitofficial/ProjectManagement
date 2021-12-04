/**
	 * @author Sanjay	
*/
package com.example.demo.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.PendingRequest;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.UserDetailsImpl;
import com.example.demo.service.AdminServices;
import com.example.demo.service.UserServices;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

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
	UserServices userServices;

	@Autowired
	AdminServices adminServices;

	// API to display the Logged in user's details
	@GetMapping("/userinfo")
	public ResponseEntity<?> displayUserDetail() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
		return ResponseEntity.ok(userServices.displayUserDetail(user.getUsername()));
	}

	// API to update the details of the currently logged in user
	@PutMapping("/updateuser")
	public ResponseEntity<?> updateEmployee(@RequestBody User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userN = (UserDetailsImpl) auth.getPrincipal();
		return ResponseEntity.ok(userServices.updateUserDetails(userN, user));
	}

	// API for registered users to request roles from the Administrator
	@PreAuthorize("#pendingRequest.getUserid() == authentication.principal.id")
	@PostMapping("/requestrole")
	public ResponseEntity<?> requestRole(@Valid @RequestBody PendingRequest pendingRequest) {
		return ResponseEntity
				.ok(userServices.addRoletoUser(pendingRequest.getUserid(), pendingRequest.getRequestedroleid()));
	}

	// API specific for Administrator to view all the users in the system
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/alluserinfo")
	public ResponseEntity<?> displayAllUserDetail() {
		return ResponseEntity.ok(adminServices.displayAllUserDetail());
	}

	// API specific for Administrator to grant requested roles to the appropriate
	// user
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/addroletouser")
	public ResponseEntity<?> addRoleToUser(@Valid @RequestBody HashMap<String, String> dataHashMap) {
		return ResponseEntity
				.ok(adminServices.addRoleToUser(dataHashMap.get("requestid"),dataHashMap.get("userid"), dataHashMap.get("requestedroleid")));
	}

	// API specific for Administrator to remove a role from a particular user
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/deleterolefromuser")
	public ResponseEntity<?> deleteRoleFromUser(@Valid @RequestBody HashMap<String, String> dataHashMap) {
		return ResponseEntity
				.ok(adminServices.deleteRoleFromUser(dataHashMap.get("username"), dataHashMap.get("roleRequested")));
	}

	// API specific for Administrator to add new role to the application
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
	@PostMapping("/addnewrole")
	public ResponseEntity<?> addNewRole(@Valid @RequestBody HashMap<String, String> dataHashMap) {
		return ResponseEntity.ok(adminServices.addNewRole(dataHashMap.get("rolename")));
	}

	// API specific for Administrator to view all the users in the system
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/allroleinfo")
	public ResponseEntity<?> displayAllRoleDetail() {
		return ResponseEntity.ok(adminServices.displayAllRoleDetail());
	}

	// API specific for Administrator to view all the users in the system
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/allactiveroleinfo")
	public ResponseEntity<?> displayAllActiveRoleDetail() {
		return ResponseEntity.ok(adminServices.displayAllActiveRoleDetail());
	}

	// API specific for Administrator to delete a role to the application
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
	@PostMapping("/deleterole")
	public ResponseEntity<?> deleteRole(@Valid @RequestBody HashMap<String, String> dataHashMap) {
		return ResponseEntity.ok(adminServices.deleteRole(dataHashMap.get("roleid")));
	}

	// API specific for Administrator to delete a role to the application
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
	@PostMapping("/updaterole")
	public ResponseEntity<?> updateRole(@Valid @RequestBody HashMap<String, String> dataHashMap) {
		return ResponseEntity.ok(adminServices.updateRole(dataHashMap.get("roleid"), dataHashMap.get("rolename"),
				Boolean.valueOf(dataHashMap.get("rolestatus"))));
	}
}