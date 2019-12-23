package sk.tsystems.gamestudio.controller;

import java.util.Formatter;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
//Scope session vytvara nove hracie pole pre kazdeho hraca, web browser must be a different or in incognito session
@Scope(WebApplicationContext.SCOPE_SESSION)

public class GuessTheNumberController {

	@Autowired
	private MainController mainController;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private CommentService comments;

	@Autowired
	private RatingService ratings;

	private final int minNumber = 1;
	private final int maxNumber = 50;

	private Random random = new Random();
	private int randomNumber;
	private String message;
	private int guessedNumber;
	private int tries;

	@RequestMapping("/guessNumber")
	public String index() {
		randomNumber = random.nextInt(maxNumber) + minNumber;
		return "guessthenumber";
	}

	@RequestMapping("/guessNumber/guess")
	public String guess(String input) {
		try {
			guessedNumber = Integer.parseInt(input);
			tries++;
			if (isSolved() && mainController.isLogged()) {
				scoreService.addScore(
						new Score(mainController.getLoggedPlayer().getName(), "Guess The Number", getScore()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "guessthenumber";
	}

	@RequestMapping("/guessNumber/addComments")
	public String addComments(String content) {
		if (mainController.isLogged())
			comments.addComment(new Comment(mainController.getLoggedPlayer().getName(), "Guess The Number", content));
		return "guessthenumber";
	}

	@RequestMapping("/guessNumber/rating")
	public String rating(int value) {
		if (mainController.isLogged()) {
			if (ratings.getRatingByGameNameAndUserName("Guess The Number",
					mainController.getLoggedPlayer().getName()) == null) {
				ratings.addRating(new Rating(mainController.getLoggedPlayer().getName(), "Guess The Number", value));
			} else
				ratings.updateRatingValueForUserAndGame("Guess The Number", mainController.getLoggedPlayer().getName(),
						value);
		}
		return "guessthenumber";
	}

	private int getScore() {
		int score = maxNumber - (tries * 2);
		if(score>0)
			return score;
			else return 0;
	}

	public String getGuessNumberField() {
		Formatter f = new Formatter();

		f.format("<form action=\"/guessNumber/guess\">");
		f.format("<div class=\"form-group\">");
		f.format("<label for=\"name\">I am thinking of number between 1 and 50.</label>");
		f.format("<input type=\"text\" class=\"form-control\" name=\"input\" placeholder=\"Type your guess...\">");
		f.format("<button type=\"submit\" class=\"btn btn-primary\">Submit</button>");
		f.format("</div>");
		f.format("</form>");

		return f.toString();
	}

	public String getMessage() {
		if (guessedNumber > randomNumber)
			return "Too high, try again";
		if (guessedNumber < randomNumber)
			return "Too low, try again";
		if (guessedNumber == randomNumber)
			return "That was a correct number! You win!";
		return message;
	}

	public int getGuessedNumber() {
		return guessedNumber;
	}

	public boolean isSolved() {
		return (guessedNumber == randomNumber);
	}

	public List<Score> getScores() {
		return scoreService.getTopScores("Guess The Number");
	}
	public boolean isScore() {
		return getScores().size() > 0;
	}

	public List<Comment> getComments() {
		return comments.getAllComments("Guess The Number");
	}

	public double getAvgRating() {
		return Math.round(ratings.getAverageRatingByGameName("Guess The Number"));
	}

	public boolean isRating() {
		return getAvgRating() != 0;
	}

	public boolean isComment() {
		return getComments().size() >0;
	}
}
