package com.deviget.minesweeper.domain;

import java.util.List;

public interface IGameGrid {
	Integer getWidth();

	Integer getHeight();

	Integer getMines();

	List<ICell> getCells();
}
