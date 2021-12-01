package com.example.demo.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;

@JsonIgnoreProperties
//@Data
//@AllArgsConstructor
//@Getter
//@Setter
@Document(collection = "files")
public class FileModel {
//	@Id
	private String defect_id;
	private List<FileSubDocument> filesubdocument;

	
	public String getDefect_id() {
		return defect_id;
	}

	public void setDefect_id(String defect_id) {
		this.defect_id = defect_id;
	}

	public List<FileSubDocument> getFilesubdocument() {
		return filesubdocument;
	}

	public FileModel(String defect_id, List<FileSubDocument> filesubdocument) {
//		super();
		this.defect_id = defect_id;
		this.filesubdocument = filesubdocument;
	}

	public FileModel() {
		super();
	}

	public void setFilesubdocument(List<FileSubDocument> filesubdocument) {
		this.filesubdocument = filesubdocument;
	}

	

	
	
	
	
}
