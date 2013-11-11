package game;

import java.awt.Color;

public class GamePiece {

	private String owner;
	private int id;
	private String type;
	private int steps;
	private int hp;
	private int attack;
	private Color color;

	public GamePiece(String owner, String type, int id) {
		this.owner = owner;
		this.id = id;
		this.setType(type);
		if (type.endsWith("Base")) {
			initialize(0, 100, 10,Color.black);
		} else if (type.endsWith("Soldier")) {
			initialize(2, 10, 2, Color.WHITE);
		}
	}

	private void initialize(int steps, int hp, int attack, Color color) {
		this.steps = steps;
		this.attack = attack;
		this.hp = hp;
		this.setColor(color);
	}

	public int getSteps() {
		return steps;
	}

	public int getId() {
		return id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
