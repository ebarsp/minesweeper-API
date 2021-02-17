package com.deviget.minesweeper.unit;

import com.deviget.minesweeper.domain.Cell;
import com.deviget.minesweeper.domain.CellPosition;
import com.deviget.minesweeper.domain.CellStatus;
import com.deviget.minesweeper.domain.CellValue;
import com.deviget.minesweeper.domain.GameOverException;
import com.deviget.minesweeper.domain.InvalidActionException;
import com.deviget.minesweeper.domain.InvalidStateException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CellTest {

	@Test
	public void givenAnIncompleteCell_whenItsInstanced_thenCannotCreateACellWithInvalidState() {
		assertThrows(InvalidStateException.class, () -> new Cell(null, null, null));
		assertThrows(InvalidStateException.class, () -> new Cell(null, CellValue.MINE, CellStatus.MARKED));
		assertThrows(InvalidStateException.class, () -> new Cell(new CellPosition(1, 2), null, CellStatus.MARKED));
		assertThrows(InvalidStateException.class, () -> new Cell(new CellPosition(1, 2), CellValue.MINE, null));
	}

	@Test
	public void givenACell_whenTryMarkIt_thenCellIsMarked() {
		Cell cell = new Cell(new CellPosition(1, 2), CellValue.MINE, CellStatus.COVERED);
		cell.mark();

		assertEquals(CellStatus.MARKED, cell.getStatus());
	}

	@Test
	public void givenBothAnUncoveredOrMarkedCell_whenTryMarkIt_thenInvalidAction() {
		Cell cell = new Cell(new CellPosition(1, 2), CellValue.TWO, CellStatus.UNCOVERED);

		assertThrows(InvalidActionException.class, cell::mark);

		cell = new Cell(new CellPosition(1, 2), CellValue.TWO, CellStatus.MARKED);

		assertThrows(InvalidActionException.class, cell::mark);
	}

	@Test
	public void givenAMarkedCell_whenTryUnmarkIt_thenCellIsCovered() {
		Cell cell = new Cell(new CellPosition(1, 2), CellValue.EMPTY, CellStatus.MARKED);
		cell.unmark();

		assertEquals(CellStatus.COVERED, cell.getStatus());
	}

	@Test
	public void givenANonMarkedCell_whenTryUnmarkIt_thenInvalidAction() {
		Cell cell = new Cell(new CellPosition(1, 2), CellValue.EMPTY, CellStatus.UNCOVERED);

		assertThrows(InvalidActionException.class, cell::unmark);
	}

	@Test
	public void givenACell_whenTryFlagIt_thenCellIsFlagged() {
		Cell cell = new Cell(new CellPosition(1, 2), CellValue.MINE, CellStatus.COVERED);
		cell.flag();

		assertEquals(CellStatus.FLAGGED, cell.getStatus());
	}

	@Test
	public void givenBothAnUncoveredOrFlaggedCell_whenTryFlagIt_thenInvalidAction() {
		Cell cell = new Cell(new CellPosition(1, 2), CellValue.TWO, CellStatus.UNCOVERED);

		assertThrows(InvalidActionException.class, cell::mark);

		cell = new Cell(new CellPosition(1, 2), CellValue.TWO, CellStatus.FLAGGED);

		assertThrows(InvalidActionException.class, cell::flag);
	}

	@Test
	public void givenAFlaggedCell_whenTryUnflagIt_thenCellIsCovered() {
		Cell cell = new Cell(new CellPosition(1, 2), CellValue.ONE, CellStatus.FLAGGED);
		cell.unflag();

		assertEquals(CellStatus.COVERED, cell.getStatus());
	}

	@Test
	public void givenANonFlaggedCell_whenTryUnflagIt_thenInvalidAction() {
		Cell cell = new Cell(new CellPosition(1, 2), CellValue.ONE, CellStatus.MARKED);

		assertThrows(InvalidActionException.class, cell::unflag);
	}

	@Test
	public void givenACell_whenTryUncoverIt_thenCellIsUncovered() {
		Cell cell = new Cell(new CellPosition(1, 2), CellValue.EMPTY, CellStatus.COVERED);
		cell.uncover();

		assertEquals(CellStatus.UNCOVERED, cell.getStatus());
	}

	@Test
	public void givenAMinedCell_whenUncoverIt_thenGameIsOver() {
		Cell cell = new Cell(new CellPosition(1, 2), CellValue.MINE, CellStatus.COVERED);

		assertThrows(GameOverException.class, cell::uncover);
	}
}
