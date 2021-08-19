package com.swift.heartbeat.aspect;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);

	public static final int MAX_SIZE_OF_ERROR_MSG = 200;
	public static final String CRITICAL_LOG_FORMAT = "CRITICAL: {}: {}: {}";

	private LoggerAspect() {

	}

	public static void logAlert(Logger logger, String className, String methodName, String message) {
		logger.error(CRITICAL_LOG_FORMAT, className, methodName, StringUtils.left(message, MAX_SIZE_OF_ERROR_MSG));
	}

	@AfterThrowing(pointcut = "execution(* com.swift.heartbeat..*.*(..))", throwing = "ex")
	public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
		if (ex.getCause() instanceof SQLException || ex.getCause() instanceof IOException
				|| ex.getCause() instanceof IllegalAccessException
				|| ex.getCause() instanceof org.hibernate.TransactionException
				|| ex.getCause() instanceof TransactionException) {
			logAlert(LOGGER, joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(),
					ex.getLocalizedMessage());
			LOGGER.info(CRITICAL_LOG_FORMAT, joinPoint.getTarget().getClass().getSimpleName(),
					joinPoint.getSignature().getName(), ex.getLocalizedMessage());
			LOGGER.warn("An exception has been thrown : " + Arrays.toString(ex.getStackTrace()));
		} else {
			LOGGER.info("ERROR: {}", ex.getLocalizedMessage());
		}
	}

}
