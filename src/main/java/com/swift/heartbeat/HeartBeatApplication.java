package com.swift.heartbeat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HeartBeatApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(HeartBeatApplication.class, args);
		// Get JMS template bean reference
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

		// Send a message
		System.out.println("Sending a message.");
		jmsTemplate.convertAndSend("http://localhost:8161/", "Test JMS message");
	}

}
