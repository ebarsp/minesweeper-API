package com.deviget.minesweeper.infrastructure.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

	@GetMapping("/health")
	public String get() {
		return "pong";
	}
}
