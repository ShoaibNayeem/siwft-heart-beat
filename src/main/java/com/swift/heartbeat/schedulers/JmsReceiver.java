package com.swift.heartbeat.schedulers;

import java.util.Date;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.swift.heartbeat.constants.Constants;
import com.swift.heartbeat.entities.SwiftHeartBeatEntity;
import com.swift.heartbeat.repositories.SwiftHeartBeatRepository;

@Component
public class JmsReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(JmsReceiver.class);

	@Autowired
	private SwiftHeartBeatRepository swiftHeartBeatRepository;

	@JmsListener(destination = "${shb.req.queue.name}", containerFactory = "myFactory", concurrency = "1")
	public void receiveMessage(Message message) {
		try {
			LOGGER.info("Recieved message from the queue \n" + message);
			String messageData = null;
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				messageData = textMessage.getText();
				System.out.println("message " + messageData);
			}
			String correlationId = getCorrelationIdFromMessage(messageData);
			LOGGER.info("Extracted correlation Id from the message --> " + correlationId.trim());
			SwiftHeartBeatEntity swiftHeartBeatEntity = swiftHeartBeatRepository.findByCorrelationId(correlationId.trim());
			swiftHeartBeatEntity.setRepTimestamp(new Date());
			swiftHeartBeatEntity.setAlarmistCheck(Constants.NEW.getValue());
			swiftHeartBeatRepository.save(swiftHeartBeatEntity);
		} catch (Exception e) {
			LOGGER.error("Exception occured in JMS listener " + e.getMessage());
		}
	}

	private String getCorrelationIdFromMessage(String message) {
		return StringUtils.substringBetween(message, ":77E:", "\n");
	}

}
