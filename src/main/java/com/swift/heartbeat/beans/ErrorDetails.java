package com.swift.heartbeat.beans;

import java.util.Date;
import java.util.List;

public class ErrorDetails {

	private Date date;
	private String errorMessage;
	private List<String> errorDescription;
	private String statusCode;

	public ErrorDetails(Date date, String errorMessage, List<String> errorDescription, String statusCode) {
		super();
		this.date = date;
		this.errorMessage = errorMessage;
		this.errorDescription = errorDescription;
		this.statusCode = statusCode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<String> getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(List<String> errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}
