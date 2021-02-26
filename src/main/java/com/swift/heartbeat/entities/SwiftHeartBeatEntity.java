package com.swift.heartbeat.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.swift.heartbeat.constants.TableConstants;

@Entity
@Table(name = TableConstants.TBL_SWIFT_HEART_BEAT)
public class SwiftHeartBeatEntity {
	@Id
	@Column(name = TableConstants.COL_SWIFT_CORRELATION_ID)
	private String correlationId;

	@Column(name = TableConstants.COL_SWIFT_REQ_TIME_STAMP)
	private Date reqTimestamp;

	@Column(name = TableConstants.COL_SWIFT_REP_TIME_STAMP)
	private Date repTimestamp;

	@Column(name = TableConstants.COL_SWIFT_ELAPSED_TIME)
	private Long elapsedTimeInMin;

	@Column(name = TableConstants.COL_SWIFT_ALARM_ACTIVE)
	private Boolean alarmActive;

	@Column(name = TableConstants.COL_SWIFT_ALARMIST_CHECK)
	private String alarmistCheck;

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public Date getReqTimestamp() {
		return reqTimestamp;
	}

	public void setReqTimestamp(Date reqTimestamp) {
		this.reqTimestamp = reqTimestamp;
	}

	public Date getRepTimestamp() {
		return repTimestamp;
	}

	public void setRepTimestamp(Date repTimestamp) {
		this.repTimestamp = repTimestamp;
	}

	public Long getElapsedTimeInMin() {
		return elapsedTimeInMin;
	}

	public void setElapsedTimeInMin(Long elapsedTimeInMin) {
		this.elapsedTimeInMin = elapsedTimeInMin;
	}

	public Boolean getAlarmActive() {
		return alarmActive;
	}

	public void setAlarmActive(Boolean alarmActive) {
		this.alarmActive = alarmActive;
	}

	public String getAlarmistCheck() {
		return alarmistCheck;
	}

	public void setAlarmistCheck(String alarmistCheck) {
		this.alarmistCheck = alarmistCheck;
	}
}
