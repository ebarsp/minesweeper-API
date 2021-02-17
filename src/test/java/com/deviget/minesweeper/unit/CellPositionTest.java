package com.deviget.minesweeper.unit;

import com.deviget.minesweeper.domain.CellPosition;
import com.deviget.minesweeper.domain.InvalidStateException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CellPositionTest {

	@Test
	public void givenAnIncompletePosition_whenItsInstanced_thenCannotCreateAPositionWithInvalidState() {
		assertThrows(InvalidStateException.class, () -> new CellPosition( null, null));
		assertThrows(InvalidStateException.class, () -> new CellPosition( 1, null));
		assertThrows(InvalidStateException.class, () -> new CellPosition( null, 1));
	}

	@Test
	public void givenAPosition_whenGetAdjacentPositions_thenTheyAreCorrectlyListed() {
		CellPosition position = new CellPosition(2, 2);

		List<CellPosition> positions = position.getAdjacentPositions();
		assertEquals("(1,1)", positions.get(0).toString());
		assertEquals("(1,2)", positions.get(1).toString());
		assertEquals("(1,3)", positions.get(2).toString());
		assertEquals("(2,1)", positions.get(3).toString());
		assertEquals("(2,3)", positions.get(4).toString());
		assertEquals("(3,1)", positions.get(5).toString());
		assertEquals("(3,2)", positions.get(6).toString());
		assertEquals("(3,3)", positions.get(7).toString());
	}
}
