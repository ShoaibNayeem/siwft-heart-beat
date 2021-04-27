package com.swift.heartbeat.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

	private static final long serialVersionUID = 4953826787011952250L;

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public void handleExceptions(Exception ex) {
		LOGGER.error("Runtime Exception occcured " + ex.getMessage());
	}
}
