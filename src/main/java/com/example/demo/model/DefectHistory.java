package com.example.demo.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dash_defect_history")
public class DefectHistory {

	private String time;
	private long defectCountStartOfDay;
	private long defectCountEndOfDay;
	private long defectClosedCount;
	private List<String> defectIdList;

	public DefectHistory() {
		super();
	}

	public DefectHistory(String time, long defectCountStartOfDay, long defectCountEndOfDay, long defectClosedCount,
			List<String> defectIdList) {
		super();
		this.time = time;
		this.defectCountStartOfDay = defectCountStartOfDay;
		this.defectCountEndOfDay = defectCountEndOfDay;
		this.defectClosedCount = defectClosedCount;
		this.defectIdList = defectIdList;
	}

	public long getDefectClosedCount() {
		return defectClosedCount;
	}

	public void setDefectClosedCount(long defectClosedCount) {
		this.defectClosedCount = defectClosedCount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getDefectCountStartOfDay() {
		return defectCountStartOfDay;
	}

	public void setDefectCountStartOfDay(long defectCountStartOfDay) {
		this.defectCountStartOfDay = defectCountStartOfDay;
	}

	public long getDefectCountEndOfDay() {
		return defectCountEndOfDay;
	}

	public void setDefectCountEndOfDay(long defectCountEndOfDay) {
		this.defectCountEndOfDay = defectCountEndOfDay;
	}

	public List<String> getDefectIdList() {
		return defectIdList;
	}

	public void setDefectIdList(List<String> defectIdList) {
		this.defectIdList = defectIdList;
	}

}
