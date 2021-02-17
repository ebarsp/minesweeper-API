package com.deviget.minesweeper.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameGrid implements IGameGrid {
	private final Integer height;
	private final Integer width;
	private final Integer mines;
	private final Map<CellPosition, Cell> cells;
	private final Set<CellPosition> visited;

	private GameGrid(GridBuilder builder) {
		height = builder.height;
		width = builder.width;
		mines = builder.mines;
		cells = Objects.isNull(builder.cells)
				? getGridCells(builder.height, builder.width, builder.mines) : builder.cells;
		visited = Objects.isNull(builder.visited) ? new HashSet<>() : builder.visited;
	}

	@Override
	public Integer getHeight() {
		return height;
	}

	@Override
	public Integer getWidth() {
		return width;
	}

	@Override
	public Integer getMines() {
		return mines;
	}

	@Override
	public List<ICell> getCells() {
		return new ArrayList<>(cells.values());
	}

	public void markACell(CellPosition position) {
		validatePosition(position);
		cells.get(position).mark();
	}

	public void unmarkACell(CellPosition position) {
		validatePosition(position);
		cells.get(position).unmark();
	}

	public void flagACell(CellPosition position) {
		validatePosition(position);
		cells.get(position).flag();
	}

	public void unflagACell(CellPosition position) {
		validatePosition(position);
		cells.get(position).unflag();
	}

	public void uncoverACell(CellPosition position) {
		validatePosition(position);
		visited.add(position);
		final Cell cell = cells.get(position);
		if (cell.isEmpty()) {
			List<CellPosition> adjacentPositions = position.getAdjacentPositions();
			adjacentPositions.stream()
					.filter(cells::containsKey)
					.filter(p -> !visited.contains(p))
					.map(cells::get)
					.filter(c -> !c.isAMine())
					.map(Cell::getPosition)
					.forEach(this::uncoverACell);
		}
		cell.uncover();
		if (isGameOver()) {
			throw new GameOverException(GameStatus.WON);
		}
	}

	private boolean isGameOver() {
		return visited.size() + mines == cells.size();
	}

	private void validatePosition(CellPosition position) {
		if (!cells.containsKey(position)) {
			throw new OutOfGridException(String.format("Position %s does not belong to the grid.", position));
		}
	}

	private Map<CellPosition, Cell> getGridCells(Integer height, Integer width, Integer mines) {
		Map<CellPosition, Cell> cells = new HashMap<>();
		// List all grid's positions
		List<CellPosition> positions = getAllPositions(height, width);
		// Decide mine places by positions
		Set<CellPosition> positionsToMine = getMinePositions(positions, mines);
		// Get the adjacent elements to the mines
		Map<CellPosition, Integer> adjacentPositions = getAdjacentPositionsToTheMines(positions, positionsToMine);
		// Create a CellValue mapper by cardinal positions in the enum
		CellValue[] valueMapper = CellValue.values();
		// Add the adjacent elements into cells map
		adjacentPositions
				.keySet().stream()
				.map(p -> new Cell(p, valueMapper[adjacentPositions.get(p)], CellStatus.COVERED))
				.forEach(c -> cells.put(c.getPosition(), c));
		// Add the mines into cells map
		positionsToMine.stream()
				.map(p -> new Cell(p, CellValue.MINE, CellStatus.COVERED))
				.forEach(c -> cells.put(c.getPosition(), c));
		// Completed the cells map with the empty cells
		positions.stream()
				.filter(p -> !positionsToMine.contains(p))
				.filter(p -> !adjacentPositions.containsKey(p))
				.map(p -> new Cell(p, CellValue.EMPTY, CellStatus.COVERED))
				.forEach(c -> cells.put(c.getPosition(), c));
		return cells;
	}

	private static Map<CellPosition, Integer> getAdjacentPositionsToTheMines(List<CellPosition> positions, Set<CellPosition> positionsToMine) {
		return positionsToMine.stream()
				.flatMap(p -> p.getAdjacentPositions().stream())
				.filter(positions::contains)
				.collect(Collectors.toMap(Function.identity(), v -> 1, Integer::sum));
	}

	private static List<CellPosition> getAllPositions(Integer height, Integer width) {
		List<CellPosition> positions = new ArrayList<>();
		for (int y = 1; y <= height; y++) {
			for (int x = 1; x <= width; x++) {
				positions.add(new CellPosition(x, y));
			}
		}
		return positions;
	}

	private static Set<CellPosition> getMinePositions(List<CellPosition> positions, Integer mines) {
		Set<CellPosition> minedPositions = new HashSet<>();
		Random random = new Random();
		while (minedPositions.size() < mines) {
			final CellPosition position = positions.get(random.nextInt(positions.size()));
			if (minedPositions.contains(position)) {
				continue;
			}
			minedPositions.add(position);
		}
		return minedPositions;
	}

	public static final class GridBuilder {
		private Map<CellPosition, Cell> cells;
		private Set<CellPosition> visited;
		private Integer height;
		private Integer width;
		private Integer mines;

		private GridBuilder() {
		}

		public static GridBuilder aGrid() {
			return new GridBuilder();
		}

		public GridBuilder withCells(Map<CellPosition, Cell> cells) {
			this.cells = cells;
			return this;
		}

		public GridBuilder withVisited(Set<CellPosition> visited) {
			this.visited = visited;
			return this;
		}

		public GridBuilder withHeight(Integer height) {
			this.height = height;
			return this;
		}

		public GridBuilder withWidth(Integer width) {
			this.width = width;
			return this;
		}

		public GridBuilder withMines(Integer mines) {
			this.mines = mines;
			return this;
		}

		public GameGrid build() {
			if (height == null || width == null || mines == null) {
				throw new InvalidStateException("There are missed arguments");
			}
			if (height < 1 || width < 1 || mines < 1) {
				throw new InvalidStateException("Both grid's dimensions and mines should be higher than zero");
			}
			if (mines > height * width) {
				throw new InvalidStateException("Mines shouldn't be higher than grid's dimensions");
			}
			return new GameGrid(this);
		}
	}
}
