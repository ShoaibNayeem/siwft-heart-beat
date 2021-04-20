package com.swift.heartbeat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.swift.heartbeat.schedulers.JmsSenderScheduler;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class HeartBeatApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatApplication.class);

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(HeartBeatApplication.class, args);
		JmsSenderScheduler jmsSenderScheduler = context.getBean(JmsSenderScheduler.class);
		LOGGER.info("Heart Beat Application startup...");
		jmsSenderScheduler.sendMessageToQueue();
	}

	public static void restart() {
		LOGGER.info("Restarting the application...");
		ApplicationArguments applicationArguments = context.getBean(ApplicationArguments.class);
		Thread thread = new Thread(() -> {
			context.close();
			context = SpringApplication.run(HeartBeatApplication.class, applicationArguments.getSourceArgs());
		});
		thread.setDaemon(false);
		thread.start();
	}
}
