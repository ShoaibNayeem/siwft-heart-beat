package com.swift.heartbeat.repositories;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.swift.heartbeat.constants.Constants;
import com.swift.heartbeat.entities.SwiftHeartBeatEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SwiftHeartBeatRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private SwiftHeartBeatRepository swiftHeartBeatRepository;

	@Test
	public void saveNewEntityTest() {
		SwiftHeartBeatEntity swiftHeartBeatEntity = new SwiftHeartBeatEntity();
		swiftHeartBeatEntity.setCorrelationId("123abc");
		swiftHeartBeatEntity.setReqTimestamp(new Date());
		entityManager.persist(swiftHeartBeatEntity);
		SwiftHeartBeatEntity swift = swiftHeartBeatRepository.findByCorrelationId("123abc");
		Assert.assertEquals(swift.getCorrelationId(), "123abc");
	}

	@Test
	public void getEntityWithAlarmistCheckNew() {
		SwiftHeartBeatEntity swiftHeartBeatEntity = new SwiftHeartBeatEntity();
		swiftHeartBeatEntity.setCorrelationId("123abc");
		swiftHeartBeatEntity.setReqTimestamp(new Date());
		swiftHeartBeatEntity.setAlarmistCheck(Constants.NEW.getValue());
		entityManager.persist(swiftHeartBeatEntity);
		List<SwiftHeartBeatEntity> swift = swiftHeartBeatRepository.findAllByAlarmistCheck(Constants.NEW.getValue());
		for (SwiftHeartBeatEntity sw : swift) {
			Assert.assertEquals(sw.getAlarmistCheck(), Constants.NEW.getValue());
		}
	}
}
