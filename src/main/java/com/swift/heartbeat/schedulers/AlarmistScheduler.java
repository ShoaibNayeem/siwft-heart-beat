package com.swift.heartbeat.schedulers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.swift.heartbeat.constants.Constants;
import com.swift.heartbeat.entities.SwiftHeartBeatEntity;
import com.swift.heartbeat.repositories.SwiftHeartBeatRepository;
import com.swift.heartbeat.utils.SwiftHeartBeatUtils;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Component
public class AlarmistScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AlarmistScheduler.class);

	@Autowired
	private SwiftHeartBeatRepository swiftHeartBeatRepository;

	@Autowired
	private SwiftHeartBeatUtils swiftHeartBeatUtils;

	@Async("alarmistSchedulerJobPool")
	@Scheduled(cron = "${ALARMIST_CRON_SCHEDULER}")
	@SchedulerLock(name = "AlarmistScheduler_alarmistCheck", lockAtLeastFor = "PT2M", lockAtMostFor = "PT5M")
	public void alarmistCheck() {
		LOGGER.info("Alarmist scheduler called");
		Map<String, String> appParamsMap = new HashMap<>();
		appParamsMap = swiftHeartBeatUtils.getAppParamsMap();
		if (appParamsMap != null && !appParamsMap.isEmpty() && checkTime(appParamsMap)) {
			List<SwiftHeartBeatEntity> swiftHeartBeatEntities = swiftHeartBeatRepository
					.findAllByAlarmistCheck(Constants.NEW.getValue());
			LOGGER.info("Number of records with NEW alarmist check " + swiftHeartBeatEntities.size());
			if (swiftHeartBeatEntities != null && !swiftHeartBeatEntities.isEmpty()) {
				for (SwiftHeartBeatEntity swiftHeartBeatEntity : swiftHeartBeatEntities) {
					long elapsedTime = findDifferenceInTime(swiftHeartBeatEntity);
					swiftHeartBeatEntity.setElapsedTimeInMin(elapsedTime);
					if (elapsedTime >= Integer
							.parseInt(swiftHeartBeatUtils.getAppParamsMap().get(Constants.ELAPSED_TIME.getValue()))) {
						LOGGER.info("Elapsed time is above the limit " + elapsedTime);
						swiftHeartBeatEntity.setAlarmActive(Constants.TRUE.getValue());
					}
					swiftHeartBeatEntity.setAlarmistCheck(Constants.COMPLETED.getValue());
					swiftHeartBeatRepository.save(swiftHeartBeatEntity);
				}
			}
		} else {
			LOGGER.info("Current time is not in between the specified time");
		}
	}

	private boolean checkTime(Map<String, String> appParamsMap) {
		LOGGER.info("Checking the current time is in between specified time");
		String currentDay = checkCurrentDay();
		LocalTime startTime = null;
		LocalTime endTime = null;
		switch (currentDay) {
		case "MONDAY":
			if (!appParamsMap.get(Constants.ALARMIST_MON_START_TIME.getValue()).toUpperCase()
					.equals(Constants.NO_RUN.getValue())) {
				startTime = LocalTime.parse(appParamsMap.get(Constants.ALARMIST_MON_START_TIME.getValue()));
				endTime = LocalTime.parse(appParamsMap.get(Constants.ALARMIST_MON_END_TIME.getValue()));
			} else {
				return false;
			}
			break;
		case "TUESDAY":
			if (!appParamsMap.get(Constants.ALARMIST_TUES_START_TIME.getValue()).toUpperCase()
					.equals(Constants.NO_RUN.getValue())) {
				startTime = LocalTime.parse(appParamsMap.get(Constants.ALARMIST_TUES_START_TIME.getValue()));
				endTime = LocalTime.parse(appParamsMap.get(Constants.ALARMIST_TUES_END_TIME.getValue()));
			} else {
				return false;
			}
			break;
		case "WEDNESDAY":
			if (!appParamsMap.get(Constants.ALARMIST_WED_START_TIME.getValue()).toUpperCase()
					.equals(Constants.NO_RUN.getValue())) {
				startTime = LocalTime.parse(appParamsMap.get(Constants.ALARMIST_WED_START_TIME.getValue()));
				endTime = LocalTime.parse(appParamsMap.get(Constants.ALARMIST_WED_END_TIME.getValue()));
			} else {
				return false;
			}
			break;
		case "THURSDAY":
			if (!appParamsMap.get(Constants.ALARMIST_THUR_START_TIME.getValue()).toUpperCase()
					.equals(Constants.NO_RUN.getValue())) {
				startTime = LocalTime.parse(appParamsMap.get(Constants.ALARMIST_THUR_START_TIME.getValue()));
				endTime = LocalTime.parse(appParamsMap.get(Constants.ALARMIST_THUR_END_TIME.getValue()));
			} else {
				return false;
			}
			break;
		case "FRIDAY":
			if (!appParamsMap.get(Constants.ALARMIST_FRI_START_TIME.getValue()).toUpperCase()
					.equals(Constants.NO_RUN.getValue())) {
				startTime = LocalTime.parse(appParamsMap.get(Constants.ALARMIST_FRI_START_TIME.getValue()));
				endTime = LocalTime.parse(appParamsMap.get(Constants.ALARMIST_FRI_END_TIME.getValue()));
			} else {
				return false;
			}
			break;
		case "SATURDAY":
			if (!appParamsMap.get(Constants.ALARMIST_SAT_START_TIME.getValue()).toUpperCase()
					.equals(Constants.NO_RUN.getValue())) {
				startTime = LocalTime.parse(appParamsMap.get(Constants.ALARMIST_SAT_START_TIME.getValue()));
				endTime = LocalTime.parse(appParamsMap.get(Constants.ALARMIST_SAT_END_TIME.getValue()));
			} else {
				return false;
			}
			break;
		case "SUNDAY":
			if (!appParamsMap.get(Constants.ALARMIST_SUN_START_TIME.getValue()).toUpperCase()
					.equals(Constants.NO_RUN.getValue())) {
				startTime = LocalTime.parse(appParamsMap.get(Constants.ALARMIST_SUN_START_TIME.getValue()));
				endTime = LocalTime.parse(appParamsMap.get(Constants.ALARMIST_SUN_END_TIME.getValue()));
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

	private long findDifferenceInTime(SwiftHeartBeatEntity swiftHeartBeatEntity) {
		LOGGER.info("Finding the elapsed time");
		long diffInTime = (swiftHeartBeatEntity.getRepTimestamp().getTime()
				- swiftHeartBeatEntity.getReqTimestamp().getTime());
		return (diffInTime / (1000 * 60)) % 60;
	}

}
