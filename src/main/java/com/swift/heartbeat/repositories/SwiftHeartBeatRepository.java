package com.swift.heartbeat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swift.heartbeat.entities.SwiftHeartBeatEntity;

@Repository
public interface SwiftHeartBeatRepository extends JpaRepository<SwiftHeartBeatEntity, String> {

	SwiftHeartBeatEntity findByCorrelationId(String correlationId);
	List<SwiftHeartBeatEntity> findAllByAlarmistCheck(String alarmistCheck);

}
