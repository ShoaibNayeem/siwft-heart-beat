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
	public ResponseEntity<String> onDemand() {
		LOGGER.info("On Demand service called");
		String status = Constants.FAILURE;
		Map<String, String> appParamsMap = new HashMap<>();
		appParamsMap = swiftHeartBeatUtils.getApplicationParameters();
		if (appParamsMap != null && !appParamsMap.isEmpty()) {
			Session session = getSessionInstance(appParamsMap);
			status = emailService.sendEmail(session, appParamsMap, "Swift Heart-Beat Check",
					"Hi,\n\nReceived request to check swift heart-beat.\n\nThanks & Regards,\nTeam");
			jmsSenderScheduler.sendMessage();
		}
		return ResponseEntity.ok().body(status);
	}

	private Session getSessionInstance(Map<String, String> appParamsMap) {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", appParamsMap.get(Constants.SMTP_HOST));
		props.put("mail.smtp.port", appParamsMap.get(Constants.SMTP_PORT));
		props.put("mail.smtp.auth", appParamsMap.get(Constants.SMTP_AUTH));
		props.put("mail.smtp.starttls.enable", appParamsMap.get(Constants.SMTP_STARTTLS_ENABLE));
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(appParamsMap.get(Constants.SMTP_SENDER),
						appParamsMap.get(Constants.SMTP_SENDER_PASSWORD));
			}
		};
		return Session.getDefaultInstance(props, auth);
	}

}
