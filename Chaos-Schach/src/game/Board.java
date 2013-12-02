package game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseInputListener, ActionListener {

	private final int FIELDHEIGHT = 30;
	private final int FIELDWIDTH = 30;
	private final int DISTANCE = 3;
	private final int FIELDCOUNTX = 23;
	private final int FIELDCOUNTY = 11;

	private int basePos;
	private int enemyBasePos;
	private String player;
	private Input input;
	private Output output;
	private boolean turn;
	private BufferedImage handImg, swordImg, feetImg, noImg;
	private Cursor handCur, swordCur, feetCur, noCur;

	private ArrayList<GameField> gameFields = new ArrayList<GameField>();
	private HashMap<GameField, Integer> reachableGameFields = new HashMap<GameField, Integer>(),
			attackableGameFields = new HashMap<GameField, Integer>();
	private GameField clickedField;
	private Random rand = new Random(20071969);
	private GameFrame gameFrame;
	private GamePiece actGamePiece;

	private int x = 0;

	/**
	 * @param server
	 *            Konstruktor des Boards fuer den Server
	 */
	Board(Server server) {
		player = "Server";
		input = new Input(server, this);
		output = new Output(server);
		basePos = FIELDCOUNTX * FIELDCOUNTY - 2;
		enemyBasePos = 1;
		turn = true;
		init();
	}

	/**
	 * @param client
	 *            Konstruktor des Boards fuer den Client
	 */
	Board(Client client) {
		player = "Client";
		input = new Input(client, this);
		output = new Output(client);
		basePos = 1;
		enemyBasePos = FIELDCOUNTX * FIELDCOUNTY - 2;
		turn = false;
		init();
	}

	/**
	 * Hilfsmethode welche vom Konstruktor aufgerufen wird welche den Input
	 * Thread startet, Listener added, Spielfelder und Spielfiguren erzeugt und
	 * die "Gameloop" startet
	 */
	private void init() {
		(new Thread(input)).start();
		addMouseListener(this);
		addMouseMotionListener(this);
		this.requestFocus();
		setGameFields();
		setGamePieces();
		defCursor();
	}

	private void defCursor() {
		ClassLoader cldr = this.getClass().getClassLoader();
		try {
			handImg = ImageIO.read(cldr.getResource("images/HandCursor.png"));
			swordImg = ImageIO.read(cldr.getResource("images/Sword.png"));
			feetImg = ImageIO.read(cldr.getResource("images/Feet.png"));
			noImg = ImageIO.read(cldr.getResource("images/NoCur.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		handCur = Toolkit.getDefaultToolkit().createCustomCursor(handImg,
				new Point(10, 0), "handCur");
		swordCur = Toolkit.getDefaultToolkit().createCustomCursor(swordImg,
				new Point(0, 0), "swordCur");
		feetCur = Toolkit.getDefaultToolkit().createCustomCursor(feetImg,
				new Point(10, 10), "feetCur");
		noCur = Toolkit.getDefaultToolkit().createCustomCursor(noImg,
				new Point(10, 10), "noCur");
	}

	/**
	 * Erzeugt die einzelnen Spielfelder mit einem zufaelligen Typen. Schafft
	 * auï¿½erdem direkt die Nachbarverbindungen der Spielfelder
	 */
	public void setGameFields() {
		int height = FIELDHEIGHT;
		int width = FIELDWIDTH;
		int mx = width;
		int my = height;
		int flag = 0;
		for (int j = 0; j < FIELDCOUNTX; j++) {
			for (int i = 0; i < FIELDCOUNTY; i++) {
				int chance = rand.nextInt(100);
				int fieldtype = 0;
				if (chance >= 1 && chance < 34) {
					fieldtype = 0;
				} else if (chance >= 34 && chance < 67) {
					fieldtype = 1;
				} else if (chance >= 67 && chance < 97) {
					fieldtype = 2;
				} else if (chance >= 97 && chance <= 100) {
					fieldtype = 3;
				}
				gameFields.add(new GameField(mx, my, fieldtype));
				my += height * 2;
			}
			if (flag == 0) {
				my = 2 * height;
				flag = 1;
			} else {
				my = height;
				flag = 0;
			}
			mx += (int) (width * 1.5);
		}

		for (GameField gf : gameFields) {
			if (getGameField(gf.getX(), gf.getY() + 2 * height) != null)
				gf.addNeighbor(getGameField(gf.getX(), gf.getY() + 2 * height));
			if ((getGameField((int) (gf.getX() - 1.5 * width), gf.getY()
					+ height)) != null)
				gf.addNeighbor(getGameField((int) (gf.getX() - 1.5 * width),
						gf.getY() + height));
			if (getGameField((int) (gf.getX() + 1.5 * width), gf.getY()
					+ height) != null)
				gf.addNeighbor(getGameField((int) (gf.getX() + 1.5 * width),
						gf.getY() + height));
			if (getGameField(gf.getX(), gf.getY() - 2 * height) != null)
				gf.addNeighbor(getGameField(gf.getX(), gf.getY() - 2 * height));
			if (getGameField((int) (gf.getX() - 1.5 * width), gf.getY()
					- height) != null)
				gf.addNeighbor(getGameField((int) (gf.getX() - 1.5 * width),
						gf.getY() - height));
			if (getGameField((int) (gf.getX() + 1.5 * width), gf.getY()
					- height) != null)
				gf.addNeighbor(getGameField((int) (gf.getX() + 1.5 * width),
						gf.getY() - height));
		}
	}

	/**
	 * Setzt in das obere linke und das untere rechte Eck des Spielfeldes die
	 * Basis Steine und bereits einen Soldaten
	 */
	public void setGamePieces() {
		GamePiece base1 = new GamePiece("Client", 0, "Client" == player);
		gameFields.get(1).setPiece(base1);
		GamePiece soldier1 = new GamePiece("Client", 1, "Client" == player);
		gameFields.get(2).setPiece(soldier1);

		GamePiece base2 = new GamePiece("Server", 0, "Server" == player);
		gameFields.get(FIELDCOUNTX * FIELDCOUNTY - 2).setPiece(base2);
		GamePiece soldier2 = new GamePiece("Server", 1, "Server" == player);
		gameFields.get(FIELDCOUNTX * FIELDCOUNTY - 3).setPiece(soldier2);

		gameFields.get(100).setPiece(
				new GamePiece("Server", 3, "Server" == player));
		gameFields.get(101).setPiece(
				new GamePiece("Server", 3, "Server" == player));
		gameFields.get(102).setPiece(
				new GamePiece("Server", 2, "Server" == player));
		gameFields.get(103).setPiece(
				new GamePiece("Server", 2, "Server" == player));
		gameFields.get(104).setPiece(
				new GamePiece("Server", 2, "Server" == player));
		gameFields.get(105).setPiece(
				new GamePiece("Client", 2, "Client" == player));
		gameFields.get(106).setPiece(
				new GamePiece("Client", 2, "Client" == player));
		gameFields.get(107).setPiece(
				new GamePiece("Client", 3, "Client" == player));
		gameFields.get(108).setPiece(
				new GamePiece("Client", 3, "Client" == player));
		gameFields.get(109).setPiece(
				new GamePiece("Client", 2, "Client" == player));
		gameFields.get(110).setPiece(
				new GamePiece("Client", 2, "Client" == player));

	}

	/**
	 * Setzt an die erste Position neben der Basis welche er findet zufaellig
	 * einen neuen Spielstein
	 */
	public void spawn() {
		resetStatus();
		GameField tmp = gameFields.get(basePos);

		for (GameField gf : tmp.getNeighbors()) {
			if (gf.getPiece() == null) {
				GamePiece temp = randomNewPiece();
				gf.setPiece(temp);
				output.create("c", gf.getX(), gf.getY(), temp.getOwner(),
						temp.getType());
				break;
			}
		}
		this.repaint();
	}

	/**
	 * @param x
	 * @param y
	 * @param player
	 * @param type
	 *            Erzeugt eine neue Spielfigur an der Stelle x,y vom Typ type
	 *            und der Besitzer ist player
	 */
	public void create(int x, int y, String player, int type) {
		getGameField(x, y).setPiece(
				new GamePiece(player, type, player == this.player));
		this.repaint();
	}

	/**
	 * @return Gibt einen neuen Spielstein von einem zufaelligen Typen zurueck
	 */
	public GamePiece randomNewPiece() {
		return new GamePiece(player, rand.nextInt(3) + 1, true);
	}

	/**
	 * @param x
	 * @param y
	 * @return Gibt das Spielfeld an der Position x,y zurueck
	 */
	public GameField getGameField(int x, int y) {
		for (GameField gf : gameFields) {

			if (gf.getX() == x && gf.getY() == y) {
				return gf;
			}
		}
		return null;
	}

	/**
	 * @param event
	 * @return Gibt das vom Mausclick am wenigsten entfernte Spielfeld zurueck
	 */
	public GameField nearestField(MouseEvent event) {
		GameField nearestGameField = gameFields.get(0);
		for (GameField gf : gameFields) {
			if ((Math.sqrt(Math.pow((gf.getX() - event.getX()), 2)
					+ Math.pow((gf.getY() - event.getY()), 2))) < (Math
					.sqrt(Math.pow((nearestGameField.getX() - event.getX()), 2)
							+ Math.pow(
									(nearestGameField.getY() - event.getY()), 2)))) {
				nearestGameField = gf;
			}
		}
		return nearestGameField;
	}

	/**
	 * @param gf
	 * @param steps
	 * @return Gibt eine Menge mit den von einem bestimmten Spielfeld ueber eine
	 *         bestimmte Schritteanzahl erreichbarer Spielfelder zurueck
	 */
	public HashMap<GameField, Integer> reachableGameFields(GameField gf,
			int steps) {
		reachableGameFields.put(gf, steps);
		for (GameField tmp : gf.getNeighbors()) {
			if (!(reachableGameFields.containsKey(tmp))
					|| reachableGameFields.get(tmp) <= steps) {
				if (tmp.getPiece() == null) {
					if (steps >= tmp.getDifficulty())
						reachableGameFields(tmp, steps - tmp.getDifficulty());
				}
			}
		}
		return reachableGameFields;
	}

	/**
	 * @param gf
	 * @param steps
	 * @return Gibt eine Menge mit den von einem bestimmten Spielfeld ueber eine
	 *         bestimmte Schritteanzahl angreifbarer Spielfelder zurueck
	 */
	public HashMap<GameField, Integer> attackableGameFields(GameField gf,
			int reach) {
		x++;
		System.out.println(x);
		for (GameField tmp : gf.getNeighbors()) {
			if (!(attackableGameFields.containsKey(tmp))
					|| attackableGameFields.get(tmp) <= reach) {
				if (tmp.getPiece() == null
						|| (tmp.getPiece() != null && tmp.getPiece().getOwner() == player)) {
					if (reach > 0)
						attackableGameFields(tmp, reach - 1);
				} else if (tmp.getPiece() != null
						&& tmp.getPiece().getOwner() != player) {
					if (reach > 0) {
						attackableGameFields.put(tmp, reach);
						attackableGameFields(tmp, reach - 1);
					}
				}
			}
		}
		return attackableGameFields;
	}

	/**
	 * @param xAlt
	 * @param yAlt
	 * @param xNeu
	 * @param yNeu
	 *            Spielstein an der Position xAlt, yAlt greift Spielstein an der
	 *            Position xNeu, yNeu an
	 */
	public void attack(int xAlt, int yAlt, int xNeu, int yNeu) {
		if (getGameField(xNeu, yNeu).getPiece().getHp() <= getGameField(xAlt,
				yAlt).getPiece().getAttack()) {
			getGameField(xNeu, yNeu).setPiece(null);
		} else {
			getGameField(xNeu, yNeu).getPiece().setHp(
					getGameField(xNeu, yNeu).getPiece().getHp()
							- getGameField(xAlt, yAlt).getPiece().getAttack());
		}
		getGameField(xAlt, yAlt).getPiece().setAtk(false);
		if (gameFields.get(enemyBasePos).getPiece() == null)
			win();
		this.repaint();
	}

	public void moveGamePiece(int xAlt, int yAlt, int xNeu, int yNeu) {
		getGameField(xNeu, yNeu).setPiece(getGameField(xAlt, yAlt).getPiece());
		getGameField(xAlt, yAlt).setPiece(null);
		getGameField(xNeu, yNeu).getPiece().setStepsLeft(0);
		this.repaint();
	}

	/**
	 * Spiel wird beendet
	 */
	public void end() {
		output.close();
		System.exit(0);
	}

	/**
	 * Gibt den Zug an den Gegner ab
	 */
	public void turn() {
		output.turn();
		turn = false;
		gameFrame.changeTurnStatus();
		resetStatus();
		this.repaint();
	}

	/**
	 * Spiel gewonnen
	 */
	public void win() {
		output.win();
		JOptionPane.showMessageDialog(null,
				"Gegnerische Basis zerstï¿½rt \nSie haben das Spiel gewonnen!",
				"Gewonnen", JOptionPane.WARNING_MESSAGE);
		System.exit(0);
	}

	public void resetStatus() {
		for (GameField gf : gameFields) {
			if (gf.getPiece() != null) {
				gf.getPiece().setStepsLeft(gf.getPiece().getSteps());
				gf.getPiece().setAtk(true);
			}
		}
	}

	/**
	 * Ruft die Methode im GameFrame auf welche anzeigt wer an der Reihe ist
	 */
	public void changeTurnStatus() {
		gameFrame.changeTurnStatus();
	}

	/**
	 * @return Gibt den HandCursor zurück
	 */
	public Cursor getHandCur() {
		return handCur;
	}

	/**
	 * @return Gibt zurueck ob man an der Reihe ist
	 */
	public boolean getTurn() {
		return turn;
	}

	/**
	 * @param turn
	 *            Setzt ob man an der Reihe ist
	 */
	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	/**
	 * @param gameFrame
	 *            Fuegt GameFrame zum Board hinzu
	 */
	public void addGameFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}

	private void actCur(MouseEvent event) {
		GameField ngf = nearestField(event);
		if (reachableGameFields.containsKey(ngf)) {
			setCursor(feetCur);
		} else if (attackableGameFields.containsKey(ngf)) {
			setCursor(swordCur);
		} else if (ngf.getPiece() != null) {
			if (ngf.getPiece().getOwner() != player) {
				setCursor(noCur);
			} else {
				setCursor(handCur);
			}
		} else {
			setCursor(handCur);
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		GameField ngf = nearestField(event);
		if (ngf.getPiece() != null) {
			gameFrame.setTextArea(ngf.getPiece().toString(), ngf.getPiece()
					.getOwner().equals(player));
		}
		actCur(event);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (turn) {
			if (actGamePiece == null) {
				System.out.println("firstclick");
				firstclick(event);
				this.repaint();
			} else {
				System.out.println("secondclick");
				secondclick(event);
				clickedField = null;
				actGamePiece = null;
				this.repaint();
			}
		}
		actCur(event);
	}

	private void firstclick(MouseEvent event) {
		clickedField = nearestField(event);
		if (clickedField.getPiece() != null
				&& clickedField.getPiece().getOwner().equals(player)) {
			reachableGameFields = reachableGameFields(clickedField,
					clickedField.getPiece().getStepsLeft());
			if (clickedField.getPiece().getAtk()) {
				attackableGameFields = attackableGameFields(clickedField,
						clickedField.getPiece().getReach());
			}
			actGamePiece = clickedField.getPiece();
		}
	}

	private void secondclick(MouseEvent event) {
		GameField releasedField = nearestField(event);
		if (!(clickedField.equals(releasedField))
				&& releasedField.getPiece() == null
				&& clickedField.getPiece() != null) {

			for (GameField gf : reachableGameFields.keySet()) {
				if (gf == releasedField) {
					output.move(clickedField.getX(), clickedField.getY(),
							releasedField.getX(), releasedField.getY());
					moveGamePiece(clickedField.getX(), clickedField.getY(),
							releasedField.getX(), releasedField.getY());
					gf.getPiece().setStepsLeft(0);
				}
			}
		} else if (!(clickedField.equals(releasedField))
				&& releasedField.getPiece() != null
				&& clickedField.getPiece() != null) {
			for (GameField gf : attackableGameFields.keySet()) {
				if (gf == releasedField) {
					output.attack(clickedField.getX(), clickedField.getY(),
							releasedField.getX(), releasedField.getY());
					attack(clickedField.getX(), clickedField.getY(),
							releasedField.getX(), releasedField.getY());
				}
			}

		}
		attackableGameFields.clear();
		reachableGameFields.clear();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		actCur(event);
	}

	@Override
	public void mouseExited(MouseEvent event) {
		actCur(event);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int height = FIELDHEIGHT - DISTANCE + 1;
		int width = FIELDWIDTH - DISTANCE + 1;
		for (GameField gf : gameFields) {
			int ax = (gf.getX() + width); // rechter Punkt
			int ay = (gf.getY());
			int bx = (int) (gf.getX() + (width * 0.5)); // unterer rechter Punkt
			int by = (gf.getY() + height);
			int cx = (int) (gf.getX() - (width * 0.5)); // unterer linker Punkt
			int cy = (gf.getY() + height);
			int dx = (gf.getX() - width); // linker Punkt
			int dy = (gf.getY());
			int ex = (int) (gf.getX() - (width * 0.5)); // oberer linker Punkt
			int ey = (gf.getY() - height);
			int fx = (int) (gf.getX() + (width * 0.5)); // oberer rechter Punkt
			int fy = (gf.getY() - height);

			Polygon polygon = new Polygon();

			polygon.addPoint(ax, ay);
			polygon.addPoint(bx, by);
			polygon.addPoint(cx, cy);
			polygon.addPoint(dx, dy);
			polygon.addPoint(ex, ey);
			polygon.addPoint(fx, fy);

			if (this.reachableGameFields.containsKey(gf)) {
				g2.setColor(Color.magenta);
				g2.fillPolygon(polygon);
				g2.drawPolygon(polygon);
			}
			if (this.attackableGameFields.containsKey(gf)) {
				g2.setColor(Color.red);
				g2.fillPolygon(polygon);
				g2.drawPolygon(polygon);
			}

			g2.drawImage(gf.getImg(), gf.getX() - gf.getImg().getWidth() / 2,
					gf.getY() - gf.getImg().getHeight() / 2, null);

		}

		for (GameField gf : gameFields) {
			if (gf.getPiece() != null) {

				g2.drawImage(gf.getPiece().getImg(), gf.getX()
						- gf.getPiece().getImg().getWidth() / 2, gf.getY()
						- gf.getPiece().getImg().getHeight() / 2, null);
				
				int length = gf.getPiece().getImg().getWidth() - 4;
				int life = gf.getPiece().getHp() * length / gf.getPiece().getMaxHp();
				int nolife = length - life;
				
				g2.setColor(Color.red);
				g2.fillRect(life + 2 + gf.getX() - gf.getPiece().getImg().getWidth() / 2,
						gf.getY() - 2 - gf.getPiece().getImg().getHeight() / 2, nolife,
						4);
				
				g2.setColor(Color.green);
				g2.fillRect(gf.getX() + 2 - gf.getPiece().getImg().getWidth() / 2,
						gf.getY() - 2 - gf.getPiece().getImg().getHeight() / 2, life,
						4);
			}
		}
	}

}
