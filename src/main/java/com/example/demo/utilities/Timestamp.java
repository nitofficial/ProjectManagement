/**
* 	@author Vijay
*/
package com.example.demo.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;

import com.example.demo.service.ValidationService;

public class Timestamp {
	private int date;
	private int month;
	private int year;
	private int hour;
	private int min;
	@Transient
	Logger logger = LoggerFactory.getLogger(ValidationService.class);
	
	public Timestamp() {
		logger.info("Constructor to TimeStamp class initiated");
		LocalDateTime d = LocalDateTime.now();
		DateTimeFormatter f = DateTimeFormatter.ofPattern("dd");
		this.date = Integer.parseInt((d.format(f)));
		f = DateTimeFormatter.ofPattern("MM");
		this.month = Integer.parseInt((d.format(f)));
		f = DateTimeFormatter.ofPattern("yyyy");
		this.year = Integer.parseInt((d.format(f)));
		f = DateTimeFormatter.ofPattern("HH");
		this.hour = Integer.parseInt((d.format(f)));
		f = DateTimeFormatter.ofPattern("mm");
		this.min = Integer.parseInt((d.format(f)));
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}
	
	
}
