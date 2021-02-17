package com.deviget.minesweeper.infrastructure.controllers.mappers;

import com.deviget.minesweeper.domain.GameGrid;
import com.deviget.minesweeper.domain.IGameGrid;
import com.deviget.minesweeper.infrastructure.controllers.dtos.CellDTO;
import com.deviget.minesweeper.infrastructure.controllers.dtos.GridPayloadDTO;
import com.deviget.minesweeper.infrastructure.controllers.dtos.GridResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GridMapper {
	private final CellMapper cellMapper;

	public GridMapper(CellMapper cellMapper) {
		this.cellMapper = cellMapper;
	}

	public GridResponseDTO domainToApi(final IGameGrid grid) {
		final List<CellDTO> cells = cellMapper.domainToApi(grid.getCells());
		return new GridResponseDTO(grid.getWidth(), grid.getHeight(), grid.getMines(), cells);
	}

	public GameGrid apiToDomain(final GridPayloadDTO grid) {
		return new GameGrid(grid.getWidth(), grid.getHeight(), grid.getMines());
	}
}
