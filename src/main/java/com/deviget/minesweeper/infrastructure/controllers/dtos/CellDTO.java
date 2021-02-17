package com.deviget.minesweeper.infrastructure.controllers.dtos;

public class CellDTO {
	private Integer x;
	private Integer y;
	private String value;

	public CellDTO(Integer x, Integer y, String value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public String getValue() {
		return value;
	}
}
