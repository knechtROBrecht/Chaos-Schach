package game;

import java.awt.Color;

public class GameField {

	private GameField upperRight;
	private GameField lowerRight;
	private GameField lower;
	private GameField lowerLeft;
	private GameField upperLeft;
	private GameField up;
	private int difficulty = 1;
	private int x;
	private int y;
	private GamePiece gamePiece;
	private int type;
	private String name;
	private Color color;

	public GameField(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
		if (type == 0) {
			initialize(Color.yellow, 1, "Plane");
		} else if (type == 1) {
			initialize(Color.darkGray, 2, "Swamp");
		}
	}

	private void initialize(Color color, int difficulty, String name) {
		this.setColor(color);
		this.difficulty = difficulty;
		this.setName(name);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setPiece(GamePiece gp) {
		gamePiece = gp;
	}

	public GamePiece getPiece() {
		return gamePiece;
	}

	public GameField getUpperRight() {
		return upperRight;
	}

	public void setUpperRight(GameField upperRight) {
		this.upperRight = upperRight;
	}

	public GameField getLowerRight() {
		return lowerRight;
	}

	public void setLowerRight(GameField lowerRight) {
		this.lowerRight = lowerRight;
	}

	public GameField getLower() {
		return lower;
	}

	public void setLower(GameField lower) {
		this.lower = lower;
	}

	public GameField getLowerLeft() {
		return lowerLeft;
	}

	public void setLowerLeft(GameField lowerLeft) {
		this.lowerLeft = lowerLeft;
	}

	public GameField getUpperLeft() {
		return upperLeft;
	}

	public void setUpperLeft(GameField upperLeft) {
		this.upperLeft = upperLeft;
	}

	public GameField getUp() {
		return up;
	}

	public void setUp(GameField up) {
		this.up = up;
	}

	public boolean contains(GameField gf) {
		// TODO
		return false;
	}

	public boolean equals(GameField gf) {
		if ((gf.getX() == x) && gf.getY() == y) {
			return true;
		}
		return false;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
