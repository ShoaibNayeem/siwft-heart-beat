package com.swift.heartbeat.services;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.swift.heartbeat.constants.Constants;

@Service
public class EmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

	public String sendEmail(Session session, Map<String, String> appParamsMap) {
		try {
			LOGGER.info("Sending email to " + appParamsMap.get(Constants.RECIPIENT.getValue()));
			MimeMessage msg = new MimeMessage(session);
			msg = prepareEmailContent(msg, appParamsMap);
//			Transport transport = session.getTransport("smtp");
//			transport.connect();
			Transport.send(msg);
			LOGGER.info("Email sent successfully");
		} catch (Exception e) {
			LOGGER.error("Error occured while sending email " + e.getMessage());
		}
		return Constants.SUCCESS.getValue();
	}

	private MimeMessage prepareEmailContent(MimeMessage msg, Map<String, String> appParamsMap)
			throws MessagingException, UnsupportedEncodingException {
		msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		msg.addHeader("format", "flowed");
		msg.addHeader("Content-Transfer-Encoding", "8bit");
		msg.setFrom(new InternetAddress(appParamsMap.get(Constants.SMTP_SENDER.getValue()), "Swift Team"));
		msg.setReplyTo(InternetAddress.parse(appParamsMap.get(Constants.SMTP_SENDER.getValue()), false));
		msg.setSubject(appParamsMap.get(Constants.EMAIL_SUBJECT.getValue()), "UTF-8");
		msg.setText(appParamsMap.get(Constants.EMAIL_BODY.getValue()), "UTF-8");
		msg.setSentDate(new Date());
		msg.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(appParamsMap.get(Constants.RECIPIENT.getValue()), false));
		return msg;
	}

}
