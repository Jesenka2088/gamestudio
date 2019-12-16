package sk.tsystems.gamestudio.controller;

import java.util.Formatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Player;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.npuzzle.core.Field;
import sk.tsystems.gamestudio.game.npuzzle.core.Tile;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MainController {
	private Player loggedPlayer;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(Player player) {
		if("heslo".equals(player.getPassword())) {
			loggedPlayer = player;
		}
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout() {
			loggedPlayer = null;
		return "redirect:/";
	}
	
	
	public boolean isLogged() {
		return loggedPlayer != null;
	}
	
	public Player getLoggedPlayer() {
		return loggedPlayer;
	}
	

}