package com.deviget.minesweeper.infrastructure.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CellDTO {
	@JsonProperty
	private Integer x;
	@JsonProperty
	private Integer y;
	@JsonProperty
	private String value;

	public CellDTO(Integer x, Integer y, String value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
}
