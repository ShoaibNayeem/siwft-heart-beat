package com.swift.heartbeat.repositories;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.swift.heartbeat.entities.SwiftHeartBeatEntity;

@Repository
public interface SwiftHeartBeatRepository extends JpaRepository<SwiftHeartBeatEntity, String> {

	SwiftHeartBeatEntity findByCorrelationId(String correlationId);

	List<SwiftHeartBeatEntity> findAllByAlarmistCheck(String alarmistCheck);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM tb_swift_heart_beat sw where sw.req_time_stamp <= :date", nativeQuery = true)
	void deleteOldRecords(LocalDateTime date);

}
