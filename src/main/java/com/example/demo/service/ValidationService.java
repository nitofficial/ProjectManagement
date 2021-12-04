/**
* 	@author Vijay
*/
package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.Defect;
import com.example.demo.model.ProjectModel;
import com.example.demo.model.User;

public class ValidationService {
	@Autowired
	private MongoTemplate mongoTemplate;
	Logger logger = LoggerFactory.getLogger(ValidationService.class);
	
	/**
	 * Method to validate the defectID
	 *
	 * 
	 * @param DefectID as a string
	 * @return  The defect object
	 */
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
	
	/**
	 * Method to validate the Status String
	 *
	 * 
	 * @param Status as a string
	 * @return  Boolean value
	 */
	public boolean validateStatus(String status) {
		if(!(status.equals("New") || status.equals("Open") || status.equals("Retest failed") || status.equals("Closed") || status.equals("Cancelled"))) {
			logger.warn("Validation failed");
			throw new BadRequestException("Invalid Status Type.");
		}
		return true;
	}
}
