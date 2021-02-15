package com.deviget.minesweeper.infrastructure.controllers.mappers;

import com.deviget.minesweeper.domain.GameGrid;
import com.deviget.minesweeper.infrastructure.controllers.dtos.GridDTO;
import org.springframework.stereotype.Component;

@Component
public class GridMapper {

	public GridDTO domainToApi(final GameGrid grid) {
		return new GridDTO(grid);
	}

	public GameGrid apiToDomain(final GridDTO grid) {
		return GameGrid.GridBuilder.aGrid()
				.withWidth(grid.getWidth())
				.withHeight(grid.getHeight())
				.withMines(grid.getMines())
				.build();
	}
}
