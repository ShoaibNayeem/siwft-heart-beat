package com.swift.heartbeat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swift.heartbeat.schedulers.JmsSenderScheduler;

@RestController
@RequestMapping("/api/v1")
public class Controller {

	private static final Logger logger = LoggerFactory.getLogger(Controller.class);

	@Autowired
	private JmsSenderScheduler jmsSenderScheduler;

	@GetMapping("/ondemand")
	public void send() {
		logger.info("On Demand service called");
		jmsSenderScheduler.sendMessage();
	}
}
