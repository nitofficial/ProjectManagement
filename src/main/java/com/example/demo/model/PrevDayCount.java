package com.example.demo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dash_defect_prev_day")
public class PrevDayCount {
	@Id
	private String historyType;
	private long prevCount;
	private List<IdOnly> historyTypeLists;

	public PrevDayCount() {
		super();
	}



	public PrevDayCount(String historyType, long prevCount, List<IdOnly> historyTypeLists) {
		super();
		this.historyType = historyType;
		this.prevCount = prevCount;
		this.historyTypeLists = historyTypeLists;
	}



	public List<IdOnly> getHistoryTypeLists() {
		return historyTypeLists;
	}



	public void setHistoryTypeLists(List<IdOnly> historyTypeLists) {
		this.historyTypeLists = historyTypeLists;
	}



	public String getHistoryType() {
		return historyType;
	}

	public void setHistoryType(String historyType) {
		this.historyType = historyType;
	}

	public long getPrevCount() {
		return prevCount;
	}

	public void setPrevCount(long prevCount) {
		this.prevCount = prevCount;
	}

}
