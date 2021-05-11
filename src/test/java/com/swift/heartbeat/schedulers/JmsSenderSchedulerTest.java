package com.swift.heartbeat.schedulers;

import java.util.Arrays;
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
import com.swift.heartbeat.utils.SwiftHeartBeatUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JmsSenderSchedulerTest {

	@Autowired
	private SwiftHeartBeatUtils swiftHeartBeatUtils;

	@Autowired
	private JmsSenderScheduler jmsSenderScheduler;

	@Test
	public void getCurrentDayTest() {
		String currentDay = jmsSenderScheduler.getCurrentDay();
		List<String> weekDays = Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY",
				"SUNDAY");
		Assert.assertTrue(weekDays.contains(currentDay));
	}

	@Test
	public void generateUUIDTest() {
		String uuid = jmsSenderScheduler.generateUUID();
		Assert.assertTrue(uuid != null);
	}

	@Test
	public void generateQueueMessageTest() {
		String message = jmsSenderScheduler.generateQueueMessage(swiftHeartBeatUtils.getShb(), "uuid");
		Assert.assertTrue(message != null);
		Assert.assertTrue(message.contains("uuid"));
	}

	@Test
	public void checkTimeTestWithinSpecifiedTime() {
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
		Assert.assertTrue(jmsSenderScheduler.checkTime(appParamsMap));
	}

	@Test
	public void checkTimeTestNotInSpecifiedTime() {
		Map<String, String> appParamsMap = new HashMap<>();
		appParamsMap.put(Constants.MON_START_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.MON_END_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.TUES_START_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.TUES_END_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.WED_START_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.WED_END_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.THUR_START_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.THUR_END_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.FRI_START_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.FRI_END_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.SAT_START_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.SAT_END_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.SUN_START_TIME.getValue(), "00:00");
		appParamsMap.put(Constants.SUN_END_TIME.getValue(), "00:00");
		Assert.assertFalse(jmsSenderScheduler.checkTime(appParamsMap));
	}

}
