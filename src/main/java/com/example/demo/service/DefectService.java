package com.example.demo.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.model.Comments;
import com.example.demo.model.Defect;
import com.example.demo.model.Status;
import com.example.demo.utilities.Dashboard;
import com.example.demo.utilities.Timestamp;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

@Service
public class DefectService {
	Logger logger = LoggerFactory.getLogger(DefectService.class);
	private MongoTemplate mongoTemplate;
	private ValidationService valSer;
	
	
	@Autowired
	public DefectService(MongoTemplate mongoTemplate) {
		logger.info("Constructor to class DefectService initialized");
		this.mongoTemplate = mongoTemplate;
	}
	
	public String addDefect(Defect defect) {
		logger.info("Validation starts");
		valSer.validateProjectId(defect.getProjectId());
		valSer.validateUserId(defect.getUserId());
		logger.info("Validation done!");
		defect.setStatus("New");
		Defect ins = mongoTemplate.insert(defect);
		addStatus(defect.getId(),"New");
		logger.info("AddDefect Successful");
		return "The defect "+ins.getDesc()+" is added into the database.";
	}
	
	public void addStatus(String id, String status) {
		logger.info("Validation starts");
		valSer.validateStatus(status);
		logger.info("Validation done!");
		Status s = new Status();
		s.setDefectId(id);
		s.setStatus(status);
		Timestamp t = new Timestamp();
		s.setDateTime(t);
		mongoTemplate.insert(s);
		logger.info("Status of defect "+id+" changed");
	}
	
	public List<Defect> getAllDefects(){
		Query q = new Query();
		q.addCriteria(Criteria.where("status").ne("Cancelled"));
		List<Defect> a = mongoTemplate.find(q,Defect.class);
		System.out.print(a);
		return a;
	}
	
	public long getDefectCount() {
		Query q = new Query();
		q.addCriteria(Criteria.where("status").ne("Closed"));
		return mongoTemplate.count(q, Defect.class);
		
	}
	public List<Defect> getOpenDefects(){
		Query q = new Query();
		q.addCriteria(Criteria.where("status").ne("Closed"));
		List<Defect> a = mongoTemplate.find(q,Defect.class);
		System.out.print(a);
		return a;
	}
	
	public List<Defect> getClosedDefects(){
		Query q = new Query();
		q.fields().include("id");
		q.addCriteria(Criteria.where("status").is("Closed"));
		List<Defect> a = mongoTemplate.find(q,Defect.class);
		System.out.print(a);
		return a;
	}
	
	
	public long getClosedDefectCount() {
		Query q = new Query();
		q.addCriteria(Criteria.where("status").is("Closed"));
		return mongoTemplate.count(q, Defect.class);
		
	}
	
	
	public List<Defect> getDefectsByProjectId(String pid){
		valSer.validateProjectId(pid);
		Query q = new Query();
		q.addCriteria(Criteria.where("status").ne("Cancelled"));
		q.addCriteria(Criteria.where("projectId").is(pid));
		List<Defect> a = mongoTemplate.find(q,Defect.class);
		System.out.print(a);
		return a;
	}
	
	public String addComment(Comments c) {
		valSer.validateDefId(c.getDefectId());
		Timestamp t = new Timestamp();
		c.setTimestamp(t);
		mongoTemplate.insert(c);
		return "Your comment has been added into the database.";
	}
	
	public String updateDefectByID(Map<String, String> data) {
		logger.info("Validation for update request starts");
		valSer.validateDefId(data.get("id"));
		logger.info("Validation for update request done!");
		Query select = Query.query(Criteria.where("id").is(data.get("id")));
		Update update = new Update();
		for(Map.Entry m:data.entrySet()){
			if(m.getKey().equals("status")) {
				updateDefectStatus(data);
				continue;
			}
			update.set((String) m.getKey(), m.getValue());  
	    }
		mongoTemplate.findAndModify(select, update, Defect.class);
		return "Update successful";
	}
	public String updateDefectStatus(Map<String, String> data) {
		valSer.validateDefId(data.get("id"));
		Query select = Query.query(Criteria.where("id").is(data.get("id")));
		Update update = new Update();
		update.set("status", data.get("status"));
		mongoTemplate.findAndModify(select, update, Defect.class);
		addStatus(data.get("id"),data.get("status"));
		return "The status of the defect "+data.get("id")+" has been updated to "+data.get("status");
	}
	
	public String deleteDefect(String id) {
		valSer.validateDefId(id);
		Query select = Query.query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("status", "Cancelled");
		mongoTemplate.findAndModify(select, update, Defect.class);
		addStatus(id,"Cancelled");
		return "The defect "+id+" is deleted successfully";
	}
	public Dashboard getDefectById(String id) {
		logger.info("Dashboard initation");
		valSer.validateDefId(id);
		Query q = new Query();
		q.addCriteria(Criteria.where("id").is(id));
		Defect a = mongoTemplate.find(q,Defect.class).get(0);
		q = new Query();
		q.addCriteria(Criteria.where("defectId").is(id));
		List <Status> b = mongoTemplate.find(q, Status.class);
		List <Comments> c = mongoTemplate.find(q, Comments.class);
		Dashboard d = new Dashboard();
		d.setDefect(a);
		d.setStatus(b);
		d.setComments(c);
		logger.info("Information retrieval for dashboard successful");
		return d;
	}
	
	
	

	
	
	
}
