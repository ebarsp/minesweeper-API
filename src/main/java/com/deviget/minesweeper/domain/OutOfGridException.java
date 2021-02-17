package com.deviget.minesweeper.domain;

public class OutOfGridException extends RuntimeException {

	public OutOfGridException(String message) {
		super(message);
	}
}
