package sk.tsystems.gamestudio.game.guessnumber.core;

import java.util.Random;

import sk.tsystems.gamestudio.game.guessnumber.consoleui.ConsoleUI;
import sk.tsystems.gamestudio.game.guessnumber.consoleui.WrongFormatException;

public class GuessTheNumberMain {
	
	private final int minNumber = 1;
	private final int maxNumber = 50;
	private Random random = new Random();
	int randomNumber = random.nextInt(maxNumber) + minNumber;
	int userNumber = 0;

	/*
	 * CONSTRUCTOR
	 */
	public GuessTheNumberMain() {
		var console = new ConsoleUI();
		console.play(userNumber, randomNumber, minNumber, maxNumber);
	}

	public static void main(String[] args)  {
		new GuessTheNumberMain();
	}

}
