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
	public void whenCreateAGame_thenTheGameIsCorrectlyCreated() {
		final IGame game = instance.create(grid);

		assertNotNull(game.getId());
		assertEquals(grid, game.getGrid());
		assertEquals(GameStatus.ONGOING, game.getStatus());
		assertEquals("0:00:00", game.getDurationString());
	}

	@Test
	public void whenCreateAGame_thenThereWasARepositoryProblem() {
		doThrow(RepositoryException.class).when(repository).save(any());

		assertThrows(RepositoryException.class, () -> instance.create(grid));
	}

	@Test
	public void whenGetAnExistentGame_thenTheGameIsAcquired() {
		final IGame expected = instance.create(grid);

		when(repository.get(expected.getId())).thenReturn(Optional.of((Game) expected));

		assertEquals(expected, instance.get(expected.getId()));
	}

	@Test
	public void whenGetANonExistentGame_thenGameNotFound() {
		final UUID gameId = UUID.randomUUID();

		when(repository.get(gameId)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> instance.get(gameId));
	}

	@Test
	public void whenGetAGame_thenThereWasARepositoryProblem() {
		final UUID gameId = UUID.randomUUID();

		when(repository.get(gameId)).thenThrow(RepositoryException.class);

		assertThrows(RepositoryException.class, () -> instance.get(gameId));
	}

	private GameGrid getGrid() {
		return GameGrid.GridBuilder.aGrid()
				.withX(10)
				.withY(10)
				.withMines(3)
				.build();
	}
}
