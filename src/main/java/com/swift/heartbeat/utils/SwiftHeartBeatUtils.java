package com.swift.heartbeat.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties()
public class SwiftHeartBeatUtils {

	private Map<String, String> shb = new HashMap<>();

	public Map<String, String> getShb() {
		return shb;
	}

	public void setShb(Map<String, String> shb) {
		this.shb = shb;
	}

}
