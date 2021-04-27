package com.swift.heartbeat.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DBPropertyPlaceholderUtil implements BeanPostProcessor, InitializingBean, EnvironmentAware {

	private static final Logger LOGGER = LoggerFactory.getLogger(DBPropertyPlaceholderUtil.class);

	private JdbcTemplate jdbcTemplate;
	private ConfigurableEnvironment environment;

	private final String propertySourceName = "propertiesInsideDatabase";

	public DBPropertyPlaceholderUtil(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterPropertiesSet() {
		LOGGER.info("Loading properties from database");
		if (environment != null) {
			Map<String, Object> systemConfigMap = new HashMap<>();
			String sql = "select `key`, `value` from tb_application_parameters";
			List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : maps) {
				String key = String.valueOf(map.get("key"));
				Object value = map.get("value");
				systemConfigMap.put(key, value);
			}
			environment.getPropertySources().addFirst(new MapPropertySource(propertySourceName, systemConfigMap));
		}
	}

	@Override
	public void setEnvironment(Environment environment) {
		if (environment instanceof ConfigurableEnvironment) {
			this.environment = (ConfigurableEnvironment) environment;
		}
	}

}
