package com.deviget.minesweeper.application;

import com.deviget.minesweeper.domain.Game;
import com.deviget.minesweeper.domain.NotFoundException;
import com.deviget.minesweeper.domain.RepositoryException;

import java.util.UUID;

public interface GameRepository {
	/**
	 * Save a Game.
	 * @param game
	 * @throws {@link RepositoryException} if there is something wrong
	 */
	void save(Game game);

	/**
	 * Get a Game by Id.
	 * @param id
	 * @return an existing {@link Game}
	 * @throws {@link NotFoundException} if there is not a game with the param id
	 * @throws {@link RepositoryException} if there is something wrong
	 */
	Game get(UUID id);
}
