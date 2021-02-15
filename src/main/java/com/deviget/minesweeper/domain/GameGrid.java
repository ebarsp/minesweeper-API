package com.deviget.minesweeper.domain;

public class GameGrid {
	private final Integer height;
	private final Integer width;
	private final Integer mines;

	private GameGrid(GridBuilder builder) {
		this.height = builder.height;
		this.width = builder.width;
		this.mines = builder.mines;
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

	public static final class GridBuilder {
		private Integer height;
		private Integer width;
		private Integer mines;

		private GridBuilder() {
		}

		public static GridBuilder aGrid() {
			return new GridBuilder();
		}

		public GridBuilder withHeight(Integer height) {
			this.height = height;
			return this;
		}

		public GridBuilder withWidth(Integer width) {
			this.width = width;
			return this;
		}

		public GridBuilder withMines(Integer mines) {
			this.mines = mines;
			return this;
		}

		public GameGrid build() {
			if (height == null || width == null || mines == null) {
				throw new InvalidStateException("There are missed arguments");
			}
			return new GameGrid(this);
		}
	}
}
