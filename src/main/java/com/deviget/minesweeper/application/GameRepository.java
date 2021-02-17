package com.deviget.minesweeper.application;

import com.deviget.minesweeper.domain.Game;

import java.util.Optional;
import java.util.UUID;

public interface GameRepository {
	/**
	 * Save or update a Game.
	 * @param game {@link Game}
	 * @throws RepositoryException if there is something wrong
	 */
	void save(Game game);

	/**
	 * Get a Game by Id.
	 * @param id {@link UUID}
	 * @return an existing {@link Game}
	 * @throws RepositoryException if there is something wrong
	 */
	Optional<Game> get(UUID id);
}
