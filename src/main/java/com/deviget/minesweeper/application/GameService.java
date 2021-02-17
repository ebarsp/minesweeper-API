package com.deviget.minesweeper.application;

import com.deviget.minesweeper.domain.CellPosition;
import com.deviget.minesweeper.domain.Game;
import com.deviget.minesweeper.domain.GameGrid;
import com.deviget.minesweeper.domain.GameStatus;
import com.deviget.minesweeper.domain.IGame;

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
	 * @return the paused {@link Game}
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
	 * @return the ongoing {@link Game}
	 * @throws {@link NotFoundException} if there is not a game with the param id
	 */
	public IGame unpause(UUID id) {
		final Game game = gameRepository.get(id)
				.orElseThrow(NotFoundException::new);
		game.unpause();
		gameRepository.save(game);
		return game;
	}

	/**
	 * Uncover a Game's cell indicated by a position
	 * @param id
	 * @param position
	 * @return the current {@link Game}
	 * @throws {@link NotFoundException} if there is not a game with the param id
	 */
	public IGame uncoverACell(UUID id, CellPosition position) {
		final Game game = gameRepository.get(id)
				.orElseThrow(NotFoundException::new);
		game.uncoverACell(position);
		gameRepository.save(game);
		return game;
	}

	/**
	 * Mark a Game's cell indicated by a position
	 * @param id
	 * @param position
	 * @return the ongoing {@link Game}
	 * @throws {@link NotFoundException} if there is not a game with the param id
	 */
	public IGame markACell(UUID id, CellPosition position) {
		final Game game = gameRepository.get(id)
				.orElseThrow(NotFoundException::new);
		game.markACell(position);
		gameRepository.save(game);
		return game;
	}

	/**
	 * Unmark a marked Game's cell indicated by position
	 * @param id
	 * @param position
	 * @return the ongoing {@link Game}
	 * @throws {@link NotFoundException} if there is not a game with the param id
	 */
	public IGame unmarkACell(UUID id, CellPosition position) {
		final Game game = gameRepository.get(id)
				.orElseThrow(NotFoundException::new);
		game.unmarkACell(position);
		gameRepository.save(game);
		return game;
	}

	/**
	 * Flag a Game's cell indicated by a position
	 * @param id
	 * @param position
	 * @return the ongoing {@link Game}
	 * @throws {@link NotFoundException} if there is not a game with the param id
	 */
	public IGame flagACell(UUID id, CellPosition position) {
		final Game game = gameRepository.get(id)
				.orElseThrow(NotFoundException::new);
		game.flagACell(position);
		gameRepository.save(game);
		return game;
	}

	/**
	 * Unflag a flagged Game's cell indicated by position
	 * @param id
	 * @param position
	 * @return the ongoing {@link Game}
	 * @throws {@link NotFoundException} if there is not a game with the param id
	 */
	public IGame unflagACell(UUID id, CellPosition position) {
		final Game game = gameRepository.get(id)
				.orElseThrow(NotFoundException::new);
		game.unflagACell(position);
		gameRepository.save(game);
		return game;
	}
}
