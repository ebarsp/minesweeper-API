package com.deviget.minesweeper.domain;

import java.util.List;

public interface IGameGrid {
	Integer getHeight();

	Integer getWidth();

	Integer getMines();

	List<ICell> getCells();
}
