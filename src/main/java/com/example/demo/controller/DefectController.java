package com.example.demo.controller;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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


@RequestMapping("/api/defects")
@RestController
public class DefectController {
	
	@Autowired
	private SequenceGenService service;
	private DefectService defService;
	
	public DefectController(DefectService defService) {
		this.defService = defService;
	}
	
	@PostMapping("/create")
	public String createDefect(@RequestBody Defect defect) {
		defect.setId("D"+service.getCount(IdGen.getSequenceName()));
		return defService.addDefect(defect);
	}
	
	@GetMapping("/display")
	public List<Defect> getAllDefects(){
		return defService.getAllDefects();
	}
	
	@GetMapping("displayprojectdef/{pid}")
	public List<Defect> getProjectDefects(@PathVariable("pid") String pid){
		return defService.getDefectsByProjectId(pid);
	}
	
	@GetMapping("/display/{id}")
	public Dashboard getDefectById(@PathVariable("id") String id){
		return defService.getDefectById(id);
	}
	
	@PostMapping("/addComment")
	public String addComment(@RequestBody Comments c) {
		return defService.addComment(c);
	}
	
	@PutMapping("/update")
	public String updateDefect(@RequestBody Map<String,String> defect) {
		return defService.updateDefectByID(defect);
	}
	
	@PutMapping("/updateStatus")
	public String updateDefectStatus(@PathVariable("id") String id, @RequestBody Map<String,String> defect) {
		return defService.updateDefectStatus(defect);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteDefect(@PathVariable("id") String id) {
		return defService.deleteDefect(id);
	}
}
