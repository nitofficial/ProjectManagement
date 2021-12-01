package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo.constants.Constants;
import com.example.demo.utilities.Timestamp;


@Document(collection = Constants.COMMENT_COLLECTION)
public class Comments {
	private Timestamp timestamp;
	private String userId;
	private String comment;
	private String defectId;
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDefectId() {
		return defectId;
	}
	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}
	
	
}
