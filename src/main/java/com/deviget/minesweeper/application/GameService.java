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
	 *
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
	 *
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
}
