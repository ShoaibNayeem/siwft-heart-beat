package com.swift.heartbeat.schedulers;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.swift.heartbeat.entities.SwiftHeartBeatEntity;
import com.swift.heartbeat.repositories.SwiftHeartBeatRepository;

@Component
public class JmsReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(JmsReceiver.class);

	@Autowired
	private SwiftHeartBeatRepository swiftHeartBeatRepository;

	@JmsListener(destination = "http://localhost:8161/queue")
	public void receiveMessage(String message) {
		LOGGER.info("Recieved message from the queue " + message);
		String correlationId = getCorrelationIdFromMessage(message);
		LOGGER.info("Extracted correlation Id from the message --> " + correlationId);
		SwiftHeartBeatEntity swiftHeartBeatEntity = swiftHeartBeatRepository.findByCorrelationId(correlationId);
		swiftHeartBeatEntity.setRepTimestamp(new Date());
		swiftHeartBeatEntity.setAlarmistCheck("NEW");
		swiftHeartBeatRepository.save(swiftHeartBeatEntity);
	}

	private String getCorrelationIdFromMessage(String message) {
		return StringUtils.substringBetween(message, ":77E:", "\n");
	}

}
