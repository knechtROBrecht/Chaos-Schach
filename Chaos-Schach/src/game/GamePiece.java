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

	/**
	 * @param owner
	 * @param type
	 *            Konstruktor ruft die Hilfsmethode initialize mit den jeweils
	 *            fuer den Spielsteintypen benoetigten Parametern auf
	 */
	public GamePiece(String owner, int type) {
		this.owner = owner;
		this.type = type;
		switch (type) {
		case 0:
			initialize(0, 1, 10, Color.black, 1, "Base");
			break;
		case 1:
			initialize(2, 10, 2, Color.WHITE, 1, "Soldier");
			break;
		case 2:
			initialize(3, 5, 1, Color.CYAN, 3, "Archer");
			break;
		default:
			break;
		}
	}

	/**
	 * @param steps
	 * @param hp
	 * @param attack
	 * @param color
	 * @param reach
	 * @param name
	 *            Wird vom Konstruktor aufgerufen und setzt die Paramter fuer
	 *            einen bestimmten Spielsteintypen
	 */
	private void initialize(int steps, int hp, int attack, Color color,
			int reach, String name) {
		this.steps = steps;
		this.stepsLeft = steps;
		this.hp = hp;
		this.attack = attack;
		this.color = color;
		this.reach = reach;
		this.name = name;
	}

	/**
	 * @return Gibt die Anzahl der Schritte zurueck die eine Spielfigur
	 *         zuruecklegen kann
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * @return Gibt den Besitzer der Spielfigur zurueck
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            Setzt den Besitzer einer Spielfigur
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return Gibt die Angriffsstaerke einer Spielfigur zurueck
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * @param attack
	 *            Setzt die Angriffstaerke einer Spielfigur
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	/**
	 * @return Gibt die Anzahl der Lebenspunkte einer Spielfigur zurueck
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @param hp
	 *            Setzt die Anzahl der Lebenspunkte einer Spielfigur
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * @return Gibt den Typen der Spielfigur zurueck
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type2
	 *            Setzt den Typen der Spielfigur
	 */
	public void setType(int type2) {
		this.type = type2;
	}

	/**
	 * @return Gibt die Farbe der Spielfigur zurueck
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *            Setzt die Farbe der Spielfigur
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	public String toString() {
		return "Type: " + getName() + newline + "Attack: " + getAttack()
				+ newline + "Reach: " + reach + newline + "HP: " + getHp()
				+ newline + "StepsLeft: " + getStepsLeft() + newline
				+ "Steps: " + getSteps() + newline;

	}

	/**
	 * @return Gibt die Anzahl der noch uebrigen Schritte einer Figur zurueck
	 */
	public int getStepsLeft() {
		return stepsLeft;
	}

	/**
	 * @param stepsLeft
	 *            Setzt die uebrigen Schritte einer Spielfigur
	 */
	public void setStepsLeft(int stepsLeft) {
		this.stepsLeft = stepsLeft;
	}

	/**
	 * @return Gibt die Angriffsreichweite einer Spielfigur zurueck
	 */
	public int getReach() {
		return reach;
	}

	/**
	 * @param reach
	 *            Setzt die Angriffsreichweite einer Spielfigur
	 */
	public void setReach(int reach) {
		this.reach = reach;
	}

	/**
	 * @return Gibt den Namen einer Spielfigur zurueck
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            Setzt den Namen einer Spielfigur
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Gibt zurueck ob die Spielfigur angreifen kann oder nicht
	 */
	public Boolean getAtk() {
		return atk;
	}

	/**
	 * @param atk
	 *            Setzt per boolean ob die Spielfigur angriffsfaehig ist oder
	 *            nicht
	 */
	public void setAtk(Boolean atk) {
		this.atk = atk;
	}
}
