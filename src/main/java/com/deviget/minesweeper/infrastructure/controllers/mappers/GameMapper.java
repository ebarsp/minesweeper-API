package com.deviget.minesweeper.infrastructure.controllers.mappers;

import com.deviget.minesweeper.domain.Game;
import com.deviget.minesweeper.infrastructure.controllers.dtos.GameDTO;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

	public GameDTO domainToApi(Game game) {
		return new GameDTO(game.getId());
	}
}
