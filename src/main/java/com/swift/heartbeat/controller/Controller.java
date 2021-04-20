package com.swift.heartbeat.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swift.heartbeat.constants.Constants;
import com.swift.heartbeat.schedulers.JmsSenderScheduler;
import com.swift.heartbeat.services.EmailService;
import com.swift.heartbeat.utils.SwiftHeartBeatUtils;

import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/v1")
public class Controller {

	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

	@Autowired
	private JmsSenderScheduler jmsSenderScheduler;

	@Autowired
	private EmailService emailService;

	@Autowired
	private SwiftHeartBeatUtils swiftHeartBeatUtils;

	@GetMapping("/ondemand")
	@ApiResponse(code = 200, message = "Status of Email delivery and sending message to queue")
	public ResponseEntity<String> onDemand() {
		LOGGER.info("On Demand service called");
		String status = Constants.FAILURE.getValue();
		Map<String, String> appParamsMap = new HashMap<>();
		appParamsMap = swiftHeartBeatUtils.getAppParamsMap();
		if (appParamsMap != null && !appParamsMap.isEmpty()) {
			Session session = getSessionInstance(appParamsMap);
			status = emailService.sendEmail(session, appParamsMap);
			if (status.equals(Constants.SUCCESS.getValue()))
				jmsSenderScheduler.sendMessageToQueue();
		}
		return ResponseEntity.ok().body(status);
	}

	private Session getSessionInstance(Map<String, String> appParamsMap) {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", appParamsMap.get(Constants.SMTP_HOST.getValue()));
		props.put("mail.smtp.port", appParamsMap.get(Constants.SMTP_PORT.getValue()));
		props.put("mail.smtp.auth", appParamsMap.get(Constants.SMTP_AUTH.getValue()));
		props.put("mail.smtp.starttls.enable", appParamsMap.get(Constants.SMTP_STARTTLS_ENABLE.getValue()));
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(appParamsMap.get(Constants.SMTP_SENDER.getValue()),
						appParamsMap.get(Constants.SMTP_SENDER_PASSWORD.getValue()));
			}
		};
		return Session.getDefaultInstance(props, auth);
	}

}
