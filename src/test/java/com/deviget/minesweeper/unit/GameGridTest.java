package com.deviget.minesweeper.unit;

import com.deviget.minesweeper.domain.CellPosition;
import com.deviget.minesweeper.domain.CellStatus;
import com.deviget.minesweeper.domain.CellValue;
import com.deviget.minesweeper.domain.GameGrid;
import com.deviget.minesweeper.domain.GameOverException;
import com.deviget.minesweeper.domain.ICell;
import com.deviget.minesweeper.domain.InvalidStateException;
import com.deviget.minesweeper.domain.OutOfGridException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GameGridTest {

	@Test
	public void givenAnIncompleteGridBuilder_whenItsBuilt_thenCannotCreateAGridWithInvalidState() {
		GameGrid.GridBuilder builder = GameGrid.GridBuilder.aGrid();

		assertThrows(InvalidStateException.class, builder::build);

		builder.withCells(Map.of());
		assertThrows(InvalidStateException.class, builder::build);

		builder.withHeight(1);
		assertThrows(InvalidStateException.class, builder::build);

		builder.withWidth(2);
		assertThrows(InvalidStateException.class, builder::build);

		builder.withHeight(null).withMines(2);
		assertThrows(InvalidStateException.class, builder::build);
	}

	@Test
	public void givenAGridBuilderWithMoreMinesThanGridDimensions_whenItsBuilt_thenCannotCreateAGridWithInvalidState() {
		GameGrid.GridBuilder builder = GameGrid.GridBuilder.aGrid()
				.withCells(Map.of())
				.withHeight(1)
				.withWidth(2)
				.withMines(4);

		assertThrows(InvalidStateException.class, builder::build);
	}

	@Test
	public void givenAGridBuilderWithNegativeMines_whenItsBuilt_thenCannotCreateAGridWithInvalidState() {
		GameGrid.GridBuilder builder = GameGrid.GridBuilder.aGrid()
				.withCells(Map.of())
				.withHeight(2)
				.withWidth(2)
				.withMines(-1);

		assertThrows(InvalidStateException.class, builder::build);
	}

	@Test
	public void givenAGridBuilderWithInvalidGridDimensions_whenItsBuilt_thenCannotCreateAGridWithInvalidState() {
		GameGrid.GridBuilder builder = GameGrid.GridBuilder.aGrid()
				.withCells(Map.of())
				.withHeight(-1)
				.withWidth(10)
				.withMines(4);

		assertThrows(InvalidStateException.class, builder::build);
	}

	@Test
	public void givenBothAGridAndAOutOfGridPosition_whenFlagTheCell_thenPositionIsOutOfGrid() {
		final int height = 4;
		final int width = 4;
		GameGrid grid = GameGrid.GridBuilder.aGrid()
				.withHeight(height)
				.withWidth(width)
				.withMines(4)
				.build();

		final CellPosition position = new CellPosition(6, 6);

		assertThrows(OutOfGridException.class, () -> grid.flagACell(position));
	}

	@Test
	public void givenBothAGridAndAOutOfGridPosition_whenUnflagTheCell_thenPositionIsOutOfGrid() {
		final int height = 4;
		final int width = 4;
		GameGrid grid = GameGrid.GridBuilder.aGrid()
				.withHeight(height)
				.withWidth(width)
				.withMines(4)
				.build();

		final CellPosition position = new CellPosition(6, 6);

		assertThrows(OutOfGridException.class, () -> grid.unflagACell(position));
	}

	@Test
	public void givenBothAGridAndAOutOfGridPosition_whenMarkTheCell_thenPositionIsOutOfGrid() {
		final int height = 4;
		final int width = 4;
		GameGrid grid = GameGrid.GridBuilder.aGrid()
				.withHeight(height)
				.withWidth(width)
				.withMines(4)
				.build();

		final CellPosition position = new CellPosition(6, 6);

		assertThrows(OutOfGridException.class, () -> grid.markACell(position));
	}

	@Test
	public void givenBothAGridAndAOutOfGridPosition_whenUnmarkTheCell_thenPositionIsOutOfGrid() {
		final int height = 4;
		final int width = 4;
		GameGrid grid = GameGrid.GridBuilder.aGrid()
				.withHeight(height)
				.withWidth(width)
				.withMines(4)
				.build();

		final CellPosition position = new CellPosition(6, 6);

		assertThrows(OutOfGridException.class, () -> grid.unmarkACell(position));
	}

	@Test
	public void givenBothAGridAndAOutOfGridPosition_whenUncoverTheCell_thenPositionIsOutOfGrid() {
		final int height = 4;
		final int width = 4;
		GameGrid grid = GameGrid.GridBuilder.aGrid()
				.withHeight(height)
				.withWidth(width)
				.withMines(4)
				.build();

		final CellPosition position = new CellPosition(6, 6);

		assertThrows(OutOfGridException.class, () -> grid.uncoverACell(position));
	}

	@Test
	public void givenAGrid_whenUncoverAnEmptyCell_thenAdjacentNotMineCellsAreUncoveredToo() {
		final int height = 10;
		final int width = 10;
		GameGrid grid = GameGrid.GridBuilder.aGrid()
				.withHeight(height)
				.withWidth(width)
				.withMines(4)
				.build();

		CellPosition emptyCell = grid.getCells().stream()
				.filter(c -> CellValue.EMPTY.equals(c.getValue()))
				.map(ICell::getPosition)
				.findAny().orElseThrow();

		long initialUncoveredCells = grid.getCells().stream()
				.filter(c -> CellStatus.UNCOVERED.equals(c.getStatus()))
				.count();

		assertEquals(0L, initialUncoveredCells);

		grid.uncoverACell(emptyCell);

		long currentUncoveredCells = grid.getCells().stream()
				.filter(c -> CellStatus.UNCOVERED.equals(c.getStatus()))
				.count();

		assertTrue(currentUncoveredCells > 1);
	}

	@Test
	public void givenAGrid_whenFlagMinesAndUncoverOtherCells_thenGameIsOver() {
		final int height = 10;
		final int width = 10;
		GameGrid grid = GameGrid.GridBuilder.aGrid()
				.withHeight(height)
				.withWidth(width)
				.withMines(4)
				.build();

		// flagging mines
		grid.getCells().stream()
				.filter(c -> CellValue.MINE.equals(c.getValue()))
				.map(ICell::getPosition)
				.forEach(grid::flagACell);

		// uncovering other cells
		assertThrows(GameOverException.class, () -> grid.getCells().stream()
				.filter(c -> !CellValue.MINE.equals(c.getValue()))
				.map(ICell::getPosition)
				.forEach(grid::uncoverACell));
	}

	@Test
	public void givenAGrid_whenUncoverOtherCells_thenGameIsOver() {
		final int height = 4;
		final int width = 4;
		GameGrid grid = GameGrid.GridBuilder.aGrid()
				.withHeight(height)
				.withWidth(width)
				.withMines(4)
				.build();

		// uncovering other cells
		assertThrows(GameOverException.class, () -> grid.getCells().stream()
				.filter(c -> !CellValue.MINE.equals(c.getValue()))
				.map(ICell::getPosition)
				.forEach(grid::uncoverACell));
	}
}
