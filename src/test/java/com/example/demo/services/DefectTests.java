/**
* 	@author Vijay
*/
package com.example.demo.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Dashboard;
import com.example.demo.model.Defect;
import com.example.demo.service.DefectService;

@SpringBootTest
public class DefectTests {
	@Autowired
	DefectService defService;
	
	/**
	 * Method to test the create defect Service
	 *
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testCreateDefect() {
		Defect defect = new com.example.demo.model.Defect();
		defect.setDesc("Validation error");
		defect.setExpResults("Invalid User");
		defect.setProjectId("Prj-1");
		defect.setSeverity(2);
		defect.setUserId("U-1");
		assertTrue(defService.addDefect(defect) instanceof String);
	}
	
	@Test
	public void testUpdateDefect() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("defectId","Def-2");
		parameters.put("status", "Open");
		parameters.put("comment", "Status update");
		assertEquals("Update successful",defService.updateDefectByID(parameters));
	}
	
	/**
	 * Method to test the getAllDefects Service
	 *
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testGetAllDefects() {
		assertTrue(defService.getAllDefects(new HashMap<String,String>()).get(0) instanceof Defect);
	}
	
	/**
	 * Method to test the getDefectByID Service
	 *
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testGetDefectById() {
		assertTrue(defService.getDefectById("Def-1") instanceof Dashboard);
	}
	
	/**
	 * Method to test the DeleteDefect Service
	 *
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testDeleteDefect() {
		assertEquals("The defect Def-14 is deleted successfully", defService.deleteDefect("Def-14"));
	}
}