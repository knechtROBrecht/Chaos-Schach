package game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseInputListener, ActionListener {

	private final int FIELDHEIGHT = 30;
	private final int FIELDWIDTH = 30;
	private final int DISTANCE = 3;
	private final int PIECESIZE = (FIELDHEIGHT - DISTANCE);
	private final int FIELDCOUNTX = 23;
	private final int FIELDCOUNTY = 11;

	private int basePos;
	private int enemyBasePos;
	private String player;
	private Input input;
	private Output output;
	private boolean turn;

	private ArrayList<GameField> gameFields = new ArrayList<GameField>();
	private Set<GameField> reachableGameFields = new HashSet<GameField>();
	private Set<GameField> attackableGameFields = new HashSet<GameField>();
	private Timer time = new Timer(30, this);
	private GameField clickedField;
	private Random rand = new Random(20071969);
	private GameFrame gameFrame;

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

		time.start();
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
	 * Ruft die Methode im GameFrame auf welche anzeigt wer an der Reihe ist
	 */
	public void changeTurnStatus() {
		gameFrame.changeTurnStatus();
	}

	/**
	 * Setzt in das obere linke und das untere rechte Eck des Spielfeldes die
	 * Basis Steine und bereits einen Soldaten
	 */
	public void setGamePieces() {
		GamePiece base1 = new GamePiece("Client", 0);
		gameFields.get(1).setPiece(base1);
		GamePiece soldier1 = new GamePiece("Client", 1);
		gameFields.get(2).setPiece(soldier1);

		GamePiece base2 = new GamePiece("Server", 0);
		gameFields.get(FIELDCOUNTX * FIELDCOUNTY - 2).setPiece(base2);
		GamePiece soldier2 = new GamePiece("Server", 1);
		gameFields.get(FIELDCOUNTX * FIELDCOUNTY - 3).setPiece(soldier2);
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
				output.create("c", gf.getX(), gf.getY(), temp.getOwner(), temp.getType());
				break;
			}
		}
		this.repaint();
	}

	/**
	 * @return Gibt einen neuen Spielstein von einem zufaelligen Typen zurueck
	 */
	public GamePiece randomNewPiece() {
		return new GamePiece(player, rand.nextInt(2) + 1);
	}

	/**
	 * Erzeugt die einzelnen Spielfelder mit einem zufaelligen Typen. Schafft
	 * au�erdem direkt die Nachbarverbindungen der Spielfelder
	 */
	public void setGameFields() {
		int height = FIELDHEIGHT;
		int width = FIELDWIDTH;
		int mx = width;
		int my = height;
		int flag = 0;
		for (int j = 0; j < FIELDCOUNTX; j++) {
			for (int i = 0; i < FIELDCOUNTY; i++) {
				gameFields.add(new GameField(mx, my, rand.nextInt(3)));

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

	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int height = FIELDHEIGHT - DISTANCE;
		int width = FIELDWIDTH - DISTANCE;
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

			g2.setColor(gf.getColor());
			g2.fillPolygon(polygon);
			g2.drawPolygon(polygon);

			if (this.reachableGameFields.contains(gf)) {
				g2.setColor(Color.GREEN);
				g2.drawPolygon(polygon);
			}
			if (this.attackableGameFields.contains(gf)) {
				g2.setColor(Color.red);
				g2.drawPolygon(polygon);
			}

		}

		for (GameField gf : gameFields) {
			if (gf.getPiece() != null) {

				g2.drawImage(gf.getPiece().getImg(), gf.getX() - gf.getPiece().getImg().getWidth()/2, gf.getY() - gf.getPiece().getImg().getHeight()/2, null);
			
//				g2.setColor(gf.getPiece().getColor());
//				g2.fillRect(gf.getX() - PIECESIZE / 2, gf.getY() - PIECESIZE
//						/ 2, PIECESIZE, PIECESIZE);
				if (gf.getPiece().getOwner().equals(player)) {
					g2.setColor(Color.BLUE);
				} else {
					g2.setColor(Color.RED);
				}
				g2.drawRect(gf.getX() - PIECESIZE / 2, gf.getY() - PIECESIZE
						/ 2, PIECESIZE, PIECESIZE);
			} else {
				g2.setColor(gf.getColor());
				g2.fillRect(gf.getX() - PIECESIZE / 2, gf.getY() - PIECESIZE
						/ 2, PIECESIZE, PIECESIZE);
				g2.drawRect(gf.getX() - PIECESIZE / 2, gf.getY() - PIECESIZE
						/ 2, PIECESIZE, PIECESIZE);
			}
		}

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
	 * @param gf
	 * @param steps
	 * @return Gibt eine Menge mit den von einem bestimmten Spielfeld ueber eine
	 *         bestimmte Schritteanzahl erreichbarer Spielfelder zurueck
	 */
	public Set<GameField> reachableGameFields(GameField gf, int steps) {
		reachableGameFields.add(gf);
		for (GameField tmp : gf.getNeighbors()) {
			if (tmp.getPiece() == null) {
				if (steps >= tmp.getDifficulty())
					reachableGameFields(tmp, steps - tmp.getDifficulty());
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
	public Set<GameField> attackableGameFields(GameField gf, int reach) {

		for (GameField tmp : gf.getNeighbors()) {
			if (tmp.getPiece() == null) {
				if (reach > 0)
					attackableGameFields(tmp, reach - 1);
			} else if (tmp != null && tmp.getPiece() != null
					&& tmp.getPiece().getOwner() != player) {
				if (reach > 0) {
					attackableGameFields.add(tmp);
					attackableGameFields(tmp, reach - 1);
				}
			}
		}
		return attackableGameFields;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
//		this.repaint();
	}

	public void mousePressed(MouseEvent event) {
		if (turn) {
			clickedField = nearestField(event);
			if (clickedField.getPiece() != null
					&& clickedField.getPiece().getOwner().equals(player)) {
				reachableGameFields = reachableGameFields(clickedField,
						clickedField.getPiece().getStepsLeft());
				if (clickedField.getPiece().getAtk()) {
					attackableGameFields = attackableGameFields(clickedField,
							clickedField.getPiece().getReach());
				}
			}
		}
		this.repaint();
	}

	public void mouseReleased(MouseEvent event) {
		if (turn) {
			GameField releasedField = nearestField(event);
			if (!(clickedField.equals(releasedField))
					&& releasedField.getPiece() == null
					&& clickedField.getPiece() != null) {

				for (GameField gf : reachableGameFields) {
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
				for (GameField gf : attackableGameFields) {
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
		this.repaint();
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

	@Override
	public void mouseMoved(MouseEvent event) {
		GameField ngf = nearestField(event);
		if (ngf.getPiece() != null) {
			gameFrame.setTextArea(ngf.getPiece().toString(), ngf.getPiece()
					.getOwner().equals(player));
		}
	}

	/**
	 * @param gameFrame
	 *            Fuegt GameFrame zum Board hinzu
	 */
	public void addGameFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
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
				"Gegnerische Basis zerst�rt \nSie haben das Spiel gewonnen!",
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

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

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
		getGameField(x, y).setPiece(new GamePiece(player, type));
		this.repaint();
	}

}
