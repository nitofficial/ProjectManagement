package com.example.demo.services;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.constants.Constants;
import com.example.demo.service.DashboardService;

public class ConstantsTests {
	
	@Autowired
	private Constants constants;
	
	@Test
	public void ConstantsTests() {
		Constants con = new Constants();
		assertEquals(Constants.ASSET_ID,Constants.ASSET_ID);
		assertEquals(Constants.CLOUDINARY_UPLOAD_PRESET,Constants.CLOUDINARY_UPLOAD_PRESET);
		assertEquals(Constants.CLOUDINARY_UPLOAD_PRESET,Constants.CLOUDINARY_UPLOAD_PRESET);
		assertEquals(Constants.CLOUDINARY_URL,Constants.CLOUDINARY_URL);
		assertEquals(Constants.COMMENT_COLLECTION,Constants.COMMENT_COLLECTION);
		assertEquals(Constants.COUNTER_COLLECTION,Constants.COUNTER_COLLECTION);
		assertEquals(Constants.DATE_FORMAT,Constants.DATE_FORMAT);
		assertEquals(Constants.DEFECT_COLLECTION,Constants.DEFECT_COLLECTION);
		assertEquals(Constants.DEFECT_ID,Constants.DEFECT_ID);
		assertEquals(Constants.FILE_SUB_DOCUMENT,Constants.FILE_SUB_DOCUMENT);
		assertEquals(Constants.PROJECT_COLLECION,Constants.PROJECT_COLLECION);
		assertEquals(Constants.PROJECT_COUNTER_DOCUMENT_ID,Constants.PROJECT_COUNTER_DOCUMENT_ID);
		assertEquals(Constants.PROJECT_COUNTER_DOCUMENT_SEQUENCE_COLUMN,Constants.PROJECT_COUNTER_DOCUMENT_SEQUENCE_COLUMN);
		assertEquals(Constants.PROJECT_PREFIX,Constants.PROJECT_PREFIX);
		assertEquals(Constants.REQUIREMENT_COLLECTION,Constants.REQUIREMENT_COLLECTION);
		assertEquals(Constants.REQUIREMENT_INVALID_STATUS,Constants.REQUIREMENT_INVALID_STATUS);
		assertEquals(Constants.REQUIREMENT_PREFIX,Constants.REQUIREMENT_PREFIX);




		
	}
	

}
