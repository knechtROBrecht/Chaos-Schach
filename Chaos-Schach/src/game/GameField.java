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

	/**
	 * @param x
	 * @param y
	 * @param type
	 *            Konstruktor ruft die initialize Methode mit den zum Typ
	 *            passenden Parametern auf.
	 */
	public GameField(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
		if (type == 0) {
			initialize(Color.yellow, 1, "Plane");
		} else if (type == 1) {
			initialize(Color.darkGray, 2, "Swamp");
		} else if (type == 2) {
			initialize(Color.white, 1, "Ice");
			// FIXME difficulty 0 funktioniert nicht.
		}

	}

	/**
	 * @param color
	 * @param difficulty
	 * @param name
	 *            Hilfsmethode des Konstruktors zum Festlegen bestimmter Werte
	 */
	private void initialize(Color color, int difficulty, String name) {
		this.setColor(color);
		this.difficulty = difficulty;
		this.setName(name);
	}

	/**
	 * @return Gibt die x-Koordinate des GameFields zurueck
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return Gibt die y-Koordinate des GameFields zurueck
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return Gibt die Schwierigkeit zurueck mit der eine Spielfigur sich ueber
	 *         das Spielfeld bewegen kann (wieviel reach ihr fuer diese Bewegung
	 *         abgezogen wird)
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * @param gp
	 *            Setzt ein GamePiece auf das GameField
	 */
	public void setPiece(GamePiece gp) {
		gamePiece = gp;
	}

	/**
	 * @return Gibt das GamePiece welches sich auf dem GameField befindet
	 *         zurueck
	 */
	public GamePiece getPiece() {
		return gamePiece;
	}

	/**
	 * @return Gibt das obere rechte Nachbarfeld zurueck
	 */
	public GameField getUpperRight() {
		return upperRight;
	}

	/**
	 * @param upperRight
	 *            Setzt das obere rechte Nachbarfeld
	 */
	public void setUpperRight(GameField upperRight) {
		this.upperRight = upperRight;
	}

	/**
	 * @return Gibt das untere rechte Nachbarfeld zurueck
	 */
	public GameField getLowerRight() {
		return lowerRight;
	}

	/**
	 * @param lowerRight
	 *            Setzt das untere rechte Nachbarfeld
	 */
	public void setLowerRight(GameField lowerRight) {
		this.lowerRight = lowerRight;
	}

	/**
	 * @return Gibt das untere Nachbarfeld zurueck
	 */
	public GameField getLower() {
		return lower;
	}

	/**
	 * @param lower
	 *            Setzt das untere Nachbarfeld
	 */
	public void setLower(GameField lower) {
		this.lower = lower;
	}

	/**
	 * @return Gibt das untere linke Nachbarfeld zurueck
	 */
	public GameField getLowerLeft() {
		return lowerLeft;
	}

	/**
	 * @param lowerLeft
	 *            Setzt das untere linke Nachbarfeld
	 */
	public void setLowerLeft(GameField lowerLeft) {
		this.lowerLeft = lowerLeft;
	}

	/**
	 * @return Gibt das obere linke Nachbarfeld zurueck
	 */
	public GameField getUpperLeft() {
		return upperLeft;
	}

	/**
	 * @param upperLeft
	 *            Setzt das obere linke Nachbarfeld
	 */
	public void setUpperLeft(GameField upperLeft) {
		this.upperLeft = upperLeft;
	}

	/**
	 * @return Gibt das obere Nachbarfeld zurueck
	 */
	public GameField getUp() {
		return up;
	}

	/**
	 * @param up
	 *            Setzt das obere Nachbarfeld
	 */
	public void setUp(GameField up) {
		this.up = up;
	}

	/**
	 * @param gf
	 * @return
	 */
	public boolean contains(GameField gf) {
		// TODO
		return false;
	}

	/**
	 * @param gf
	 * @return boolean Prueft ob die GameFields inhaltsgleich sind
	 */
	public boolean equals(GameField gf) {
		if ((gf.getX() == x) && gf.getY() == y) {
			return true;
		}
		return false;
	}

	/**
	 * @return Gibt die Farbe des GameFields zurueck
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *            Setzt die Farbe des GameFields
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return Gibt den Typen des GameFields zurueck
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            Setzt den Typen des GameFields
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return Gibt den Namen des GameFields zurueck
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            Setzt den Namen des GameFields
	 */
	public void setName(String name) {
		this.name = name;
	}

}
