package com.swift.heartbeat.constants;

public enum Constants {

	SENDER_BIC("SENDER_BIC"),
	RECEIVER_BIC("RECEIVER_BIC"),
	ELAPSED_TIME("ELAPSED_TIME"),
	START_TIME("START_TIME"),
	END_TIME("END_TIME"),
	NEW("NEW"),
	COMPLETED("COMPLETED"),
	SMTP_HOST("SMTP_HOST"),
	SMTP_PORT("SMTP_PORT"),
	SMTP_AUTH("SMTP_AUTH"),
	SMTP_STARTTLS_ENABLE("SMTP_STARTTLS_ENABLE"),
	SMTP_SENDER("SMTP_SENDER"),
	SMTP_SENDER_PASSWORD("SMTP_SENDER_PASSWORD"),
	RECIPIENT("RECIPIENT"),
	SUCCESS("SUCCESS"),
	FAILURE("FAILURE"),
	EMAIL_SUBJECT("EMAIL_SUBJECT"),
	EMAIL_BODY("EMAIL_BODY"), 
	HOUSEKEEPING_TERM("HOUSEKEEPING_TERM");
	
	private String value;
	
	Constants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
