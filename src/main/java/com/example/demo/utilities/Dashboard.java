package com.example.demo.utilities;

import java.util.List;

import com.example.demo.model.Comments;
import com.example.demo.model.Defect;
import com.example.demo.model.Status;

public class Dashboard {
	private Defect defect;
	private List<Status> status;
	private List<Comments> comments;
	
	public List<Comments> getComments() {
		return comments;
	}
	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
	public Defect getDefect() {
		return defect;
	}
	public void setDefect(Defect defect) {
		this.defect = defect;
	}
	public List<Status> getStatus() {
		return status;
	}
	public void setStatus(List<Status> status) {
		this.status = status;
	}
	
	
	
}
