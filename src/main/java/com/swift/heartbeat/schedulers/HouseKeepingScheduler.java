package com.swift.heartbeat.schedulers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.swift.heartbeat.constants.Constants;
import com.swift.heartbeat.repositories.SwiftHeartBeatRepository;
import com.swift.heartbeat.utils.SwiftHeartBeatUtils;

@Component
public class HouseKeepingScheduler {

	@Autowired
	private SwiftHeartBeatUtils swiftHeartBeatUtils;

	@Autowired
	private SwiftHeartBeatRepository swiftHeartBeatRepository;

	@Async("houseKeepingSchedulerJobPool")
	@Scheduled(cron = "0 */10 * * * 1-5")
	public void houseKeeping() {
		Map<String, String> appParamsMap = new HashMap<>();
		appParamsMap = swiftHeartBeatUtils.getAppParamsMap();
		System.out.println(appParamsMap.get(Constants.HOUSEKEEPING_TERM.getValue()));
		LocalDateTime currentData = LocalDateTime.now();
		LocalDateTime houseKeepingDate = currentData
				.plusDays(Long.parseLong(appParamsMap.get(Constants.HOUSEKEEPING_TERM.getValue())));
		swiftHeartBeatRepository.deleteOldRecords(houseKeepingDate);
	}
}