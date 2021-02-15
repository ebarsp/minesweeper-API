package com.deviget.minesweeper.infrastructure.repositories;

import com.deviget.minesweeper.application.GameRepository;
import com.deviget.minesweeper.domain.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
	public Optional<Game> get(UUID id) {
		return Optional.ofNullable(dictionary.get(id));
	}
}
