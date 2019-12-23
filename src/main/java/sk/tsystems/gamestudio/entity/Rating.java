package sk.tsystems.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Rating {
	

	@Id
	@GeneratedValue
	private int ident;
	
	private String username;
	
	private String game;
	
	private int value;
	
	
	public Rating() {
		
	}
	
	public Rating(String username, String game, int value) {

		this.username = username;
		this.game = game;
		this.value = value;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getIdent() {
		return ident;
	}

	@Override
	public String toString() {
		return "Rating [ident=" + ident + ", username=" + username + ", game=" + game + ", value=" + value + "]";
	}
	
	
}
