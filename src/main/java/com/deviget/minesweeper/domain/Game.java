package com.deviget.minesweeper.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Game implements IGame {
	private final UUID id;
	private final GameGrid grid;
	private GameStatus status;
	private Duration duration;
	private LocalDateTime lastUpdateTime;

	public Game(UUID id, GameGrid grid, GameStatus status, Duration duration, LocalDateTime lastUpdateTime) {
		if (id == null || grid == null || status == null || duration == null || lastUpdateTime == null) {
			throw new InvalidStateException("There are missed arguments");
		}
		this.id = id;
		this.grid = grid;
		this.status = status;
		this.duration = duration;
		this.lastUpdateTime = lastUpdateTime;
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public GameGrid getGrid() {
		return grid;
	}

	@Override
	public GameStatus getStatus() {
		return status;
	}

	@Override
	public String getDurationString() {
		updateDuration();
		return String.format("%d:%02d:%02d", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
	}

	public LocalDateTime getLastUpdateTime() {
		return lastUpdateTime;
	}

	private void updateDuration() {
		if (isOngoing()) {
			this.duration = duration.plus(Duration.between(lastUpdateTime, LocalDateTime.now()));
		}
	}

	public void pause() {
		validateGame();
		updateDuration();
		this.lastUpdateTime = LocalDateTime.now();
		this.status = GameStatus.PAUSED;
	}

	public void unpause() {
		if (isGameOver() || isOngoing()) {
			throw new InvalidActionException();
		}
		this.status = GameStatus.ONGOING;
		this.lastUpdateTime = LocalDateTime.now();
	}

	public void markACell(CellPosition position) {
		validateGame();
		grid.markACell(position);
	}

	public void unmarkACell(CellPosition position) {
		validateGame();
		grid.unmarkACell(position);
	}

	public void flagACell(CellPosition position) {
		validateGame();
		grid.flagACell(position);
	}

	public void unflagACell(CellPosition position) {
		validateGame();
		grid.unflagACell(position);
	}

	public void uncoverACell(CellPosition position) {
		validateGame();
		try {
			grid.uncoverACell(position);
		} catch (GameOverException e) {
			status = e.getStatus();
		}
		updateDuration();
	}

	private void validateGame() {
		if (isGameOver() || isPaused()) {
			throw new InvalidActionException();
		}
	}

	private boolean isOngoing() {
		return GameStatus.ONGOING.equals(status);
	}

	private boolean isPaused() {
		return GameStatus.PAUSED.equals(status);
	}

	private boolean isGameOver() {
		return GameStatus.WON.equals(status) || GameStatus.LOST.equals(status);
	}
}
