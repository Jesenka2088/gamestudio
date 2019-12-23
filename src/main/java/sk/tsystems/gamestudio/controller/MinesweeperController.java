package sk.tsystems.gamestudio.controller;

import java.util.Formatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.minesweeper.core.Clue;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/minesweeper")
public class MinesweeperController {
	private Field field;
	private boolean marking;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private MainController mainController;

	@Autowired
	private RatingService ratings;

	@Autowired
	private CommentService comments;

	@RequestMapping
	public String index() {
		field = new Field(9, 9, 10);
		return "minesweeper";
	}

	@RequestMapping("/action")
	public String openTile(int row, int column) {
		if (field.getState() == GameState.PLAYING) {
			if (marking) {
				field.markTile(row, column);
			} else
				field.openTile(row, column);
		}
		if (field.isSolved() && mainController.isLogged()) {
			scoreService
					.addScore(new Score(mainController.getLoggedPlayer().getName(), "minesweeper", field.getScore()));
		}
		return "minesweeper";
	}

	@RequestMapping("/change")
	public String change() {
		marking = !marking;
		return "minesweeper";
	}

	@RequestMapping("/addComments")
	public String addComments(String content) {
		if (mainController.isLogged())
			comments.addComment(new Comment(mainController.getLoggedPlayer().getName(), "minesweeper", content));
		return "minesweeper";
	}

	@RequestMapping("/rating")
	public String rating(int value) {
		if (mainController.isLogged()) {
			if (ratings.getRatingByGameNameAndUserName("minesweeper",
					mainController.getLoggedPlayer().getName()) == null) {
				ratings.addRating(new Rating(mainController.getLoggedPlayer().getName(), "minesweeper", value));
			} else
				ratings.updateRatingValueForUserAndGame("minesweeper", mainController.getLoggedPlayer().getName(),
						value);
		}
		return "minesweeper";
	}

	public boolean isMarking() {
		return marking;
	}

	public String getHtmlField() {
		Formatter f = new Formatter();
		f.format("<table>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			f.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				f.format("<td>\n");
				Tile tile = field.getTile(row, column);
				f.format(
						"<a href='/minesweeper/action?row=%d&column=%d'> <img src='/images/mines/%s.png' alt=\"tile\"/></a>",
						row, column, getImageName(tile));
				f.format("</td>\n");
			}
			f.format("<tr>\n");
		}
		f.format("</table>\n");
		return f.toString();
	}

	private String getImageName(Tile tile) {
		switch (tile.getState()) {
		case CLOSED:
			return "closed";
		case MARKED:
			return "marked";
		case OPEN:
			if (tile instanceof Clue)
				return "open" + ((Clue) tile).getValue();
			else
				return "mine";
		default:
			throw new IllegalArgumentException();
		}
	}

	public boolean isSolved() {
		return field.isSolved();
	}

	public List<Score> getScores() {
		return (scoreService.getTopScores("minesweeper"));
	}
	
	public boolean isScore() {
		return getScores().size() > 0;
	}

	public List<Comment> getComments() {
		return comments.getAllComments("minesweeper");
	}

	public double getAvgRating() {
		return Math.round(ratings.getAverageRatingByGameName("minesweeper"));
	}

	public boolean isRating() {
		return getAvgRating() != 0;
	}

	public boolean isComment() {
		return comments.getAllComments("minesweeper").size() > 0;
	}

}