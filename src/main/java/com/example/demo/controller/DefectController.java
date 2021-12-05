/**
* 	@author Vijay
*/
package com.example.demo.controller;

import java.util.List;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.Comments;
import com.example.demo.model.Dashboard;
import com.example.demo.model.Defect;
import com.example.demo.service.DefectService;
import com.example.demo.service.IdGen;
import com.example.demo.service.SequenceGenService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RequestMapping("/api/defects")
@RestController
public class DefectController {
	@Transient
	Logger logger = LoggerFactory.getLogger(DefectController.class);

	private DefectService defService;
	
	public DefectController(DefectService defService) {
		logger.info("Entered Defect Controller Class");
		this.defService = defService;
	}
	
	/**
	 * Method to create a new defect
	 *
	 * 
	 * @param Defect object
	 * @return  The ID of the newly created Defect.
	 */
	@PostMapping("/create")
	public String createDefect(@Valid @RequestBody Defect defect) {
		logger.info(" Entered Create Defect Controller");
		return defService.addDefect(defect);
	}
	
	/**
	 * Method to retrieve all the defects
	 *
	 * 
	 * @param Filters for search if any, as a hashmap
	 * @return  A list of all the defects in the collection
	 */
	@GetMapping("/display")
	public List<Defect> getAllDefects(@RequestBody Map<String,String> filters){
		logger.info("Entered Display Defect Controller");
		return defService.getAllDefects(filters);
	}
	
	/**
	 * Method to return all the details associated with a particular defect
	 *
	 * 
	 * @param defect ID as a string
	 * @return  A dashboard object with all the details of the string
	 */
	@GetMapping("/display/{id}")
	public Dashboard getDefectById(@PathVariable("id") String defectId){
		logger.info("Entered Get Defect By ID Controller");
		return defService.getDefectById(defectId);
	}
	
	/**
	 * Method to update defect parameters using their ID
	 *
	 * 
	 * @param Defect ID and the parameters to be updated as a hashmap
	 * @return A string of acknowledgement
	 */
	@PutMapping("/update")
	public String updateDefect(@RequestBody Map<String,String> defect) {
		logger.info("Entered Update Defect Controller");
		return defService.updateDefectByID(defect);
	}
	
	/**
	 * Method to delete a particular defect
	 * 
	 * @param The ID of the defect as a string
	 * @return  A string of acknowledgement
	 * @throws BadRequestException handles Exception.
	 */
	@DeleteMapping("/delete/{id}")
	public String deleteDefect(@PathVariable("id") String defectId) {
		logger.info("Entered Delete Defect Controller");
		return defService.deleteDefect(defectId);
	}
}
