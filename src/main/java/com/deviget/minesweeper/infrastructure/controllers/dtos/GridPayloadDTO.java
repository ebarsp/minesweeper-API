package com.deviget.minesweeper.infrastructure.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GridPayloadDTO {
	@JsonProperty(value = "grid_x", required = true)
	private Integer x;
	@JsonProperty(value = "grid_y", required = true)
	private Integer y;
	@JsonProperty(required = true)
	private Integer mines;

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public Integer getMines() {
		return mines;
	}
}
