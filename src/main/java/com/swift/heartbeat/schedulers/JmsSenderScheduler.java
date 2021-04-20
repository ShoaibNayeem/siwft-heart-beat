package com.swift.heartbeat.schedulers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

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
	@Scheduled(cron = "0 */10 * * * *")
	@SchedulerLock(name = "JmsSenderScheduler_sendMessageToQueue", lockAtLeastFor = "PT2M", lockAtMostFor = "PT5M")
	public void sendMessageToQueue() {
		Map<String, String> appParamsMap = new HashMap<>();
		appParamsMap = swiftHeartBeatUtils.getAppParamsMap();
		if (appParamsMap != null && !appParamsMap.isEmpty() && checkTime(appParamsMap)) {
			LOGGER.info("Checking the current time is in between specified time");
			String uuid = generateUUID();
			LOGGER.info("Sending message to queue");
			String message = generateQueueMessage(appParamsMap, uuid);
			SwiftHeartBeatEntity swiftHeartBeatEntity = new SwiftHeartBeatEntity();
			swiftHeartBeatEntity.setCorrelationId(uuid);
			swiftHeartBeatEntity.setReqTimestamp(new Date());
			swiftHeartBeatEntity.setAlarmActive(false);
			swiftHeartBeatEntity.setAlarmistCheck(Constants.NEW.getValue());
			swiftHeartBeatRepository.save(swiftHeartBeatEntity);
			jmsTemplate.convertAndSend("http://localhost:8161/queue", message);
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
		String currentDay = checkCurrentDay();
		LocalTime startTime = null;
		LocalTime endTime = null;
		switch (currentDay) {
		case "MONDAY":
			if (!appParamsMap.get(Constants.MON_START_TIME.getValue()).toUpperCase()
					.equals(Constants.NO_RUN.getValue())) {
				startTime = LocalTime.parse(appParamsMap.get(Constants.MON_START_TIME.getValue()));
				endTime = LocalTime.parse(appParamsMap.get(Constants.MON_END_TIME.getValue()));
			} else {
				return false;
			}
			break;
		case "TUESDAY":
			if (!appParamsMap.get(Constants.TUES_START_TIME.getValue()).toUpperCase()
					.equals(Constants.NO_RUN.getValue())) {
				startTime = LocalTime.parse(appParamsMap.get(Constants.TUES_START_TIME.getValue()));
				endTime = LocalTime.parse(appParamsMap.get(Constants.TUES_END_TIME.getValue()));
			} else {
				return false;
			}
			break;
		case "WEDNESDAY":
			if (!appParamsMap.get(Constants.WED_START_TIME.getValue()).toUpperCase()
					.equals(Constants.NO_RUN.getValue())) {
				startTime = LocalTime.parse(appParamsMap.get(Constants.WED_START_TIME.getValue()));
				endTime = LocalTime.parse(appParamsMap.get(Constants.WED_END_TIME.getValue()));
			} else {
				return false;
			}
			break;
		case "THURSDAY":
			if (!appParamsMap.get(Constants.THUR_START_TIME.getValue()).toUpperCase()
					.equals(Constants.NO_RUN.getValue())) {
				startTime = LocalTime.parse(appParamsMap.get(Constants.THUR_START_TIME.getValue()));
				endTime = LocalTime.parse(appParamsMap.get(Constants.THUR_END_TIME.getValue()));
			} else {
				return false;
			}
			break;
		case "FRIDAY":
			if (!appParamsMap.get(Constants.FRI_START_TIME.getValue()).toUpperCase()
					.equals(Constants.NO_RUN.getValue())) {
				startTime = LocalTime.parse(appParamsMap.get(Constants.FRI_START_TIME.getValue()));
				endTime = LocalTime.parse(appParamsMap.get(Constants.FRI_END_TIME.getValue()));
			} else {
				return false;
			}
			break;
		case "SATURDAY":
			if (!appParamsMap.get(Constants.SAT_START_TIME.getValue()).toUpperCase()
					.equals(Constants.NO_RUN.getValue())) {
				startTime = LocalTime.parse(appParamsMap.get(Constants.SAT_START_TIME.getValue()));
				endTime = LocalTime.parse(appParamsMap.get(Constants.SAT_END_TIME.getValue()));
			} else {
				return false;
			}
			break;
		case "SUNDAY":
			if (!appParamsMap.get(Constants.SUN_START_TIME.getValue()).toUpperCase()
					.equals(Constants.NO_RUN.getValue())) {
				startTime = LocalTime.parse(appParamsMap.get(Constants.SUN_START_TIME.getValue()));
				endTime = LocalTime.parse(appParamsMap.get(Constants.SUN_END_TIME.getValue()));
			} else {
				return false;
			}
			break;
		default:
			LOGGER.error("NOT KNOWN");
		}
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		LocalTime currentTime = LocalTime.parse(formatter.format(new Date().getTime()));
		return (currentTime.isAfter(startTime) && currentTime.isBefore(endTime));
	}

	private String checkCurrentDay() {
		LocalDate date = LocalDate.now();
		return date.getDayOfWeek().toString();
	}

	private String generateQueueMessage(Map<String, String> appParamsMap, String uuid) {
		LOGGER.info("Preparing message");
		StringBuilder msg = new StringBuilder();
		msg.append("{1:F01").append(appParamsMap.get(Constants.SENDER_BIC.getValue())).append("0000000000").append("}\n");
		msg.append("{2:I198").append(appParamsMap.get(Constants.RECEIVER_BIC.getValue())).append("N}\n");
		msg.append("{4:20:SWIFTHEARTBEAT" + "\n" + ":12:123" + "\n" + ":77E:").append(uuid + "\n").append("-}");
		LOGGER.info("Prepared message " + msg.toString());
		return msg.toString();
	}
}
