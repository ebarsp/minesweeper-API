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
		grid = new GameGrid(1, 1, 1);
	}

	@Test
	public void givenAnIncompleteGame_whenItsInstanced_thenCannotCreateAGameWithInvalidState() {
		assertThrows(InvalidStateException.class, () -> new Game(null, null, null, null, null));
		assertThrows(InvalidStateException.class, () -> new Game(null, grid, GameStatus.ONGOING, Duration.ZERO, LocalDateTime.MIN));
		assertThrows(InvalidStateException.class, () -> new Game(UUID.randomUUID(), null, GameStatus.ONGOING, Duration.ZERO, LocalDateTime.MIN));
		assertThrows(InvalidStateException.class, () -> new Game(UUID.randomUUID(), grid, null, Duration.ZERO, LocalDateTime.MIN));
		assertThrows(InvalidStateException.class, () -> new Game(UUID.randomUUID(), grid, GameStatus.ONGOING, null, LocalDateTime.MIN));
		assertThrows(InvalidStateException.class, () -> new Game(UUID.randomUUID(), grid, GameStatus.ONGOING, Duration.ZERO, null));
	}

	@Test
	public void givenAnOngoingGame_whenPausedItAfterTwoSeconds_thenBothGameStatusAndDurationAreChanged() throws InterruptedException, InvalidActionException {
		Game game = new Game(gameId, grid, GameStatus.ONGOING, Duration.ZERO, LocalDateTime.now());

		TimeUnit.SECONDS.sleep(2);
		game.pause();

		assertEquals(GameStatus.PAUSED, game.getStatus());
		assertEquals("0:00:02", game.getDurationString());
	}

	@Test
	public void givenAPausedGame_whenTryPauseIt_thenItsAnInvalidAction() {
		Game game = new Game(gameId, grid, GameStatus.PAUSED, Duration.ZERO, LocalDateTime.now());

		assertThrows(InvalidActionException.class, game::pause);
	}

	@Test
	public void givenAGameOver_whenTryPauseIt_thenItsAnInvalidAction() {
		Game game = new Game(gameId, grid, GameStatus.LOST, Duration.ZERO, LocalDateTime.now());

		assertThrows(InvalidActionException.class, game::pause);
	}

	@Test
	public void givenAPausedGame_whenUnpauseIt_thenBothGameStatusAndLastUpdateAreChanged() throws InvalidActionException, InterruptedException {
		Game game = new Game(gameId, grid, GameStatus.PAUSED, Duration.ZERO, LocalDateTime.now());
		LocalDateTime original = game.getLastUpdateTime();

		TimeUnit.MILLISECONDS.sleep(1);
		game.unpause();

		assertEquals(GameStatus.ONGOING, game.getStatus());
		assertNotEquals(original, game.getLastUpdateTime());
	}

	@Test
	public void givenAnOngoingGame_whenTryUnpauseIt_thenItsAnInvalidAction() {
		Game game = new Game(gameId, grid, GameStatus.ONGOING, Duration.ZERO, LocalDateTime.now());

		assertThrows(InvalidActionException.class, game::unpause);
	}

	@Test
	public void givenAGameOver_whenTryUnpauseIt_thenItsAnInvalidAction() {
		Game game = new Game(gameId, grid, GameStatus.LOST, Duration.ZERO, LocalDateTime.now());

		assertThrows(InvalidActionException.class, game::unpause);
	}

	@Test
	public void givenAnOngoingGame_whenPausedItResumeItAndGettingDuration_thenDurationIsCorrect() throws InterruptedException, InvalidActionException {
		Game game = new Game(gameId, grid, GameStatus.ONGOING, Duration.ZERO, LocalDateTime.now());

		TimeUnit.SECONDS.sleep(2);
		game.pause();

		TimeUnit.SECONDS.sleep(2);
		game.unpause();

		TimeUnit.SECONDS.sleep(3);

		assertEquals(GameStatus.ONGOING, game.getStatus());
		assertEquals("0:00:05", game.getDurationString());
	}
}
