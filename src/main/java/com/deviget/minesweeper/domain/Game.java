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

	private Game(GameBuilder builder) {
		this.id = builder.id;
		this.grid = builder.grid;
		this.status = builder.status;
		this.duration = builder.duration;
		this.lastUpdateTime = builder.lastUpdateTime;
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
		return String.format("%d:%02d:%02d", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
	}

	public LocalDateTime getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void recalculateDuration() {
		if (isOngoing()) {
			final LocalDateTime now = LocalDateTime.now();
			this.duration = Duration.between(lastUpdateTime, now);
			this.lastUpdateTime = now;
		}
	}

	public void pause() {
		validateGame();
		recalculateDuration();
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
		recalculateDuration();
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

	public static final class GameBuilder {
		private UUID id;
		private GameGrid grid;
		private GameStatus status;
		private Duration duration;
		private LocalDateTime lastUpdateTime;

		private GameBuilder() {
		}

		public static GameBuilder aGame() {
			return new GameBuilder();
		}

		public GameBuilder withId(UUID id) {
			this.id = id;
			return this;
		}

		public GameBuilder withGrid(GameGrid grid) {
			this.grid = grid;
			return this;
		}

		public GameBuilder withStatus(GameStatus status) {
			this.status = status;
			return this;
		}

		public GameBuilder withDuration(Duration duration) {
			this.duration = duration;
			return this;
		}

		public GameBuilder withLastUpdateTime(LocalDateTime lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
			return this;
		}

		public Game build() {
			if (id == null || grid == null || status == null || duration == null || lastUpdateTime == null) {
				throw new InvalidStateException("There are missed arguments");
			}
			return new Game(this);
		}
	}
}
