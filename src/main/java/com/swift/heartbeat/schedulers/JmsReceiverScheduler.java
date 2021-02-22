package com.swift.heartbeat.schedulers;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsReceiverScheduler {

	@JmsListener(destination = "http://localhost:8161/")
	public void receiveMessage(String message) {
		System.out.println("Recieved message " + message);
	}

}
