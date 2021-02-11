package com.deviget.minesweeper.application;

import com.deviget.minesweeper.domain.Game;
import com.deviget.minesweeper.domain.GameGrid;
import com.deviget.minesweeper.domain.GameStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class MinesweeperService {
	private final GameRepository gameRepository;

	public MinesweeperService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	/**
	 *
	 * @param grid
	 * @return a new {@link Game}
	 */
	public Game create(GameGrid grid) {
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
	 *
	 * @param id
	 * @return an existing {@link Game}
	 */
	public Game get(UUID id) {
		final Game game = gameRepository.get(id);
		game.recalculateDuration();
		return game;
	}
}
