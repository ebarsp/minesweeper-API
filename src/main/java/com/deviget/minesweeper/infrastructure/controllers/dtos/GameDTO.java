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

	public GameDTO(UUID id, GameStatus status, String duration, GridResponseDTO grid) {
		this.id = id;
		this.status = status;
		this.duration = duration;
		this.grid = grid;
	}
}
