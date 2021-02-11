package com.deviget.minesweeper.unit;

import com.deviget.minesweeper.domain.Game;
import com.deviget.minesweeper.domain.GameGrid;
import com.deviget.minesweeper.domain.GameStatus;
import com.deviget.minesweeper.domain.InvalidStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GameTest {
	private UUID gameId;
	private GameGrid grid;

	@BeforeEach
	public void setUp() {
		gameId = UUID.randomUUID();
		grid = GameGrid.GridBuilder.aGrid()
				.withX(1)
				.withY(1)
				.withMines(1)
				.build();
	}

	@Test
	public void whenAnIncompleteGameIsBuilt_thenCannotCreateAGameWithInvalidState() {
		Game.GameBuilder builder = Game.GameBuilder.aGame()
				.withId(gameId);

		assertThrows(InvalidStateException.class, builder::build);

		builder.withDuration(Duration.ZERO);
		assertThrows(InvalidStateException.class, builder::build);

		builder.withStatus(GameStatus.ONGOING);
		assertThrows(InvalidStateException.class, builder::build);

		builder.withLastUpdateTime(LocalDateTime.MIN);
		assertThrows(InvalidStateException.class, builder::build);

		builder.withId(null).withGrid(grid);
		assertThrows(InvalidStateException.class, builder::build);
	}

	@Test
	public void whenAGameStarts_thenTheDurationOfTheGameBegins() throws InterruptedException {
		Game game = Game.GameBuilder.aGame()
				.withId(gameId)
				.withStatus(GameStatus.ONGOING)
				.withDuration(Duration.ZERO)
				.withLastUpdateTime(LocalDateTime.now())
				.withGrid(grid)
				.build();

		//keeps time running
		TimeUnit.SECONDS.sleep(2);
		game.recalculateDuration();

		assertEquals("0:00:02", game.getDurationString());
	}
}
