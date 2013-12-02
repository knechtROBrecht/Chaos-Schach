package game;

import java.awt.image.BufferedImage;
import java.util.*;

public class GameField {

	private Set<GameField> neighbors = new HashSet<GameField>();
	private int difficulty = 1;
	private int x;
	private int y;
	private GamePiece gamePiece;
	private BufferedImage img;

	/**
	 * @param x
	 * @param y
	 * @param type
	 *            Konstruktor ruft die initialize Methode mit den zum Typ
	 *            passenden Parametern auf.
	 */
	public GameField(BufferedImage img, int x, int y, int type) {
		this.x = x;
		this.y = y;
		if (type == 0) {
			initialize(img, 1);
		} else if (type == 1) {
			initialize(img, 2);
		} else if (type == 2) {
			initialize(img, 3);
		} else if (type == 3) {
			initialize(img, 999);
		}

	}

	/**
	 * @param color
	 * @param difficulty
	 * @param name
	 *            Hilfsmethode des Konstruktors zum Festlegen bestimmter Werte
	 */
	private void initialize(BufferedImage img, int difficulty) {
		this.difficulty = difficulty;
		this.img = img;
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
	 *            fügt ein Nachbarfeld hinzu
	 */
	public void addNeighbor(GameField gf) {
		neighbors.add(gf);
	}

	/**
	 * @return gibt das Set aller Nachbarn zurück
	 */
	public Set<GameField> getNeighbors() {
		return neighbors;
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

	public BufferedImage getImg() {
		return img;
	}

}
