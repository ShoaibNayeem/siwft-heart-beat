package com.swift.heartbeat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swift.heartbeat.HeartBeatApplication;

@RestController
@RequestMapping("/swift")
public class RestartController {

	@GetMapping("/restart")
	public void restartService() {
		HeartBeatApplication.restart();
	}

}
