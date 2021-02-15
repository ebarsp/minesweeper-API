package com.deviget.minesweeper.application;

import com.deviget.minesweeper.domain.Game;
import com.deviget.minesweeper.domain.GameGrid;
import com.deviget.minesweeper.domain.GameStatus;
import com.deviget.minesweeper.domain.IGame;
import com.deviget.minesweeper.domain.NotFoundException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class GameService {
	private final GameRepository gameRepository;

	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	/**
	 * Create a Game from scratch
	 * @param grid
	 * @return a new {@link Game}
	 */
	public IGame create(GameGrid grid) {
		final Game game = Game.GameBuilder.aGame()
				.withId(UUID.randomUUID())
				.withStatus(GameStatus.ONGOING)
				.withDuration(Duration.ZERO)
				.withLastUpdateTime(LocalDateTime.now())
				.withGrid(grid)
				.build();
		gameRepository.save(game);
		return game;
	}

	/**
	 * Get an existent Game
	 * @param id
	 * @return an existing {@link Game}
	 * @throws {@link NotFoundException} if there is not a game with the param id
	 */
	public IGame get(UUID id) {
		final Game game = gameRepository.get(id)
				.orElseThrow(NotFoundException::new);
		game.recalculateDuration();
		return game;
	}

	/**
	 * Pause an ongoing Game
	 * @param id
	 * @throws {@link NotFoundException} if there is not a game with the param id
	 */
	public IGame pause(UUID id) {
		final Game game = gameRepository.get(id)
				.orElseThrow(NotFoundException::new);
		game.pause();
		gameRepository.save(game);
		return game;
	}

	/**
	 * Unpause a paused Game
	 * @param id
	 * @throws {@link NotFoundException} if there is not a game with the param id
	 */
	public IGame unpause(UUID id) {
		final Game game = gameRepository.get(id)
				.orElseThrow(NotFoundException::new);
		game.unpause();
		gameRepository.save(game);
		return game;
	}
}
