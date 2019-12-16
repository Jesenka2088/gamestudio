package sk.tsystems.gamestudio.consoleui;

import java.util.Scanner;

import sk.tsystems.gamestudio.game.guessnumber.core.GuessTheNumberMain;
import sk.tsystems.gamestudio.game.minesweeper.Minesweeper;
import sk.tsystems.gamestudio.game.npuzzle.Puzzle;


public class Menu {

	Scanner scanner = new Scanner(System.in);

	public void showMenu() {
		System.out.println("***WELCOME IN GAME STUDIO***");
		System.out.println(" ");
		System.out.println("CHOOSE YOUR GAME: ");
		System.out.println("1. MINESWEEPER");
		System.out.println("2. PUZZLE");
		System.out.println("3. GUESS THE NUMBER");
		System.out.println("4. EXIT GAME STUDIO");
		System.out.println(" ");
		processInput();
	}

	private void processInput() {
		System.out.println("Please enter your choise: ");
		int userInput = Integer.parseInt(scanner.nextLine().strip());
		while (true) {
			try {
				switch (userInput) {
				case 1:
					new Minesweeper();
					break;
				case 2:
					new Puzzle();
					break;
				case 3:
					new GuessTheNumberMain();
					break;
				case 4:
					System.exit(0);
					break;
				default:
					System.out.println("Game does not exist.");
					break;
				}
			} catch (NumberFormatException e) {
				e.getMessage();
			}
		}
	}
}
