package com.deviget.minesweeper.infrastructure.controllers.dtos;

import com.deviget.minesweeper.domain.GameStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class GameDTO {
	@JsonProperty("game_id")
	private final UUID id;
	@JsonProperty
	private final GameStatus status;
	@JsonProperty
	private final String duration;
	@JsonProperty
	private final GridDTO grid;

	public GameDTO(GameDTOBuilder builder) {
		this.id = builder.id;
		this.duration = builder.duration;
		this.status = builder.status;
		this.grid = builder.grid;
	}

	public static final class GameDTOBuilder {
		private UUID id;
		private GameStatus status;
		private String duration;
		private GridDTO grid;

		private GameDTOBuilder() {
		}

		public static GameDTOBuilder aGameDTO() {
			return new GameDTOBuilder();
		}

		public GameDTOBuilder withId(UUID id) {
			this.id = id;
			return this;
		}

		public GameDTOBuilder withStatus(GameStatus status) {
			this.status = status;
			return this;
		}

		public GameDTOBuilder withDuration(String duration) {
			this.duration = duration;
			return this;
		}

		public GameDTOBuilder withGrid(GridDTO grid) {
			this.grid = grid;
			return this;
		}

		public GameDTO build() {
			return new GameDTO(this);
		}
	}
}
