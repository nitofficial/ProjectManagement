package com.example.demo.service.defect;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.defect.Defect;
import com.example.demo.model.projectcreation.ProjectModel;
import com.example.demo.model.rolemanagement.User;

public class ValidationService {
	private MongoTemplate mongoTemplate;
	Logger logger = LoggerFactory.getLogger(ValidationService.class);
	
	public Defect validateDefId(String defId) {
		Query q = new Query();
		q.addCriteria(Criteria.where("id").is(defId));
		Defect defect = mongoTemplate.findOne(q,Defect.class);
		if(defect==null) {
			logger.warn("Validation failed");
			throw new BadRequestException("The entered Defect ID is invalid.");
		}
		return defect;
	}
	public boolean validateProjectId(String projectId) {
		Query q = new Query();
		q.addCriteria(Criteria.where("id").is(projectId));
		List<ProjectModel> a = mongoTemplate.find(q,ProjectModel.class);
		if(a.size()==0) {
			logger.warn("Validation failed");
			throw new BadRequestException("The entered Project ID is invalid.");
		}
		return true;
	}
	public boolean validateStatus(String status) {
		if(!(status.equals("New") || status.equals("Open") || status.equals("Retest failed") || status.equals("Closed") || status.equals("Cancelled"))) {
			logger.warn("Validation failed");
			throw new BadRequestException("Invalid Status Type.");
		}
		return true;
	}
	public boolean validateUserId(String userId) {
		Query q = new Query();
		q.addCriteria(Criteria.where("id").is(userId));
		List<User> a = mongoTemplate.find(q,User.class);
		if(a.size()==0) {
			logger.warn("Validation failed");
			throw new BadRequestException("The entered User ID is invalid.");
		}
		return true;
	}
}
