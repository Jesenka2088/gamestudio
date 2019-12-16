package sk.tsystems.gamestudio;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.consoleui.Menu;
import sk.tsystems.gamestudio.service.ScoreService;
import sk.tsystems.gamestudio.service.ScoreServiceJDBC;

public class Main {

	
	private Main() {
		var menu = new Menu();
		menu.showMenu();
	}
	
	
	public static void main(String[] args) {
		new Main();
		
//		ScoreService scoreService = new ScoreServiceJDBC();
//		
//		scoreService.addScore(new Score("Claudia", "mines", 153));
//
//		for (Score score : scoreService.getTopScores("npuzzle")) {
//			System.out.println(score.getUsername() + " " + score.getValue());
//		}

	}
}
