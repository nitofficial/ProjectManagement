/**
* 	@author Vijay
*/
package com.example.demo.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.Comments;
import com.example.demo.model.Defect;
import com.example.demo.model.Status;

import com.example.demo.utilities.Dashboard;
import com.example.demo.utilities.Timestamp;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DefectService {
	@Autowired
	private SequenceGenService service;
	@Transient
	private static Logger logger = LoggerFactory.getLogger(DefectService.class);
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	public DefectService(MongoTemplate mongoTemplate) {
		logger.info("Constructor to class DefectService initialized");
	}
	
	/**
	 * Method to create a new defect
	 *
	 * 
	 * @param Defect object
	 * @return  The ID of the newly created Defect.
	 */
	public String addDefect(Defect defect) {
		logger.info("Inside Add Defect");
		defect.setStatus("New");
		defect.setId("Def-"+service.getCount(IdGen.getSequenceName()));
		mongoTemplate.insert(defect);
		Status status = new Status();
		status.setDefectId(defect.getId());
		status.setStatus("New");
		Timestamp timestamp = new Timestamp();
		status.setDateTime(timestamp);
		Status s = mongoTemplate.insert(status);
		this.addStatus(defect.getId(),"New");
		logger.info("AddDefect Successful");
		return defect.getId();
	}
	
	/**
	 * Method to create a new entry in the StatusHistory collection
	 *
	 * 
	 * @param defectId(String), status(String)
	 * @return  Respective status and information of ProjectModel.
	 */
	public void addStatus(String defectId, String status_string) {
		logger.info("Inside Add Status service");
		Status status = new Status();
		status.setDefectId(defectId);
		status.setStatus(status_string);
		Timestamp t = new Timestamp();
		status.setDateTime(t);
		mongoTemplate.insert(status);
		logger.info("Status insertion successful");
	}
	
	/**
	 * Method to retrieve all the defects
	 *
	 * 
	 * @param Filters for search if any, as a hashmap
	 * @return  A list of all the defects in the collection
	 */
	public List<Defect> getAllDefects(Map <String,String> filters){
		logger.info("Inside GetAllDefects Service");
		Query query = new Query();
		List<Criteria> criteria = new ArrayList<>();
		if(!filters.containsKey("status")) {
			criteria.add(Criteria.where("status").ne("Cancelled"));
		}
		for(Map.Entry filter:filters.entrySet()){
			if(filter.getKey().equals("severity")) {
				criteria.add(Criteria.where((String) filter.getKey()).is(Integer.parseInt((String) filter.getValue())));
				continue;
			}
			if(filter.getValue()!=null)
				criteria.add(Criteria.where((String) filter.getKey()).is(filter.getValue()));
			
	    }
		query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
		List<Defect> resultSet = mongoTemplate.find(query,Defect.class);
		logger.info("Defects Retrival Successful");
		return resultSet;
	}
	
	/**
	 * Method to return the number of active defects
	 *
	 * 
	 * @param 
	 * @return  The number of unclosed defects in the collection
	 */
	public long getDefectCount() {
		Query q = new Query();
		q.addCriteria(Criteria.where("status").ne("Closed"));
		return mongoTemplate.count(q, Defect.class);
		
	}
	

	/**
	 * Method to return the objects of active defects
	 *
	 * 
	 * @param
	 * @return  A list of all the unclosed defects in the collection
	 */

	public List<Defect> getOpenDefects(){
		Query q = new Query();
		q.addCriteria(Criteria.where("status").ne("Closed"));
		List<Defect> a = mongoTemplate.find(q,Defect.class);
		System.out.print(a);
		return a;
	}
	
	/**
	 * Method to return the objects of closed defects
	 *
	 * 
	 * @param
	 * @return  A list of all the closed defects in the collection
	 */
	public List<Defect> getClosedDefects(){
		Query q = new Query();
		q.fields().include("id");
		q.addCriteria(Criteria.where("status").is("Closed"));
		List<Defect> a = mongoTemplate.find(q,Defect.class);
		System.out.print(a);
		return a;
	}
	
	/**
	 * Method to return the count of closed defects in the collection
	 *
	 * 
	 * @param
	 * @return  A list of all the closed defects in the collection
	 */
	public long getClosedDefectCount() {
		Query q = new Query();
		q.addCriteria(Criteria.where("status").is("Closed"));
		return mongoTemplate.count(q, Defect.class);
		
	}
	
	/**
	 * Method to return the objects of the defects associated with a project ID
	 *
	 * 
	 * @param Project ID as a string
	 * @return  A list of all the defects of a particular project
	 */
	public List<Defect> getDefectsByProjectId(String pid){
		Query q = new Query();
		q.addCriteria(Criteria.where("status").ne("Cancelled"));
		q.addCriteria(Criteria.where("projectId").is(pid));
		List<Defect> a = mongoTemplate.find(q,Defect.class);
		System.out.print(a);
		return a;
	}
	
	/**
	 * Method to update defect parameters using their ID
	 *
	 * 
	 * @param Defect ID and the parameters to be updated as a hashmap
	 * @return A string of acknowledgement
	 * @throws BadRequestException handles Exception.
	 */
	public String updateDefectByID(Map<String, String> update_request) {
		logger.info("Inside update Defect");
		Query select = Query.query(Criteria.where("id").is(update_request.get("defectId")));
		Update update = new Update();
		if(update_request.containsKey("comment")) {
			for(Map.Entry parameter:update_request.entrySet()){
				if(parameter.getKey().equals("status")) {
					validateStatus((String)parameter.getValue());
					addStatus(update_request.get("defectId"),(String) parameter.getValue());
				}
				else if(parameter.getKey().equals("comment")) {
					Comments comment = new Comments();
					comment.setDefectId(update_request.get("defectId"));
					comment.setComment(update_request.get("comment"));
					Timestamp timestamp = new Timestamp();
					comment.setTimestamp(timestamp);
					mongoTemplate.insert(comment);
					continue;
				}
				update.set((String) parameter.getKey(), parameter.getValue());  
			}
			mongoTemplate.findAndModify(select, update, Defect.class);
			return "Update successful";
		}
		else {
			return "Comment field is necessary, updation  unsuccessful";
		}
	}
	
	/**
	 * Method to delete a particular defect
	 * 
	 * @param The ID of the defect as a string
	 * @return  A string of acknowledgement
	 * @throws BadRequestException handles Exception.
	 */
	public String deleteDefect(String id) {
		logger.info("Inside Delete Defect");
		Query select = Query.query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("status", "Cancelled");
		mongoTemplate.findAndModify(select, update, Defect.class);
		addStatus(id,"Cancelled");
		return "The defect "+id+" is deleted successfully";
	}
	
	/**
	 * Method to return all the details associated with a particular defect
	 *
	 * 
	 * @param defect ID as a string
	 * @return  A dashboard object with all the details of the string
	 */
	public Dashboard getDefectById(String id) {
		logger.info("Dashboard initation");
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Defect defect = mongoTemplate.findOne(query,Defect.class);
		if(defect==null) {
			throw new BadRequestException("The entered Defect ID is invalid.");
		}
		query = new Query();
		query.addCriteria(Criteria.where("defectId").is(id));
		List <Status> status = mongoTemplate.find(query, Status.class);
		List <Comments> comments = mongoTemplate.find(query, Comments.class);
		Dashboard dashboard = new Dashboard();
		dashboard.setDefect(defect);
		dashboard.setStatus(status);
		dashboard.setComments(comments);
		logger.info("Information retrieval for dashboard successful");
		return dashboard;
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
