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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GameGridTest {

	@Test
	public void givenAnIncompleteGrid_whenItsInstanced_thenCannotCreateAGridWithInvalidState() {
		assertThrows(InvalidStateException.class, () -> new GameGrid(null, null, null));
		assertThrows(InvalidStateException.class, () -> new GameGrid(null, 1, 1));
		assertThrows(InvalidStateException.class, () -> new GameGrid(1, null, 1));
		assertThrows(InvalidStateException.class, () -> new GameGrid(1, 1, null));
	}

	@Test
	public void givenAGridWithMoreMinesThanGridDimensions_whenItsInstanced_thenCannotCreateAGridWithInvalidState() {
		assertThrows(InvalidStateException.class, () -> new GameGrid(2, 2, 5));
	}

	@Test
	public void givenAGridWithNegativeMines_whenItsInstanced_thenCannotCreateAGridWithInvalidState() {
		assertThrows(InvalidStateException.class, () -> new GameGrid(2, 2, -1));
	}

	@Test
	public void givenAGridWithInvalidGridDimensions_whenItsInstanced_thenCannotCreateAGridWithInvalidState() {
		assertThrows(InvalidStateException.class, () -> new GameGrid(2, -2, 1));
		assertThrows(InvalidStateException.class, () -> new GameGrid(-2, 2, 1));
		assertThrows(InvalidStateException.class, () -> new GameGrid(-2, -2, 1));
	}

	@Test
	public void givenBothAGridAndAOutOfGridPosition_whenFlagTheCell_thenPositionIsOutOfGrid() {
		GameGrid grid = new GameGrid(4, 4, 1);

		assertThrows(OutOfGridException.class, () -> grid.flagACell(new CellPosition(6, 6)));
	}

	@Test
	public void givenBothAGridAndAOutOfGridPosition_whenUnflagTheCell_thenPositionIsOutOfGrid() {
		GameGrid grid = new GameGrid(4, 4, 1);

		assertThrows(OutOfGridException.class, () -> grid.unflagACell(new CellPosition(6, 6)));
	}

	@Test
	public void givenBothAGridAndAOutOfGridPosition_whenMarkTheCell_thenPositionIsOutOfGrid() {
		GameGrid grid = new GameGrid(4, 4, 1);

		assertThrows(OutOfGridException.class, () -> grid.markACell(new CellPosition(6, 6)));
	}

	@Test
	public void givenBothAGridAndAOutOfGridPosition_whenUnmarkTheCell_thenPositionIsOutOfGrid() {
		GameGrid grid = new GameGrid(4, 4, 1);

		assertThrows(OutOfGridException.class, () -> grid.unmarkACell(new CellPosition(6, 6)));
	}

	@Test
	public void givenBothAGridAndAOutOfGridPosition_whenUncoverTheCell_thenPositionIsOutOfGrid() {
		GameGrid grid = new GameGrid(4, 4, 1);

		assertThrows(OutOfGridException.class, () -> grid.uncoverACell(new CellPosition(6, 6)));
	}

	@Test
	public void givenAGrid_whenUncoverAnEmptyCell_thenAdjacentNotMineCellsAreUncoveredToo() {
		GameGrid grid = new GameGrid(10, 10, 4);

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
		GameGrid grid = new GameGrid(10, 10, 4);

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
		GameGrid grid = new GameGrid(4, 4, 1);

		// uncovering other cells
		assertThrows(GameOverException.class, () -> grid.getCells().stream()
				.filter(c -> !CellValue.MINE.equals(c.getValue()))
				.map(ICell::getPosition)
				.forEach(grid::uncoverACell));
	}
}
