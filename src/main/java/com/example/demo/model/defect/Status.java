package com.example.demo.model.defect;

import com.example.demo.constants.*;
import org.springframework.data.mongodb.core.mapping.Document;


import com.example.demo.utilities.Timestamp;

@Document(collection = Constants.STATUS_COLLECTION)
public class Status {
	private String defectId;
	private Timestamp dateTime;
	private String status;
	
	public Status() {
		super();
	}
	
	
	public Status(String defectId, Timestamp dateTime, String status) {
		super();
		this.defectId = defectId;
		this.dateTime = dateTime;
		this.status = status;
	}


	public String getDefectId() {
		return defectId;
	}
	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
