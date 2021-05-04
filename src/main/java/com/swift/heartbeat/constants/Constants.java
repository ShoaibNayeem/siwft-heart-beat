package com.swift.heartbeat.constants;

public enum Constants {

	SENDER_BIC("sender.bic"),
	RECEIVER_BIC("receiver.bic"),
	ELAPSED_TIME("elapsed.time"),
	MON_START_TIME("mon.start.time"),
	MON_END_TIME("mon.end.time"),
	TUES_START_TIME("tues.start.time"),
	TUES_END_TIME("tues.end.time"),
	WED_START_TIME("wed.start.time"),
	WED_END_TIME("wed.end.time"),
	THUR_START_TIME("thur.start.time"),
	THUR_END_TIME("thur.end.time"),
	FRI_START_TIME("fri.start.time"),
	FRI_END_TIME("fri.end.time"),
	SAT_START_TIME("sat.start.time"),
	SAT_END_TIME("sat.end.time"),
	SUN_START_TIME("sun.start.time"),
	SUN_END_TIME("sun.end.time"),
	NEW("NEW"),
	COMPLETED("COMPLETED"),
	SMTP_HOST("smtp.host"),
	SMTP_PORT("smtp.port"),
	SMTP_AUTH("smtp.auth"),
	SMTP_STARTTLS_ENABLE("smtp.starttls.enable"),
	SMTP_SENDER("smtp.sender"),
	SMTP_SENDER_PASSWORD("smtp.sender.password"),
	RECIPIENT("recipient"),
	SUCCESS("SUCCESS"),
	FAILURE("FAILURE"),
	EMAIL_SUBJECT("email.subject"),
	EMAIL_BODY("email.body"), 
	HOUSEKEEPING_TERM_IN_DAYS("housekeeping.term.in.days"),
	NO_RUN("NO_RUN"),
	REQ_QUEUE_NAME("req.queue.name"),
	REP_QUEUE_NAME("rep.queue.name"),
	TRUE("TRUE"),
	FALSE("FALSE");
	
	private String value;
	
	Constants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
