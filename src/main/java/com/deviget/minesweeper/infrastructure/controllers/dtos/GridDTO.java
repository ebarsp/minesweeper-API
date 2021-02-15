package com.deviget.minesweeper.infrastructure.controllers.dtos;

import com.deviget.minesweeper.domain.GameGrid;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GridDTO {
	@JsonProperty(required = true)
	private Integer height;
	@JsonProperty(required = true)
	private Integer width;
	@JsonProperty(required = true)
	private Integer mines;

	public GridDTO() {}

	public GridDTO(GameGrid grid) {
		this.height = grid.getHeight();
		this.width = grid.getWidth();
		this.mines = grid.getMines();
	}

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
