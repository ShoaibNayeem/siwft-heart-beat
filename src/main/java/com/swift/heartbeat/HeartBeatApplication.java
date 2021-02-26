package com.swift.heartbeat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.swift.heartbeat.schedulers.JmsSenderScheduler;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class HeartBeatApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(HeartBeatApplication.class, args);
		JmsSenderScheduler jmsSenderScheduler = context.getBean(JmsSenderScheduler.class);
		LOGGER.info("Heart Beat Application startup...");
		jmsSenderScheduler.sendMessage();
	}

}
