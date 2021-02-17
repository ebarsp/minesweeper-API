package com.deviget.minesweeper.domain;

public interface ICell {
	CellPosition getPosition();

	CellStatus getStatus();

	CellValue getValue();
}
