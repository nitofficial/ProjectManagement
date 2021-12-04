package com.example.demo.model;

import java.util.List;

public class DashRTMModel {

	private String id;
	private String name;
	private List<DashReqModel> requirements;
//	private List<DashDefectModel> defects;

	public DashRTMModel() {
		super();
	}

	public DashRTMModel(String id, String name, List<DashReqModel> requirements) {
		super();
		this.id = id;
		this.name = name;
		this.requirements = requirements;
//		this.defects = defects;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DashReqModel> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<DashReqModel> requirements) {
		this.requirements = requirements;
	}

//	public List<DashDefectModel> getDefects() {
//		return defects;
//	}
//
//	public void setDefects(List<DashDefectModel> defects) {
//		this.defects = defects;
//	}

}
