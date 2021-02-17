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
	private final GridResponseDTO grid;

	public GameDTO(Builder builder) {
		this.id = builder.id;
		this.duration = builder.duration;
		this.status = builder.status;
		this.grid = builder.grid;
	}

	public static final class Builder {
		private UUID id;
		private GameStatus status;
		private String duration;
		private GridResponseDTO grid;

		private Builder() {
		}

		public static Builder aGameDTO() {
			return new Builder();
		}

		public Builder withId(UUID id) {
			this.id = id;
			return this;
		}

		public Builder withStatus(GameStatus status) {
			this.status = status;
			return this;
		}

		public Builder withDuration(String duration) {
			this.duration = duration;
			return this;
		}

		public Builder withGrid(GridResponseDTO grid) {
			this.grid = grid;
			return this;
		}

		public GameDTO build() {
			return new GameDTO(this);
		}
	}
}
