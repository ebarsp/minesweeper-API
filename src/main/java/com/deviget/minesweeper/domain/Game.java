package com.deviget.minesweeper.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Game {
	private final UUID id;
	private final GameGrid grid;
	private final GameStatus status;
	private final Duration duration;
	private final LocalDateTime createdTime;

	private Game(GameBuilder builder) {
		this.id = builder.id;
		this.grid = builder.grid;
		this.status = builder.status;
		this.duration = builder.duration;
		this.createdTime = builder.createdTime;
	}

	public UUID getId() {
		return id;
	}

	public GameGrid getGrid() {
		return grid;
	}

	public GameStatus getStatus() {
		return status;
	}

	public Duration getDuration() {
		return duration;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public static final class GameBuilder {
		private UUID id;
		private GameGrid grid;
		private GameStatus status;
		private Duration duration;
		private LocalDateTime createdTime;

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

		public GameBuilder withCreationTime(LocalDateTime creationTime) {
			this.createdTime = creationTime;
			return this;
		}

		public Game build() {
			if (id == null || grid == null || status == null || duration == null || createdTime == null) {
				throw new InvalidStateException("There are missed arguments");
			}
			return new Game(this);
		}
	}
}
