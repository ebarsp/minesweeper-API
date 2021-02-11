package com.deviget.minesweeper.infrastructure.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class GameDTO {
	@JsonProperty("game_id")
	private final UUID id;

	public GameDTO(UUID id) {
		this.id = id;
	}
}
