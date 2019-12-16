package sk.tsystems.gamestudio.game.minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sk.tsystems.gamestudio.consoleui.Menu;
import sk.tsystems.gamestudio.game.minesweeper.UserInterface;
import sk.tsystems.gamestudio.game.minesweeper.core.Clue;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.game.minesweeper.core.Mine;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile.State;



/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
	/** Playing field. */
	private Field field;

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Starts the game.
	 * 
	 * @param field field of mines and clues
	 */
	@Override
	public void newGameStarted(Field field) {
		this.field = field;
		do {
			update();
			
			if (field.getState() == GameState.SOLVED) {
				System.out.println("Congratulations!");
				Menu menu = new Menu();
				menu.showMenu();
//				System.exit(0);
			} else if (field.getState() == GameState.FAILED) {
				System.out.println("You hit a MINE! GAME OVER! ...but dont worry, you can try again :) ");
				Menu menu = new Menu();
				menu.showMenu();
//				System.exit(0);
				}
			processInput();
		} while (true);
	}

	/**
	 * Updates user interface - prints the field.
	 */
	@Override
	public void update() {
		System.out.println("Remaining Mines counter:  "+field.getRemainingMineCount());
		System.out.print("  ");
		for (int column = 0; column < field.getColumnCount(); column++) {
			System.out.printf("%d ", column);
		}
		for (int row = 0; row < field.getRowCount(); row++) {
			System.out.println();
			System.out.printf("%c ", 'A' + row);
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column).getState() == State.OPEN && field.getTile(row, column) instanceof Mine) {
					System.out.printf("%c ", 'X');
				}
				if (field.getTile(row, column).getState() == State.OPEN && field.getTile(row, column) instanceof Clue) {
					Clue clue = (Clue)field.getTile(row, column);
					System.out.printf("%d ", clue.getValue());
				}
				if (field.getTile(row, column).getState() == State.MARKED) {
					System.out.printf("%c ", 'M');
				}
				if (field.getTile(row, column).getState() == State.CLOSED) {
					System.out.printf("%c ", '-');
				}
			}
			
		}
		System.out.println();
	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */
	private void processInput() {
		System.out.println("Input example :  X to cancel the game, MA1 to mark tile in the position A1, OB4 to open a tile in the position B4.");
		String usersInput = readLine().toUpperCase();
		try {
			handleInput(usersInput);
		}catch (WrongFormatException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	private void handleInput (String input)  throws WrongFormatException{
		Pattern pattern = Pattern.compile("(O|M)([A-I])([0-8])");
		Matcher matcher = pattern.matcher(input);
		if(matcher.matches()) {
			char userOption = matcher.group(1).charAt(0);
			char userRow = matcher.group(2).charAt(0);
			int row = userRow - 'A';
			int column = Integer.parseInt(matcher.group(3));
			if (userOption == 'O') {
				field.openTile(row, column);
			}else if (userOption == 'M') {
				field.markTile(row, column);
			}
		} else if (input.equals("X")) {
			System.out.println("Game exit!");
			Menu menu = new Menu();
			menu.showMenu();
//			System.exit(0);
		} else {
			throw new WrongFormatException("You have enterred wrong input.");
		}
	}
}
