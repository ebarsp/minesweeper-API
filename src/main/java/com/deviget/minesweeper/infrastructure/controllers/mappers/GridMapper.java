package com.deviget.minesweeper.infrastructure.controllers.mappers;

import com.deviget.minesweeper.domain.GameGrid;
import com.deviget.minesweeper.infrastructure.controllers.dtos.GridPayloadDTO;
import org.springframework.stereotype.Component;

@Component
public class GridMapper {

	public GameGrid apiToDomain(final GridPayloadDTO payload) {
		return GameGrid.GridBuilder.aGrid()
				.withX(payload.getX())
				.withY(payload.getY())
				.withMines(payload.getMines())
				.build();
	}
}
