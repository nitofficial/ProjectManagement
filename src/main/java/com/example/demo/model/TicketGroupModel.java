package com.example.demo.model;

public class TicketGroupModel {
	
	int OpenCount;
	int closeCount;
	int FixedCount;
	int RetestCount;
	int FailedCount;
	public TicketGroupModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TicketGroupModel(int openCount, int closeCount, int fixedCount, int retestCount, int failedCount) {
		super();
		OpenCount = openCount;
		this.closeCount = closeCount;
		FixedCount = fixedCount;
		RetestCount = retestCount;
		FailedCount = failedCount;
	}
	public int getOpenCount() {
		return OpenCount;
	}
	public void setOpenCount(int openCount) {
		OpenCount = openCount;
	}
	public int getCloseCount() {
		return closeCount;
	}
	public void setCloseCount(int closeCount) {
		this.closeCount = closeCount;
	}
	public int getFixedCount() {
		return FixedCount;
	}
	public void setFixedCount(int fixedCount) {
		FixedCount = fixedCount;
	}
	public int getRetestCount() {
		return RetestCount;
	}
	public void setRetestCount(int retestCount) {
		RetestCount = retestCount;
	}
	public int getFailedCount() {
		return FailedCount;
	}
	public void setFailedCount(int failedCount) {
		FailedCount = failedCount;
	}
	
	

}
