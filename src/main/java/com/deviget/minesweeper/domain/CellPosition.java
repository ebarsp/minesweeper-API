package com.deviget.minesweeper.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CellPosition {
	private final Integer x;
	private final Integer y;

	public CellPosition(Integer x, Integer y) {
		if (x == null || y == null) {
			throw new InvalidStateException("There are missed arguments");
		}
		this.x = x;
		this.y = y;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public List<CellPosition> getAdjacentPositions() {
		List<CellPosition> positions = new ArrayList<>();
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				positions.add(new CellPosition(this.x + x, this.y + y));
			}
		}
		positions.remove(this);
		return positions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CellPosition that = (CellPosition) o;
		return Objects.equals(x, that.x) && Objects.equals(y, that.y);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public String toString() {
		return String.format("(%d,%d)", x, y);
	}
}
