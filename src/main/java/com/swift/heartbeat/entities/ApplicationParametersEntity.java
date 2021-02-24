package com.swift.heartbeat.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.swift.heartbeat.constants.TableConstants;

@Entity
@Table(name = TableConstants.TBL_APPLICATION_PARAMETERS)
public class ApplicationParametersEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = TableConstants.COL_APP_PARAM_ID)
	private int id;

	@Column(name = TableConstants.COL_APP_PARAM_KEY)
	private String key;

	@Column(name = TableConstants.COL_APP_PARAM_VAL)
	private String value;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
