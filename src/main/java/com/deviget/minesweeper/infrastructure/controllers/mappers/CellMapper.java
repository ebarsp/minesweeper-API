package com.deviget.minesweeper.infrastructure.controllers.mappers;

import com.deviget.minesweeper.domain.CellStatus;
import com.deviget.minesweeper.domain.ICell;
import com.deviget.minesweeper.infrastructure.controllers.dtos.CellDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CellMapper {

	public List<CellDTO> domainToApi(List<ICell> cells) {
		if (cells == null) {
			return null;
		}
		return cells.stream()
				.map(this::getCellDTO)
				.collect(Collectors.toList());
	}

	private CellDTO getCellDTO(ICell cell) {
		String value = CellStatus.UNCOVERED.equals(cell.getStatus()) ? cell.getValue().name() : cell.getStatus().name();
		return new CellDTO(cell.getPosition().getX(), cell.getPosition().getY(), value);
	}
}
