package com.deviget.minesweeper.domain;

public class GameOverException extends RuntimeException {
	private final GameStatus status;

	public GameOverException(GameStatus status) {
		super();
		this.status = status;
	}

	public GameStatus getStatus() {
		return status;
	}
}
