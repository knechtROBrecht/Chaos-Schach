package game;

public class GamePiece {

	private String owner;
	private int id;
	private int steps;
	private int hp;
	private int attack;

	public GamePiece(String owner, int steps, int id, int attack, int hp) {
		this.owner =owner;
		this.steps = steps;
		this.id = id;
		this.attack = attack;
		this.hp = hp;
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
}
