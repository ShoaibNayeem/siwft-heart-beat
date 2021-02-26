package com.swift.heartbeat.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	private ApplicationParametersRepository applicationParametersEntity;
	
	public Map<String, String> getApplicationParameters() {
		List<ApplicationParametersEntity> applicationParametersEntities = applicationParametersEntity.findAll();
		LOGGER.info("Number of records found in application parameters table --> " + applicationParametersEntities.size());
		return applicationParametersEntities.stream()
				.collect(Collectors.toMap(ApplicationParametersEntity::getKey, ApplicationParametersEntity::getValue));
	}

}
