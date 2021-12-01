package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties
@Document(collection = "filecount")
public class FileCount {
	
	private String time;
	private long filesCount;
	
	
	public String getTime() {
		return time;
	}
	public FileCount(String time, long filesCount) {
		super();
		this.time = time;
		this.filesCount = filesCount;
	}
	public FileCount() {
		super();
	}
	public void setTime(String time) {
		this.time = time;
	}
	public long getFilesCount() {
		return filesCount;
	}
	public void setFilesCount(long filesCount) {
		this.filesCount = filesCount;
	}
	

}
