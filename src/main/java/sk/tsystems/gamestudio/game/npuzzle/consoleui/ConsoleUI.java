package sk.tsystems.gamestudio.game.npuzzle.consoleui;

import java.util.Scanner;

import sk.tsystems.gamestudio.consoleui.Menu;
import sk.tsystems.gamestudio.game.npuzzle.core.Field;
import sk.tsystems.gamestudio.game.npuzzle.core.Tile;

public class ConsoleUI {
	private Scanner scanner = new Scanner(System.in);

	private Field field;

	public void play(Field field) {
		this.field = field;
		do {
			show();
			processInput();
		} while (!field.isSolved());

		show();
		System.out.println("You won!");
		Menu menu = new Menu();
		menu.showMenu();
	}

	private void show() {
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				if (tile != null)
					System.out.printf(" %2d", tile.getValue());
				else
					System.out.print("   ");
			}
			System.out.println();
		}
	}

	private void processInput() {
		System.out.print("Enter tile to move: , to EXIT the game please enter X.");

		try {
			String line = scanner.nextLine();
			if ("X".equals(line.toUpperCase().trim())) {
				Menu menu = new Menu();
				menu.showMenu();
			}
			int tileNumber = Integer.parseInt(line);
			field.move(tileNumber);

		} catch (NumberFormatException e) {
			System.err.println("Invalid tile number!");
		}
	}
}
