package com.deviget.minesweeper.infrastructure.controllers.mappers;

import com.deviget.minesweeper.domain.GameGrid;
import com.deviget.minesweeper.domain.IGameGrid;
import com.deviget.minesweeper.infrastructure.controllers.dtos.GridPayloadDTO;
import com.deviget.minesweeper.infrastructure.controllers.dtos.GridResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class GridMapper {
	private CellMapper cellMapper;

	public GridMapper(CellMapper cellMapper) {
		this.cellMapper = cellMapper;
	}

	public GridResponseDTO domainToApi(final IGameGrid grid) {
		return GridResponseDTO.Builder.aGridResponseDTO()
				.withHeight(grid.getHeight())
				.withWidth(grid.getWidth())
				.withCells(cellMapper.domainToApi(grid.getCells()))
				.withMines(grid.getMines())
				.build();
	}

	public GameGrid apiToDomain(final GridPayloadDTO grid) {
		return GameGrid.GridBuilder.aGrid()
				.withWidth(grid.getWidth())
				.withHeight(grid.getHeight())
				.withMines(grid.getMines())
				.build();
	}
}
