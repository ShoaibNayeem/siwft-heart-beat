package com.swift.heartbeat.schedulers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.swift.heartbeat.entities.SwiftHeartBeatEntity;
import com.swift.heartbeat.repositories.SwiftHeartBeatRepository;

@Component
public class JmsSenderScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(JmsSenderScheduler.class);
	
	private static final String SENDER_BIC = "SENDER_BIC";
	
	private static final String RECEIVER_BIC = "RECEIVER_BIC";

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private SwiftHeartBeatRepository swiftHeartBeatRepository;

	@Scheduled(cron = "0 */5 * * * 1-5")
	public void sendMessage() {
		LOGGER.info("Sending message to queue");
		String uuid = UUID.randomUUID().toString();
		String message = generateQueueMessage(uuid);
		jmsTemplate.convertAndSend("http://localhost:8161/", message);
		SwiftHeartBeatEntity swiftHeartBeatEntity = new SwiftHeartBeatEntity();
		swiftHeartBeatEntity.setCorrelationId(uuid);
		swiftHeartBeatRepository.save(swiftHeartBeatEntity);
	}

	private String generateQueueMessage(String uuid) {
		StringBuilder msg = new StringBuilder();
		msg.append("{1:F01").append(SENDER_BIC).append(0000000000).append("}");
		msg.append("{2:I198").append(RECEIVER_BIC).append("N}");
		msg.append("{4:20:SWIFTHEARTBEAT:12:123:77E:").append(uuid).append("}");
		return msg.toString();
	}
}
