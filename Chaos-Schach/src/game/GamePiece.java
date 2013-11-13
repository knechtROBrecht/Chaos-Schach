package game;

import java.awt.Color;

public class GamePiece {

	private final static String newline = "\n";

	private String owner;
	private int type;
	private String name;
	private int steps;
	private int stepsLeft;
	private int hp;
	private int attack;
	private Color color;
	private int reach;
	private Boolean atk = true;

	public GamePiece(String owner, int type) {
		this.owner = owner;
		this.type = type;
		switch (type) {
		case 0:
			initialize(0, 1, 10, Color.black, 1, "Base");
			break;
		case 1:
			initialize(2, 10, 2, Color.WHITE,1,"Soldier");
			break;			
		case 2:
			initialize(15, 15, 1, Color.CYAN,3,"Archer");
			break;
		default:
			break;
		}
	}

	private void initialize(int steps, int hp, int attack, Color color, int reach, String name) {
		this.steps = steps;
		this.stepsLeft = steps;
		this.hp = hp;
		this.attack = attack;
		this.color = color;
		this.reach = reach;
		this.name = name;
	}

	public int getSteps() {
		return steps;
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

	public int getType() {
		return type;
	}

	public void setType(int type2) {
		this.type = type2;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String toString() {
		return "Type: " + getName() + newline + "Attack: " + getAttack()
				+ newline + "Reach: " + reach + newline + "HP: " + getHp() + newline + "StepsLeft: "
				+ getStepsLeft() + newline + "Steps: " + getSteps() + newline;

	}

	public int getStepsLeft() {
		return stepsLeft;
	}

	public void setStepsLeft(int stepsLeft) {
		this.stepsLeft = stepsLeft;
	}

	public int getReach() {
		return reach;
	}

	public void setReach(int reach) {
		this.reach = reach;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getAtk() {
		return atk;
	}

	public void setAtk(Boolean atk) {
		this.atk = atk;
	}
}
