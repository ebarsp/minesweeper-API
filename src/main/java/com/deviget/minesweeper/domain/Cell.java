package com.deviget.minesweeper.domain;

public class Cell implements ICell {
	private final CellPosition position;
	private final CellValue value;
	private CellStatus status;

	public Cell(CellPosition position, CellValue value, CellStatus status) {
		if (position == null || status == null || value == null) {
			throw new InvalidStateException("There are missed arguments");
		}
		this.position = position;
		this.value = value;
		this.status = status;
	}

	@Override
	public CellPosition getPosition() {
		return position;
	}

	@Override
	public CellValue getValue() {
		return value;
	}

	@Override
	public CellStatus getStatus() {
		return status;
	}

	public void mark() {
		if (isNotCovered() && isNotFlagged()) {
			throw new InvalidActionException();
		}
		status = CellStatus.MARKED;
	}

	public void unmark() {
		if (isNotMarked()) {
			throw new InvalidActionException();
		}
		status = CellStatus.COVERED;
	}

	public void flag() {
		if (isNotCovered() && isNotMarked()) {
			throw new InvalidActionException();
		}
		status = CellStatus.FLAGGED;
	}

	public void unflag() {
		if (isNotFlagged()) {
			throw new InvalidActionException();
		}
		status = CellStatus.COVERED;
	}

	public void uncover() {
		if (isAMine()) {
			status = CellStatus.UNCOVERED;
			throw new GameOverException(GameStatus.LOST);
		}
		if (isNotFlagged() && isNotUncovered()) {
			status = CellStatus.UNCOVERED;
		}
	}

	public boolean isEmpty() {
		return CellValue.EMPTY.equals(value);
	}

	private boolean isAMine() {
		return CellValue.MINE.equals(value);
	}

	private boolean isNotUncovered() {
		return !CellStatus.UNCOVERED.equals(status);
	}

	private boolean isNotFlagged() {
		return !CellStatus.FLAGGED.equals(status);
	}

	private boolean isNotCovered() {
		return !CellStatus.COVERED.equals(status);
	}

	private boolean isNotMarked() {
		return !CellStatus.MARKED.equals(status);
	}
}
