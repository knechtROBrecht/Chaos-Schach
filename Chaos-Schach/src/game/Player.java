package game;

import java.awt.Color;

public class Player{
	
	private String name;
	
	Player(String name, Color color){
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
