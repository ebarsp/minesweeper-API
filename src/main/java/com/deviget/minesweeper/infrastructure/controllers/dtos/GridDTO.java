package com.deviget.minesweeper.infrastructure.controllers.dtos;

import com.deviget.minesweeper.domain.GameGrid;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GridDTO {
	@JsonProperty(value = "grid_x", required = true)
	private Integer x;
	@JsonProperty(value = "grid_y", required = true)
	private Integer y;
	@JsonProperty(required = true)
	private Integer mines;

	public GridDTO() {}

	public GridDTO(GameGrid grid) {
		this.x = grid.getX();
		this.y = grid.getY();
		this.mines = grid.getMines();
	}

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
