/**
	 * @author Nithish	
*/
package com.example.demo.model;

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
public class FileSubDocument {

	private String asset_id;
	private String url;
	private String secure_url;
	private String resource_type;
	private String original_filename;
	

	
	public FileSubDocument() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FileSubDocument(String asset_id, String url, String secure_url, String resource_type,
			String original_filename) {
		super();
		this.asset_id = asset_id;
		this.url = url;
		this.secure_url = secure_url;
		this.resource_type = resource_type;
		this.original_filename = original_filename;
	}
	public String getAsset_id() {
		return asset_id;
	}
	public void setAsset_id(String asset_id) {
		this.asset_id = asset_id;
	}

	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSecure_url() {
		return secure_url;
	}
	public void setSecure_url(String secure_url) {
		this.secure_url = secure_url;
	}
	public String getResource_type() {
		return resource_type;
	}
	public void setResource_type(String resource_type) {
		this.resource_type = resource_type;
	}
	public String getOriginal_filename() {
		return original_filename;
	}
	public void setOriginal_filename(String original_filename) {
		this.original_filename = original_filename;
	}
	
	

	
	
	
	
}
