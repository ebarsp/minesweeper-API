package com.deviget.minesweeper.domain;

import java.util.UUID;

public interface IGame {
	UUID getId();

	GameGrid getGrid();

	GameStatus getStatus();

	String getDurationString();
}
