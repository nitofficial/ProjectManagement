/**
	 * @author Sriram	
*/
package com.example.demo.model;

import java.util.Date;

public class UpdateHistory {
	Date updateDate;
	String description;

	public UpdateHistory(Date updateDate, String description) {
		this.updateDate = updateDate;
		this.description = description;
	}

}
