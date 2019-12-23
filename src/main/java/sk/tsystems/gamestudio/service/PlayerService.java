package sk.tsystems.gamestudio.service;

import java.util.List;

import sk.tsystems.gamestudio.entity.Player;

public interface PlayerService {

	void addPlayer(Player player);

	List<Player> getAllPlayers();

	Player findPlayerByName(String name);

}