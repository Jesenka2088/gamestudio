package sk.tsystems.gamestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.gamestudio.entity.Player;
import sk.tsystems.gamestudio.service.PlayerService;


@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MainController {
	private Player loggedPlayer;
	private Player registeredPlayer;

	@Autowired
	private PlayerService playerService;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/signin")
	public String signin(Player player) {
		if(!player.getName().trim().equals("") && !player.getPassword().trim().equals("") && playerService.findPlayerByName(player.getName()) == null)  {
			playerService.addPlayer(player);
			registeredPlayer = player;
			loggedPlayer = player;
		}  
		return "redirect:/";
	}
	
	@RequestMapping("/login")
	public String login(Player player) {
		Player userInDatabase=playerService.findPlayerByName(player.getName());
		if(userInDatabase != null && (userInDatabase.getPassword()
				.equals(player.getPassword()))) {
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
	
	public boolean isRegistered() {
		return registeredPlayer != null;
	}
	
	public Player getRegisteredPlayer() {
		return registeredPlayer;
	}

}