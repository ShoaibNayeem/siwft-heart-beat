package com.swift.heartbeat;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = HeartBeatApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-unittesting.properties")
class HeartBeatApplicationTests {
	@Test
	void contextLoads() {
		Assert.assertTrue(true);
	}
}
