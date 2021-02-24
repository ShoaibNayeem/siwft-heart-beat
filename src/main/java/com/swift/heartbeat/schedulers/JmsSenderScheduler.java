package com.swift.heartbeat.schedulers;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.swift.heartbeat.constants.Constants;
import com.swift.heartbeat.entities.ApplicationParametersEntity;
import com.swift.heartbeat.entities.SwiftHeartBeatEntity;
import com.swift.heartbeat.repositories.ApplicationParametersRepository;
import com.swift.heartbeat.repositories.SwiftHeartBeatRepository;

@Component
public class JmsSenderScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(JmsSenderScheduler.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private SwiftHeartBeatRepository swiftHeartBeatRepository;

	@Autowired
	private ApplicationParametersRepository applicationParametersEntity;

	Map<String, String> appParamsMap = new HashMap<>();

	@Scheduled(cron = "0 */10 * * * 1-5")
	public void sendMessage() {
		if (checkTime()) {
			LOGGER.info("Checking the current time is in between specified time");
			String uuid = generateUUID();
			LOGGER.info("Sending message to queue");
			String message = generateQueueMessage(uuid);
			jmsTemplate.convertAndSend("http://localhost:8161/", message);
			SwiftHeartBeatEntity swiftHeartBeatEntity = new SwiftHeartBeatEntity();
			swiftHeartBeatEntity.setCorrelationId(uuid);
			swiftHeartBeatEntity.setReqTimestamp(new Date());
			swiftHeartBeatRepository.save(swiftHeartBeatEntity);
		} else {
			LOGGER.info("Current time is not in between the specified time");
		}
	}

	private String generateUUID() {
		String uuid = UUID.randomUUID().toString();
		Optional<SwiftHeartBeatEntity> swiftHeartBeatEntity = swiftHeartBeatRepository.findById(uuid);
		if (swiftHeartBeatEntity.isPresent()) {
			LOGGER.info("Record found with the uuid " + swiftHeartBeatEntity.toString());
			LOGGER.info("Generating new uuid");
			generateUUID();
		}
		return uuid;
	}

	private boolean checkTime() {
		List<ApplicationParametersEntity> applicationParametersEntities = applicationParametersEntity.findAll();
		LOGGER.info("Number of records found --> " + applicationParametersEntities.size());
		appParamsMap = applicationParametersEntities.stream()
				.collect(Collectors.toMap(ApplicationParametersEntity::getKey, ApplicationParametersEntity::getValue));
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		LocalTime startTime = LocalTime.parse(appParamsMap.get(Constants.START_TIME));
		LocalTime endTime = LocalTime.parse(appParamsMap.get(Constants.END_TIME));
		LocalTime currentTime = LocalTime.parse(formatter.format(new Date().getTime()));
		return (currentTime.isAfter(startTime) && currentTime.isBefore(endTime));
	}

	private String generateQueueMessage(String uuid) {
		LOGGER.info("Preparing message");
		StringBuilder msg = new StringBuilder();
		msg.append("{1:F01").append(appParamsMap.get(Constants.SENDER_BIC)).append("0000000000").append("}");
		msg.append("{2:I198").append(appParamsMap.get(Constants.RECEIVER_BIC)).append("N}");
		msg.append("{4:20:SWIFTHEARTBEAT:12:123:77E:").append(uuid).append("-}");
		LOGGER.info("Prepared message " + msg.toString());
		return msg.toString();
	}
}
