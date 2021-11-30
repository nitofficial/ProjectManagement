package com.example.demo.constants;

public final class Constants {
	
	
	
	//FileManagement
	
	
	public static final String CLOUDINARY_URL = "https://api.cloudinary.com/v1_1/dc2zqvf2k/auto/upload";
    public static final String CLOUDINARY_UPLOAD_PRESET = "km3nmsxv";
    public static final String DEFECT_ID = "defect_id";
    public static final String ASSET_ID = "asset_id";
    public static final String FILE_SUB_DOCUMENT="filesubdocument";


    public static final String COUNTER_COLLECTION="counter";	
    

    //Project Creation
    
    public static final String PROJECT_COLLECION="project";
    public static final String PROJECT_COUNTER_DOCUMENT_ID="projectId";
    
    public static final String PROJECT_COUNTER_DOCUMENT_SEQUENCE_COLUMN = "seq";
    
   
    public static final String PROJECT_PREFIX="Prj-";
    public static final String REQUIREMENT_PREFIX="Req-";
    public static final String TESTCASE_PREFIX="Test-";
    public static final String DATE_FORMAT="dd-MM-yyyy";

    
    //TestCase
   
	public static final String TESTCASE_COLLECTION =  "testcase";
	
	public static final String TESTCASE_COUNTER_DOCUMENT_ID="testcaseId";

	public static final String TESTCASE_STATUS_ONHOLD = "Onhold";
	public static final String TESTCASE_STATUS_PASSED = "passed";
	public static final String TESTCASE_COUNTER_DOCUMENT_SEQUENCE_COLUMN = "seq";
	
	
	//Requirement
	 
	public static final String REQUIREMENT_COLLECTION =  "requirement";
	
	public static final String REQUIREMENT_VALID_STATUS =  "valid";
	
	
	//Defects
	public static final String DEFECT_COLLECTION = "defectInfo";
	public static final String COMMENT_COLLECTION = "defectComments";
	public static final String STATUS_COLLECTION = "StatusHistory";
}
