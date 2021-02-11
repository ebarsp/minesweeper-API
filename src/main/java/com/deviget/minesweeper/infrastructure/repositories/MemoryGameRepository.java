package com.deviget.minesweeper.infrastructure.repositories;

import com.deviget.minesweeper.application.GameRepository;
import com.deviget.minesweeper.domain.Game;
import com.deviget.minesweeper.domain.NotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryGameRepository implements GameRepository {
	private final Map<UUID, Game> dictionary;

	public MemoryGameRepository() {
		this.dictionary = new HashMap<>();
	}

	@Override
	public void save(Game game) {
		dictionary.put(game.getId(), game);
	}

	@Override
	public Game get(UUID id) {
		final Game game = dictionary.get(id);
		if (game == null) {
			throw new NotFoundException();
		}
		return game;
	}
}
