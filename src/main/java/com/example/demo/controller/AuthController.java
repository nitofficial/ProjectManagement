/**
	 * @author Sanjay	
*/
package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.request.*;
import com.example.demo.service.UserServices;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	UserServices userServices;

	// API for new users to register in the application
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return ResponseEntity.ok(userServices.registerUser(signUpRequest.getUsername(), signUpRequest.getEmail(),
				signUpRequest.getPassword()));
	}

	// API for users to sign in with the user-name and password
	@PostMapping("/signin")
	public ResponseEntity<?> userSignIn(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(userServices.userSignIn(loginRequest.getUsername(), loginRequest.getPassword()));
	}
}