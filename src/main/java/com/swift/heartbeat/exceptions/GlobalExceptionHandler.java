package com.swift.heartbeat.exceptions;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.swift.heartbeat.beans.ErrorDetails;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

	private static final long serialVersionUID = 4953826787011952250L;

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllExceptions(Exception ex) throws Exception {
		if (ex.getCause() instanceof SQLException || ex.getCause() instanceof IOException) {
			// logAlert
			LOGGER.info("CRITICAL: {}", ex.getLocalizedMessage());
		} else {
			LOGGER.info("ERROR: {}", ex.getLocalizedMessage());
		}
		LOGGER.info("CRITICAL: {}", ex.getLocalizedMessage());
		List<String> details = new ArrayList<>();
		details.add("Please check");
		ErrorDetails error = new ErrorDetails(new Date(), "Internal Server Error", details, "500");
		for (Field field : error.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			String name = field.getName();
			Object value = field.get(error);
			LOGGER.info("[\"{}\" : \"{}\"]", name, value);
		}
		return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
