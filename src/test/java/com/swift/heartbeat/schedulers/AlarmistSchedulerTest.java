package com.swift.heartbeat.schedulers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.swift.heartbeat.constants.Constants;
import com.swift.heartbeat.entities.SwiftHeartBeatEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmistSchedulerTest {
	
	@Autowired
	private AlarmistScheduler alarmistScheduler;
	
	@Test
	public void checkTimeTest() {
		Map<String, String> appParamsMap = new HashMap<>();
		appParamsMap.put(Constants.MON_START_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.MON_END_TIME.getValue(), "23:59");
		appParamsMap.put(Constants.TUES_START_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.TUES_END_TIME.getValue(), "23:59");
		appParamsMap.put(Constants.WED_START_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.WED_END_TIME.getValue(), "23:59");
		appParamsMap.put(Constants.THUR_START_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.THUR_END_TIME.getValue(), "23:59");
		appParamsMap.put(Constants.FRI_START_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.FRI_END_TIME.getValue(), "23:59");
		appParamsMap.put(Constants.SAT_START_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.SAT_END_TIME.getValue(), "23:59");
		appParamsMap.put(Constants.SUN_START_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.SUN_END_TIME.getValue(), "23:59");
		Assert.assertTrue(alarmistScheduler.checkTime(appParamsMap));
	}
	
	@Test
	public void getCurrentDayTest() {
		 String currentDay = alarmistScheduler.getCurrentDay();
		 List<String> weekDays = Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");
		 Assert.assertTrue(weekDays.contains(currentDay));
	}
	
	@Test
	public void findDifferenceInTimeTestGreaterThanElapsedTime() {
		SwiftHeartBeatEntity swiftHeartBeatEntity = new SwiftHeartBeatEntity();
		swiftHeartBeatEntity.setReqTimestamp(new Date());
		LocalDateTime date =  LocalDateTime.now().plus(Duration.of(10, ChronoUnit.MINUTES));
		Date repTimestamp = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
		swiftHeartBeatEntity.setRepTimestamp(repTimestamp);
		long elapsedTimeInMin = alarmistScheduler.findDifferenceInTime(swiftHeartBeatEntity);
		Assert.assertTrue(elapsedTimeInMin > 8);
	}
	
	@Test
	public void findDifferenceInTimeTestLessThanElapsedTime() {
		SwiftHeartBeatEntity swiftHeartBeatEntity = new SwiftHeartBeatEntity();
		swiftHeartBeatEntity.setReqTimestamp(new Date());
		LocalDateTime date =  LocalDateTime.now().plus(Duration.of(7, ChronoUnit.MINUTES));
		Date repTimestamp = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
		swiftHeartBeatEntity.setRepTimestamp(repTimestamp);
		long elapsedTimeInMin = alarmistScheduler.findDifferenceInTime(swiftHeartBeatEntity);
		Assert.assertTrue(elapsedTimeInMin < 8);
	}

}
