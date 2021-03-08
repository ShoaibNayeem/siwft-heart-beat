package com.swift.heartbeat.schedulers;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.swift.heartbeat.constants.Constants;
import com.swift.heartbeat.entities.SwiftHeartBeatEntity;
import com.swift.heartbeat.repositories.SwiftHeartBeatRepository;
import com.swift.heartbeat.utils.SwiftHeartBeatUtils;

@Component
public class JmsSenderScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(JmsSenderScheduler.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private SwiftHeartBeatRepository swiftHeartBeatRepository;

	@Autowired
	private SwiftHeartBeatUtils swiftHeartBeatUtils;

	@Async("jmsSenderSchedulerJobPool")
	@Scheduled(cron = "0 */10 * * * 1-5")
	public void sendMessage() {
		Map<String, String> appParamsMap = new HashMap<>();
		appParamsMap = swiftHeartBeatUtils.getApplicationParameters();
		if (appParamsMap != null && !appParamsMap.isEmpty() && checkTime(appParamsMap)) {
			LOGGER.info("Checking the current time is in between specified time");
			String uuid = generateUUID();
			LOGGER.info("Sending message to queue");
			String message = generateQueueMessage(appParamsMap, uuid);
			SwiftHeartBeatEntity swiftHeartBeatEntity = new SwiftHeartBeatEntity();
			swiftHeartBeatEntity.setCorrelationId(uuid);
			swiftHeartBeatEntity.setReqTimestamp(new Date());
			swiftHeartBeatEntity.setAlarmActive(false);
			swiftHeartBeatEntity.setAlarmistCheck(Constants.NEW);
			swiftHeartBeatRepository.save(swiftHeartBeatEntity);
			jmsTemplate.convertAndSend("http://localhost:8161/", message);
			LOGGER.info("Message sent to queue");
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

	private boolean checkTime(Map<String, String> appParamsMap) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		LocalTime startTime = LocalTime.parse(appParamsMap.get(Constants.START_TIME));
		LocalTime endTime = LocalTime.parse(appParamsMap.get(Constants.END_TIME));
		LocalTime currentTime = LocalTime.parse(formatter.format(new Date().getTime()));
		return (currentTime.isAfter(startTime) && currentTime.isBefore(endTime));
	}

	private String generateQueueMessage(Map<String, String> appParamsMap, String uuid) {
		LOGGER.info("Preparing message");
		StringBuilder msg = new StringBuilder();
		msg.append("{1:F01").append(appParamsMap.get(Constants.SENDER_BIC)).append("0000000000").append("}\n");
		msg.append("{2:I198").append(appParamsMap.get(Constants.RECEIVER_BIC)).append("N}\n");
		msg.append("{4:20:SWIFTHEARTBEAT" + "\n" + ":12:123" + "\n" + ":77E:").append(uuid + "\n").append("-}");
		LOGGER.info("Prepared message " + msg.toString());
		return msg.toString();
	}
}
