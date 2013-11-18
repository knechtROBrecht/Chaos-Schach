package game;

import java.awt.Color;
import java.util.*;

public class GameField {

	private Set<GameField> neighbors = new HashSet<GameField>();
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
	 * @param gf
	 * 			f�gt ein Nachbarfeld hinzu
	 */
	public void addNeighbor(GameField gf) {
		neighbors.add(gf);
	}

	/**
	 * @return gibt das Set aller Nachbarn zur�ck
	 */
	public Set<GameField> getNeighbors() {
		return neighbors;
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
