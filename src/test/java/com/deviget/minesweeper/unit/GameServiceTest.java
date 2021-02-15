package com.deviget.minesweeper.unit;

import com.deviget.minesweeper.application.GameRepository;
import com.deviget.minesweeper.application.GameService;
import com.deviget.minesweeper.domain.Game;
import com.deviget.minesweeper.domain.GameGrid;
import com.deviget.minesweeper.domain.GameStatus;
import com.deviget.minesweeper.domain.IGame;
import com.deviget.minesweeper.domain.NotFoundException;
import com.deviget.minesweeper.domain.RepositoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GameServiceTest {
	private GameService instance;
	private GameRepository repository;
	private GameGrid grid;

	@BeforeEach
	public void setUp() {
		repository = mock(GameRepository.class);
		instance = new GameService(repository);
		grid = getGrid();
	}

	@Test
	public void givenAGrid_whenCreateAGame_thenTheGameIsCorrectlyCreated() {
		final IGame game = instance.create(grid);

		assertNotNull(game.getId());
		assertEquals(grid, game.getGrid());
		assertEquals(GameStatus.ONGOING, game.getStatus());
		assertEquals("0:00:00", game.getDurationString());
	}

	@Test
	public void givenAGrid_whenCreateAGame_thenThereIsARepositoryProblem() {
		doThrow(RepositoryException.class).when(repository).save(any());

		assertThrows(RepositoryException.class, () -> instance.create(grid));
	}

	@Test
	public void givenAnExistentGame_whenGetIt_thenTheGameIsFound() {
		final IGame existent = instance.create(grid);

		when(repository.get(existent.getId())).thenReturn(Optional.of((Game) existent));

		assertEquals(existent, instance.get(existent.getId()));
	}

	@Test
	public void givenANonExistentGame_whenGetIt_thenGameIsNotFound() {
		final UUID gameId = UUID.randomUUID();

		when(repository.get(gameId)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> instance.get(gameId));
	}

	@Test
	public void givenAnExistentGame_whenGetIt_thenThereIsARepositoryProblem() {
		final UUID gameId = UUID.randomUUID();

		when(repository.get(gameId)).thenThrow(RepositoryException.class);

		assertThrows(RepositoryException.class, () -> instance.get(gameId));
	}

	@Test
	public void givenAnOngoingGame_whenPauseIt_thenGameIsPaused() {
		final IGame existent = instance.create(grid);

		when(repository.get(existent.getId())).thenReturn(Optional.of((Game) existent));

		final IGame paused = instance.pause(existent.getId());

		assertEquals(GameStatus.PAUSED, paused.getStatus());
	}

	@Test
	public void givenANonExistentGame_whenPauseIt_thenGameIsNotFound() {
		final UUID gameId = UUID.randomUUID();

		when(repository.get(gameId)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> instance.pause(gameId));
	}


	@Test
	public void givenAPausedGame_whenUnpauseIt_thenGameIsOngoing() {
		final IGame existent = instance.create(grid);

		when(repository.get(existent.getId())).thenReturn(Optional.of((Game) existent));

		final IGame paused = instance.pause(existent.getId());

		assertEquals(GameStatus.PAUSED, paused.getStatus());

		final IGame unpaused = instance.unpause(existent.getId());

		assertEquals(GameStatus.ONGOING, unpaused.getStatus());
	}

	@Test
	public void givenANonExistentGame_whenUnpauseIt_thenGameIsNotFound() {
		final UUID gameId = UUID.randomUUID();

		when(repository.get(gameId)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> instance.unpause(gameId));
	}

	private GameGrid getGrid() {
		return GameGrid.GridBuilder.aGrid()
				.withX(10)
				.withY(10)
				.withMines(3)
				.build();
	}
}
