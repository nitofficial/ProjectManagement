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

import com.example.demo.model.Comments;
import com.example.demo.model.Defect;
import com.example.demo.service.DefectService;
import com.example.demo.service.IdGen;
import com.example.demo.service.SequenceGenService;
import com.example.demo.utilities.Dashboard;

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
	
	@PostMapping("/create")
	public String createDefect(@Valid @RequestBody Defect defect) {
		logger.info(" Entered Create Defect Controller");
		return defService.addDefect(defect);
	}
	
	@GetMapping("/display")
	public List<Defect> getAllDefects(@RequestBody Map<String,String> filters){
		logger.info("Entered Display Defect Controller");
		return defService.getAllDefects(filters);
	}
	
	@GetMapping("/display/{id}")
	public Dashboard getDefectById(@PathVariable("id") String defectId){
		logger.info("Entered Get Defect By ID Controller");
		return defService.getDefectById(defectId);
	}
	
//	@PostMapping("/addComment")
//	public String addComment(@RequestBody Comments comment) {
//		logger.info("Entered Add Comment Controller");
//		return defService.addComment(comment);
//	}
	
	@PutMapping("/update")
	public String updateDefect(@RequestBody Map<String,String> defect) {
		logger.info("Entered Update Defect Controller");
		return defService.updateDefectByID(defect);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteDefect(@PathVariable("id") String defectId) {
		logger.info("Entered Delete Defect Controller");
		return defService.deleteDefect(defectId);
	}
}
