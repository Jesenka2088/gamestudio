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
import sk.tsystems.gamestudio.game.npuzzle.core.Field;
import sk.tsystems.gamestudio.game.npuzzle.core.Tile;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
// Scope session vytvara nove hracie pole pre kazdeho hraca, web browser must be a different or in incognito session
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/puzzle")
public class PuzzleController {

	private Field field;

	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private RatingService ratings;
	
	@Autowired
	private CommentService comments;

	@Autowired
	private MainController mainController;

	@RequestMapping
	public String index() {

		field = new Field(4, 4);
		return "puzzle";
	}

	@RequestMapping("/move")
	public String move(int tile) {
		field.move(tile);
		if (field.isSolved() && mainController.isLogged()) {
			scoreService.addScore(new Score(mainController.getLoggedPlayer().getName(), "puzzle", field.getScore()));
		}
		return "puzzle";
	}
	
	@RequestMapping("/addComments")
	public String addComments(String content) {
		if(mainController.isLogged())
			comments.addComment(new Comment(mainController.getLoggedPlayer().getName(), "puzzle", content));
		return "puzzle";
	}
	
	@RequestMapping("/rating")
	public String rating (int value) {
		if(mainController.isLogged()){
			if(ratings.getRatingByGameNameAndUserName("puzzle", mainController.getLoggedPlayer().getName()) == null) {
				ratings.addRating(new Rating (mainController.getLoggedPlayer().getName(),"puzzle", value));
			}else ratings.updateRatingValueForUserAndGame("puzzle", mainController.getLoggedPlayer().getName(), value);
		}
		return "puzzle";
	}

	public String getHtmlField() {
		Formatter f = new Formatter();

		f.format("<table>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			f.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				f.format("<td>\n");
				Tile tile = field.getTile(row, column);
				if (tile != null)
					f.format("<a href='/puzzle/move?tile=%d'> <img src='/images/puzzle/img%d.jpg' alt=\"tile\"/></a>",
							tile.getValue(), tile.getValue());
				f.format("</td>\n");
			}
			f.format("<tr>\n");
		}
		f.format("</table>\n");
		return f.toString();
	}

	public boolean isSolved() {
		return field.isSolved();
	}

	public List<Score> getScores() {
		return scoreService.getTopScores("puzzle");
	}
	public boolean isScore() {
		return getScores().size() > 0;
	}
	
	public List<Comment> getComments() {
		return comments.getAllComments("puzzle");
	}
	
	public double getAvgRating() {
		return Math.round(ratings.getAverageRatingByGameName("puzzle"));
	}
	
	public boolean isRating() {
			return getAvgRating() != 0;	
	}
	
	public boolean isComment() {
		return  getComments().size() > 0;	
}

}
