package sk.tsystems.gamestudio.webservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.service.ScoreService;

@RestController
public class ScoreWebService {

	@Autowired
	private ScoreService scoreService;
	
	@RequestMapping(path ="/api/score/{game}", method = RequestMethod.GET)
	public List<Score> getTopScores(@PathVariable String game){
		return scoreService.getTopScores(game);
	}
	
	@RequestMapping(path ="/api/score/", method = RequestMethod.POST)
	public void addScore(@RequestBody Score score) {
		scoreService.addScore(score);
	}
}
