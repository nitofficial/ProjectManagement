package com.example.demo.model;

public class DashDefectModel {
	private String id;
	private String userId;
	private String desc;
	private String status;

	public DashDefectModel() {
		super();
	}

	public DashDefectModel(String id, String userId, String desc, String status) {
		super();
		this.id = id;
		this.userId = userId;
		this.desc = desc;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
