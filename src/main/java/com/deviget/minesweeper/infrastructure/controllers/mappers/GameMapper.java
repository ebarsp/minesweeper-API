package com.deviget.minesweeper.infrastructure.controllers.mappers;

import com.deviget.minesweeper.domain.IGame;
import com.deviget.minesweeper.infrastructure.controllers.dtos.GameDTO;
import com.deviget.minesweeper.infrastructure.controllers.dtos.GridResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {
	private final GridMapper gridMapper;

	public GameMapper(GridMapper gridMapper) {
		this.gridMapper = gridMapper;
	}

	public GameDTO domainToApi(IGame game) {
		final GridResponseDTO grid = gridMapper.domainToApi(game.getGrid());
		return new GameDTO(game.getId(), game.getStatus(), game.getDurationString(), grid);
	}
}
