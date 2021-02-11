package com.deviget.minesweeper.domain;

public class InvalidStateException extends RuntimeException {

	public InvalidStateException(final String message) {
		super(message);
	}
}
