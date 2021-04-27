package com.swift.heartbeat.config;

import java.util.concurrent.Executor;

import javax.jms.ConnectionFactory;
import javax.jms.Session;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.ErrorHandler;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;

@Configuration
@EnableJms
public class JmsConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(JmsConfiguration.class);

	@Bean
	public LockProvider lockProvider(DataSource dataSource) {
		return new JdbcTemplateLockProvider(dataSource);
	}

	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

		factory.setErrorHandler(new ErrorHandler() {
			@Override
			public void handleError(Throwable t) {
				LOGGER.error("An error has occurred in the transaction");
			}
		});

		factory.setErrorHandler(t -> LOGGER.error("An error has occurred in the transaction"));
		factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
		configurer.configure(factory, connectionFactory);
		return factory;
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	@Bean("jmsSenderSchedulerJobPool")
	public Executor jmsSenderSchedulerJobPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(1);
		executor.setQueueCapacity(10);
		executor.setThreadNamePrefix("jssjp-");
		executor.initialize();
		return executor;
	}

	@Bean("alarmistSchedulerJobPool")
	public Executor alarmistSchedulerJobPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(1);
		executor.setQueueCapacity(10);
		executor.setThreadNamePrefix("asjp-");
		executor.initialize();
		return executor;
	}

	@Bean("houseKeepingSchedulerJobPool")
	public Executor houseKeepingSchedulerJobPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(1);
		executor.setQueueCapacity(10);
		executor.setThreadNamePrefix("hksjp-");
		executor.initialize();
		return executor;
	}

}
