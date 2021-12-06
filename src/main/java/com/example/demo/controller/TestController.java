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
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	/**
	 * API to display the Logged in user's details.
	 * @return ResponseEntity with the details of the currently logged in user
	 */
	@GetMapping("/userinfo")
	public ResponseEntity<?> displayUserDetail() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
		return ResponseEntity.ok(userServices.displayUserDetail(user.getId()));
	}
	
	/**
	 * API to update the details of the currently logged in user.
	 * @return ResponseEntity stating that the update operation is successful.
	 */
	@PutMapping("/updateuser")
	public ResponseEntity<?> updateEmployee(@RequestBody User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userN = (UserDetailsImpl) auth.getPrincipal();
		String username = userN.getUsername();
		return ResponseEntity.ok(userServices.updateUserDetails(username, user));
	}

	/**
	 * API for registered users to request roles from the Administrator.
	 * @return ResponseEntity stating that the request has been successfully initiated.
	 */
	@PreAuthorize("#pendingRequest.getUserid() == authentication.principal.id")
	@PostMapping("/requestrole")
	public ResponseEntity<?> requestRole(@Valid @RequestBody PendingRequest pendingRequest) {
		return ResponseEntity
				.ok(userServices.addRoletoUser(pendingRequest.getUserid(), pendingRequest.getRequestedroleid()));
	}

	/**
	 * API specific for Administrator to view all the users in the system.
	 * @return ResponseEntity with information about all the users.
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/alluserinfo")
	public ResponseEntity<?> displayAllUserDetail() {
		return ResponseEntity.ok(adminServices.displayAllUserDetail());
	}

	/**
	 * API specific for Administrator to delete an user from the system.
	 * @return ResponseEntity stating that the particular user has been successfully deleted.
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/deleteuser")
	public ResponseEntity<?> deleteUser(@Valid @RequestBody HashMap<String, String> dataHashMap) {
		return ResponseEntity.ok(adminServices.deleteUser(dataHashMap.get("userid")));
	}

	/**
	 * API specific for Administrator to grant requested roles to the appropriate user.
	 * @return ResponseEntity contains the particular user detail appended with the requested role.
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/addroletouser")
	public ResponseEntity<?> addRoleToUser(@Valid @RequestBody HashMap<String, String> dataHashMap) {
		return ResponseEntity.ok(adminServices.addRoleToUser(dataHashMap.get("requestid"), dataHashMap.get("userid"),
				dataHashMap.get("requestedroleid")));
	}

	/**
	 * API specific for Administrator to remove a role from a particular user.
	 * @return ResponseEntity stating that the role is removed successfully from the particular user.
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/deleterolefromuser")
	public ResponseEntity<?> deleteRoleFromUser(@Valid @RequestBody HashMap<String, String> dataHashMap) {
		return ResponseEntity
				.ok(adminServices.deleteRoleFromUser(dataHashMap.get("userid"), dataHashMap.get("roleid")));
	}

	/**
	 * API specific for Administrator to add new role to the application.
	 * @return ResponseEntity stating the the new role has been add to the Role collection.
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
	@PostMapping("/addnewrole")
	public ResponseEntity<?> addNewRole(@Valid @RequestBody HashMap<String, String> dataHashMap) {
		return ResponseEntity.ok(adminServices.addNewRole(dataHashMap.get("rolename")));
	}

	/**
	 * API specific for Administrator to view all the roles in the system.
	 * @return ResponseEntity with information about all the roles.
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/allroleinfo")
	public ResponseEntity<?> displayAllRoleDetail() {
		return ResponseEntity.ok(adminServices.displayAllRoleDetail());
	}

	/**
	 * API specific for Administrator to view all active roles in the system.
	 * @return ResponseEntity with information about all the active roles.
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/allactiveroleinfo")
	public ResponseEntity<?> displayAllActiveRoleDetail() {
		return ResponseEntity.ok(adminServices.displayAllActiveRoleDetail());
	}

	/**
	 * API specific for Administrator to delete a role to the application.
	 * @return ResponseEntity stating that the role is successfully set inactive.
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
	@PostMapping("/deleterole")
	public ResponseEntity<?> deleteRole(@Valid @RequestBody HashMap<String, String> dataHashMap) {
		return ResponseEntity.ok(adminServices.deleteRole(dataHashMap.get("roleid")));
	}

	/**
	 * API specific for Administrator to update a existing role in the application.
	 * @return ResponseEntity stating that the role has been updated successfully.
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
	@PostMapping("/updaterole")
	public ResponseEntity<?> updateRole(@Valid @RequestBody HashMap<String, String> dataHashMap) {
		return ResponseEntity.ok(adminServices.updateRole(dataHashMap.get("roleid"), dataHashMap.get("rolename"),
				Boolean.valueOf(dataHashMap.get("rolestatus"))));
	}
}