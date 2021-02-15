package com.deviget.minesweeper.infrastructure.controllers.mappers;

import com.deviget.minesweeper.domain.IGame;
import com.deviget.minesweeper.infrastructure.controllers.dtos.GameDTO;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {
	private final GridMapper gridMapper;

	public GameMapper(GridMapper gridMapper) {
		this.gridMapper = gridMapper;
	}

	public GameDTO domainToApi(IGame game) {
		return GameDTO.GameDTOBuilder.aGameDTO()
				.withId(game.getId())
				.withStatus(game.getStatus())
				.withDuration(game.getDurationString())
				.withGrid(gridMapper.domainToApi(game.getGrid()))
				.build();
	}
}
