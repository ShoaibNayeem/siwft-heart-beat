package com.swift.heartbeat.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(JmsReceiver.class);

	@JmsListener(destination = "http://localhost:8161/")
	public void receiveMessage(String message) {
		LOGGER.info("Recieved message from the queue " + message);
	}

}
