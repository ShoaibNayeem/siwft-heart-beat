package com.swift.heartbeat.schedulers;

import java.util.List;

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

@Component
public class AlarmistScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AlarmistScheduler.class);

	@Autowired
	private SwiftHeartBeatRepository swiftHeartBeatRepository;

	@Autowired
	private SwiftHeartBeatUtils swiftHeartBeatUtils;

	@Async("alarmistSchedulerJobPool")
	@Scheduled(cron = "0 */10 * * * 1-5")
	public void alarmistCheck() {
		LOGGER.info("Alarmist scheduler called");
		List<SwiftHeartBeatEntity> swiftHeartBeatEntities = swiftHeartBeatRepository
				.findAllByAlarmistCheck(Constants.NEW);
		LOGGER.info("Number of records with NEW alarmist check " + swiftHeartBeatEntities.size());
		if (swiftHeartBeatEntities != null && !swiftHeartBeatEntities.isEmpty()) {
			for (SwiftHeartBeatEntity swiftHeartBeatEntity : swiftHeartBeatEntities) {
				long elapsedTime = findDifferenceInTime(swiftHeartBeatEntity);
				swiftHeartBeatEntity.setElapsedTimeInMin(elapsedTime);
				if (elapsedTime >= Integer
						.parseInt(swiftHeartBeatUtils.getApplicationParameters().get(Constants.ELAPSED_TIME))) {
					LOGGER.info("Elapsed time is above the limit " + elapsedTime);
					swiftHeartBeatEntity.setAlarmActive(true);
				}
				swiftHeartBeatEntity.setAlarmistCheck(Constants.COMPLETED);
				swiftHeartBeatRepository.save(swiftHeartBeatEntity);
			}
		}
	}

	private long findDifferenceInTime(SwiftHeartBeatEntity swiftHeartBeatEntity) {
		LOGGER.info("Finding the elapsed time");
		long diffInTime = (swiftHeartBeatEntity.getRepTimestamp().getTime()
				- swiftHeartBeatEntity.getReqTimestamp().getTime());
		return (diffInTime / (1000 * 60)) % 60;
	}

}
