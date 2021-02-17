package com.deviget.minesweeper.infrastructure.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GridResponseDTO {
	@JsonProperty
	private Integer width;
	@JsonProperty
	private Integer height;
	@JsonProperty
	private Integer mines;
	@JsonProperty
	private List<CellDTO> cells;

	public GridResponseDTO(Integer width, Integer height, Integer mines, List<CellDTO> cells) {
		this.width = width;
		this.height = height;
		this.mines = mines;
		this.cells = cells;
	}
}
