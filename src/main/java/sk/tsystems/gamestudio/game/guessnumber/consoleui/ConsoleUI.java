package sk.tsystems.gamestudio.game.guessnumber.consoleui;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sk.tsystems.gamestudio.consoleui.Menu;

public class ConsoleUI {
	Scanner scanner = new Scanner(System.in);

	public void play(int guessedNumber, int randomNumber, int minNumber, int maxNumber) {
		boolean userCorrect = false;
		while (!userCorrect) {
			System.out.println(
					"Guess the number between: " + minNumber + " and " + maxNumber + ", to EXIT the game enter X.");
			String userStringInput = scanner.nextLine();
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher m = pattern.matcher(userStringInput);
			if ("X".equals(userStringInput.toUpperCase().strip())) {
				Menu menu = new Menu();
				menu.showMenu();
			}

			if (m.matches()) {
				int userNumber = Integer.parseInt(userStringInput);
				if (userNumber >= minNumber && userNumber <= maxNumber) {
					if (userNumber > randomNumber) {
						System.out.println("Too high, try again");
					} else if (userNumber < randomNumber) {
						System.out.println("Too low, try again");
					} else {
						System.out.println("That is CORRECT NUMBER!");
						userCorrect = true;
					}
				} else {
					throw new NumberFormatException(
							"Wrong number. Number must be in between " + minNumber + " and " + maxNumber);
				}
			} else {
				throw new NumberFormatException("Wrong input format. Input must be a number.");
			}
		}
	}
}
