package com.deviget.minesweeper.infrastructure.controllers;

import com.deviget.minesweeper.domain.InvalidStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {
	private static final Logger LOG = LoggerFactory.getLogger(ExceptionsHandler.class);

	@ExceptionHandler
	public ResponseEntity<Object> defaultHandle(final Throwable cause) {
		LOG.error(cause.getMessage(), cause);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@ExceptionHandler(InvalidStateException.class)
	public ResponseEntity<Object> badRequestHandle(final Exception cause) {
		LOG.error(cause.getMessage(), cause);
		return ResponseEntity.badRequest().body(cause.getMessage());
	}
}
