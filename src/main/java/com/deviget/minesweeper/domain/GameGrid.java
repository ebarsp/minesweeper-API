package com.deviget.minesweeper.domain;

public class GameGrid {
	private final Integer x;
	private final Integer y;
	private final Integer mines;

	private GameGrid(GridBuilder builder) {
		this.y = builder.y;
		this.x = builder.x;
		this.mines = builder.mines;
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

	public static final class GridBuilder {
		private Integer x;
		private Integer y;
		private Integer mines;

		private GridBuilder() {
		}

		public static GridBuilder aGrid() {
			return new GridBuilder();
		}

		public GridBuilder withX(Integer x) {
			this.x = x;
			return this;
		}

		public GridBuilder withY(Integer y) {
			this.y = y;
			return this;
		}

		public GridBuilder withMines(Integer mines) {
			this.mines = mines;
			return this;
		}

		public GameGrid build() {
			if (x == null || y == null || mines == null) {
				throw new InvalidStateException("There are missed arguments");
			}
			return new GameGrid(this);
		}
	}
}
