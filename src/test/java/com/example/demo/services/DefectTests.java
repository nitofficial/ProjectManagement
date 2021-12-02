package com.example.demo.services;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Defect;
import com.example.demo.service.DefectService;
import com.example.demo.utilities.Dashboard;

@SpringBootTest
public class DefectTests {
	@Autowired
	DefectService defService;
	
	@Test
	public void testCreateDefect() {
		Defect defect = new com.example.demo.model.Defect();
		defect.setDesc("Validation error");
		defect.setExpResults("Invalid User");
		defect.setProjectId("Prj-1");
		defect.setSeverity(2);
		defect.setUserId("U-1");
		assertEquals("The defect Def-21 is added into the database.", defService.addDefect(defect));
	}
	
	@Test
	public void testUpdateDefect() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("defectId","Def-2");
		parameters.put("status", "Open");
		parameters.put("comment", "Status update");
		assertEquals("Update successful",defService.updateDefectByID(parameters));
	}
	
	@Test
	public void testGetAllDefects() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("status", "Open");
		assertTrue(defService.getAllDefects(parameters).get(0) instanceof Defect);
	}
	
	@Test
	public void testGetDefectById() {
		assertTrue(defService.getDefectById("Def-1") instanceof Dashboard);
	}
	
	@Test
	public void testDeleteDefect() {
		assertEquals("The defect Def-14 is deleted successfully", defService.deleteDefect("Def-14"));
	}
	
}
