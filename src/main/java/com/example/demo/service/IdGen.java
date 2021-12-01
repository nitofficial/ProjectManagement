package com.example.demo.service;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Sequence")
public class IdGen {
	
	private static final String SEQUENCE_NAME = "user_sequence";

	@Id
	private String id;
	private int count;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
	
}
