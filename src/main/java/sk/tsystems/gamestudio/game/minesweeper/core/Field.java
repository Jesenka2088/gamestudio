package sk.tsystems.gamestudio.game.minesweeper.core;

import java.util.Random;

import sk.tsystems.gamestudio.game.minesweeper.core.Tile.State;



/**
 * Field represents playing field and game logic.
 */
public class Field {

	/**
	 * Playing field tiles.
	 */
	private final Tile[][] tiles;

	/**
	 * Field row count. Rows are indexed from 0 to (rowCount - 1).
	 */
	private final int rowCount;

	/**
	 * Column count. Columns are indexed from 0 to (columnCount - 1).
	 */
	private final int columnCount;

	/**
	 * Mine count.
	 */
	private final int mineCount;

	/**
	 * Game state.
	 */
	private GameState state = GameState.PLAYING;

	/**
	 * Constructor.
	 *
	 * @param rowCount    row count
	 * @param columnCount column count
	 * @param mineCount   mine count
	 */
	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];

		// generate the field content
		generate();
	}

	/**
	 * Opens tile at specified indeces.
	 *
	 * @param row    row number
	 * @param column column number
	 */
	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
//			tile.setState(Tile.State.OPEN);
			tiles[row][column].setState(Tile.State.OPEN);
			if (tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			} else if (tile instanceof Clue) {
				Clue clue = (Clue) tiles[row][column];
				if (clue.getValue() == 0) {
					openAdjacentTiles(row, column);
				}
				if (isSolved()) {
					state = GameState.SOLVED;
					return;
				}
			}
		}
	}

	/**
	 * Marks tile at specified indeces.
	 *
	 * @param row    row number
	 * @param column column number
	 */
	public void markTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.MARKED);
		} else if (tile.getState() == Tile.State.MARKED) {
			tile.setState(Tile.State.CLOSED);
		}
	}

	/**
	 * Generates playing field.
	 */
	private void generate() {
		int count = 0;
		Random r = new Random();
		while (count < mineCount) {
			int randomRow = r.nextInt(rowCount);
			int randomColumn = r.nextInt(columnCount);
			if (tiles[randomRow][randomColumn] == null) {
				tiles[randomRow][randomColumn] = new Mine();
				count++;
			}
		}
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == null) {
					tiles[row][column] = new Clue(countAdjacentMines(row, column));
				}
			}
		}
	}

	/**
	 * Returns true if game is solved, false otherwise.
	 *
	 * @return true if game is solved, false otherwise
	 */
	public boolean isSolved() {
		if ((rowCount * columnCount) - (getNumberOf(State.OPEN)) == (mineCount)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns number of adjacent mines for a tile at specified position in the
	 * field.
	 *
	 * @param row    row number.
	 * @param column column number.
	 * @return number of adjacent mines.
	 */
	private int countAdjacentMines(int row, int column) {
		int count = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Mine) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}

	private int getNumberOf(Tile.State state) {
		int count = 0;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column].getState() == state)
					count++;
			}
		}
		return count;
	}

	public int getRemainingMineCount() {
		int count;
		count = mineCount - getNumberOf(Tile.State.MARKED);
		return count;
	}

	private void openAdjacentTiles(int row, int column) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Clue) {
							Clue clue = (Clue) tiles[actRow][actColumn];
							if (clue.getValue() == 0) {
								openTile(actRow, actColumn);
							} else if (clue.getValue() == 1) {
								openTile(actRow, actColumn);
							} else if (clue.getValue() == 2) {
								openTile(actRow, actColumn);
							} else if (clue.getValue() == 3) {
								openTile(actRow, actColumn);
							} else if (clue.getValue() == 4) {
								openTile(actRow, actColumn);
							} else if (clue.getValue() == 5) {
								openTile(actRow, actColumn);
							} 
						}
					}
				}
			}
		}
	}

	public GameState getState() {
		return state;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	public int getScore() {
		long startMillis = System.currentTimeMillis();
		long longSeconds = (System.currentTimeMillis() - startMillis) / 1000;
		int seconds = (int) longSeconds;
		int score = rowCount*columnCount*10 - seconds;
		return score;
	}
}
