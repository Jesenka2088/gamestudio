package sk.tsystems.gamestudio.entity;

public class Player {

	private int ident;
	private String name;
	private String password;

	public Player(String name, String password) {

		this.name = name;
		this.password = password;
	}
	
	public Player () {
		
	}

	public int getIdent() {
		return ident;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}