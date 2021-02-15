package com.deviget.minesweeper.unit;

import com.deviget.minesweeper.domain.Game;
import com.deviget.minesweeper.domain.GameGrid;
import com.deviget.minesweeper.domain.GameStatus;
import com.deviget.minesweeper.domain.InvalidActionException;
import com.deviget.minesweeper.domain.InvalidStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
	public void givenAnIncompleteGame_whenItsBuilt_thenCannotCreateAGameWithInvalidState() {
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
	public void givenAnGameStartedGame_whenRecalculateDurationAfterTwoSeconds_thenBothDurationAndLastUpdateAreChanged() throws InterruptedException {
		Game game = Game.GameBuilder.aGame()
				.withId(gameId)
				.withStatus(GameStatus.ONGOING)
				.withDuration(Duration.ZERO)
				.withLastUpdateTime(LocalDateTime.now())
				.withGrid(grid)
				.build();

		LocalDateTime original = game.getLastUpdateTime();

		TimeUnit.SECONDS.sleep(2);
		game.recalculateDuration();

		assertEquals("0:00:02", game.getDurationString());
		assertNotEquals(original, game.getLastUpdateTime());
	}

	@Test
	public void givenAnOngoingGame_whenTryPausedItAfterTwoSeconds_thenBothGameStatusAndDurationAreChanged() throws InterruptedException {
		Game game = Game.GameBuilder.aGame()
				.withId(gameId)
				.withStatus(GameStatus.ONGOING)
				.withDuration(Duration.ZERO)
				.withLastUpdateTime(LocalDateTime.now())
				.withGrid(grid)
				.build();

		TimeUnit.SECONDS.sleep(2);
		game.pause();

		assertEquals(GameStatus.PAUSED, game.getStatus());
		assertEquals("0:00:02", game.getDurationString());
	}

	@Test
	public void givenAPausedGame_whenTryPauseIt_thenItsAnInvalidAction() {
		Game game = Game.GameBuilder.aGame()
				.withId(gameId)
				.withStatus(GameStatus.PAUSED)
				.withDuration(Duration.ZERO)
				.withLastUpdateTime(LocalDateTime.now())
				.withGrid(grid)
				.build();
		assertThrows(InvalidActionException.class, game::pause);
	}

	@Test
	public void givenAGameOver_whenTryPauseIt_thenItsAnInvalidAction() {
		Game game = Game.GameBuilder.aGame()
				.withId(gameId)
				.withStatus(GameStatus.ENDED)
				.withDuration(Duration.ZERO)
				.withLastUpdateTime(LocalDateTime.now())
				.withGrid(grid)
				.build();
		assertThrows(InvalidActionException.class, game::pause);
	}

	@Test
	public void givenAPausedGame_whenTryUnpauseIt_thenBothGameStatusAndLastUpdateAreChanged() {
		Game game = Game.GameBuilder.aGame()
				.withId(gameId)
				.withStatus(GameStatus.PAUSED)
				.withDuration(Duration.ZERO)
				.withLastUpdateTime(LocalDateTime.now())
				.withGrid(grid)
				.build();

		LocalDateTime original = game.getLastUpdateTime();

		game.unpause();

		assertEquals(GameStatus.ONGOING, game.getStatus());
		assertNotEquals(original, game.getLastUpdateTime());
	}

	@Test
	public void givenAnOngoingGame_whenTryUnpauseIt_thenItsAnInvalidAction() {
		Game game = Game.GameBuilder.aGame()
				.withId(gameId)
				.withStatus(GameStatus.ONGOING)
				.withDuration(Duration.ZERO)
				.withLastUpdateTime(LocalDateTime.now())
				.withGrid(grid)
				.build();
		assertThrows(InvalidActionException.class, game::unpause);
	}

	@Test
	public void givenAGameOver_whenTryUnpauseIt_thenItsAnInvalidAction() {
		Game game = Game.GameBuilder.aGame()
				.withId(gameId)
				.withStatus(GameStatus.ENDED)
				.withDuration(Duration.ZERO)
				.withLastUpdateTime(LocalDateTime.now())
				.withGrid(grid)
				.build();
		assertThrows(InvalidActionException.class, game::unpause);
	}
}
