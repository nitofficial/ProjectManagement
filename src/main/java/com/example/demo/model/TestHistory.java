package com.example.demo.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dash_test_history")
public class TestHistory {

	private String time;
	private long testCountStartOfDay;
	private long testCountEndOfDay;
	private long testPassCount;
	private List<IdOnly> testListDayStart;
	private List<IdOnly> testListDayEnd;


	public TestHistory(String time, long testCountStartOfDay, long testCountEndOfDay, long testPassCount,
			List<IdOnly> testListDayStart, List<IdOnly> testListDayEnd) {
		super();
		this.time = time;
		this.testCountStartOfDay = testCountStartOfDay;
		this.testCountEndOfDay = testCountEndOfDay;
		this.testPassCount = testPassCount;
		this.testListDayStart = testListDayStart;
		this.testListDayEnd = testListDayEnd;
	}



	public List<IdOnly> getTestListDayStart() {
		return testListDayStart;
	}



	public void setTestListDayStart(List<IdOnly> testListDayStart) {
		this.testListDayStart = testListDayStart;
	}



	public List<IdOnly> getTestListDayEnd() {
		return testListDayEnd;
	}



	public void setTestListDayEnd(List<IdOnly> testListDayEnd) {
		this.testListDayEnd = testListDayEnd;
	}



	public TestHistory() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getTestCountStartOfDay() {
		return testCountStartOfDay;
	}

	public void setTestCountStartOfDay(long testCountStartOfDay) {
		this.testCountStartOfDay = testCountStartOfDay;
	}

	public long getTestCountEndOfDay() {
		return testCountEndOfDay;
	}

	public void setTestCountEndOfDay(long testCountEndOfDay) {
		this.testCountEndOfDay = testCountEndOfDay;
	}

	public long getTestPassCount() {
		return testPassCount;
	}

	public void setTestPassCount(long testPassCount) {
		this.testPassCount = testPassCount;
	}

}
