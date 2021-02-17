package com.deviget.minesweeper.infrastructure.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GridPayloadDTO {
	@JsonProperty(required = true)
	private Integer height;
	@JsonProperty(required = true)
	private Integer width;
	@JsonProperty(required = true)
	private Integer mines;

	public Integer getHeight() {
		return height;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getMines() {
		return mines;
	}
}
