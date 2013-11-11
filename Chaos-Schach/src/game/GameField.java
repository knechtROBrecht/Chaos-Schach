package game;

public class GameField{

	private GameField upperRight;
	private GameField lowerRight;
	private GameField lower;
	private GameField lowerLeft;
	private GameField upperLeft;
	private GameField up;
	private int difficulty = 1;
	private  int x;
	private  int y;
	private GamePiece gamePiece;

	public GameField(int x, int y) {
		this.x = x;
		this.y = y;
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
	
	public boolean contains(GameField gf){
		//TODO
		return false;
	}
	
	public boolean equals(GameField gf){
		if((gf.getX() == x) && gf.getY() == y){
			return true;
		}
		return false;	
	}


}
