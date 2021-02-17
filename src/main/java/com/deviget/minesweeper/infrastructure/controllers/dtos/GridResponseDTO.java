package com.deviget.minesweeper.infrastructure.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GridResponseDTO {
	@JsonProperty
	private Integer height;
	@JsonProperty
	private Integer width;
	@JsonProperty
	private Integer mines;
	@JsonProperty
	private List<CellDTO> cells;

	public GridResponseDTO(Builder builder) {
		this.mines = builder.mines;
		this.cells = builder.cells;
		this.width = builder.width;
		this.height = builder.height;
	}

	public static final class Builder {
		private Integer height;
		private Integer width;
		private Integer mines;
		private List<CellDTO> cells;

		private Builder() {
		}

		public static Builder aGridResponseDTO() {
			return new Builder();
		}

		public Builder withHeight(Integer height) {
			this.height = height;
			return this;
		}

		public Builder withWidth(Integer width) {
			this.width = width;
			return this;
		}

		public Builder withMines(Integer mines) {
			this.mines = mines;
			return this;
		}

		public Builder withCells(List<CellDTO> cells) {
			this.cells = cells;
			return this;
		}

		public GridResponseDTO build() {
			return new GridResponseDTO(this);
		}
	}
}
