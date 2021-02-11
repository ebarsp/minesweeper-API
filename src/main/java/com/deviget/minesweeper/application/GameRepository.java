package com.deviget.minesweeper.application;

import com.deviget.minesweeper.domain.Game;

public interface GameRepository {
	void save(Game game);
}
