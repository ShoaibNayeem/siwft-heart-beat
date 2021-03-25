package com.swift.heartbeat.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swift.heartbeat.entities.ApplicationParametersEntity;
import com.swift.heartbeat.repositories.ApplicationParametersRepository;

@Component
public class SwiftHeartBeatUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(SwiftHeartBeatUtils.class);

	@Autowired
	private ApplicationParametersRepository applicationParametersRepository;

	private Map<String, String> appParamsMap;

	@PostConstruct
	public void init() {
		appParamsMap = new HashMap<>();
		List<ApplicationParametersEntity> applicationParametersEntities = applicationParametersRepository.findAll();
		LOGGER.info(
				"Number of records found in application parameters table --> " + applicationParametersEntities.size());
		appParamsMap = applicationParametersEntities.stream()
				.collect(Collectors.toMap(ApplicationParametersEntity::getKey, ApplicationParametersEntity::getValue));
	}

	public Map<String, String> getAppParamsMap() {
		return appParamsMap;
	}

	public void setAppParamsMap(Map<String, String> appParamsMap) {
		this.appParamsMap = appParamsMap;
	}

}
