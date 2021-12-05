package com.example.demo.services;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.constants.Constants;
import com.example.demo.service.DashboardService;

public class ConstantsTests {
	
	@Autowired
	private Constants constants;
	
	@Test
	public void ConstantsTests() {
		Constants con = new Constants();
	}
	

}
