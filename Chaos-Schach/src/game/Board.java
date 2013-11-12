package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, ActionListener,
		KeyListener {

	final int FIELDHEIGHT = 20;
	final int FIELDWIDTH = 20;
	final int DISTANCE = 4;
	final int PIECESIZE = (FIELDHEIGHT - DISTANCE) / 2;

	private Player player;
	private Input input;
	private Output output; 
	private boolean turn;
	private int fieldCount = 20;
	private List<GameField> gameFields = new ArrayList<GameField>();
	private Set<GameField> reachableGameFields = new HashSet<GameField>();
	private Timer time = new Timer(30, this);
	private GameField clickedField;
	private Random rand = new Random(20071969);

	Board(Server server) {
		player = new Player("Server");
		input = new Input(server, this);
		output = new Output(server);
		turn = true;
		init();
	}

	Board(Client client) {
		player = new Player("Client");
		input = new Input(client, this);
		output = new Output(client);
		turn = false;
		init();
	}

	private void init() {
		(new Thread(input)).start();
		addMouseListener(this);
		addKeyListener(this);
		this.requestFocus();
		setGameFields();
		setGamePieces();
		time.start();
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public void setGamePieces() {
		GamePiece base1 = new GamePiece("Client","Base", 1);
		gameFields.get(0).setPiece(base1);
		GamePiece soldier1 = new GamePiece("Client","Soldier", 2);
		gameFields.get(1).setPiece(soldier1);

		GamePiece base2 = new GamePiece("Server","Base",3);
		gameFields.get(399).setPiece(base2);
		GamePiece soldier2 = new GamePiece("Server","Soldier", 4);
		gameFields.get(398).setPiece(soldier2);
	}

	public void setGameFields() {
		int height = FIELDHEIGHT;
		int width = FIELDWIDTH;
		int mx = width;
		int my = height;
		int flag = 0;
		for (int j = 0; j < fieldCount; j++) {
			for (int i = 0; i < fieldCount; i++) {
				gameFields.add(new GameField(mx, my, rand.nextInt(2)));

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
			gf.setLower(getGameField(gf.getX(), gf.getY() + 2 * height));
			gf.setLowerLeft(getGameField((int) (gf.getX() - 1.5 * width),
					gf.getY() + height));
			gf.setLowerRight(getGameField((int) (gf.getX() + 1.5 * width),
					gf.getY() + height));
			gf.setUp(getGameField(gf.getX(), gf.getY() - 2 * height));
			gf.setUpperLeft(getGameField((int) (gf.getX() - 1.5 * width),
					gf.getY() - height));
			gf.setUpperRight(getGameField((int) (gf.getX() + 1.5 * width),
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

		}

		for (GameField gf : gameFields) {
			if (gf.getPiece() != null) {
				
				g2.setColor(gf.getPiece().getColor());
				g2.fillRect(gf.getX() - PIECESIZE / 2, gf.getY() - PIECESIZE
						/ 2, PIECESIZE, PIECESIZE);
				
				if(gf.getPiece().getOwner().equals(player.getName())){
					g2.setColor(Color.BLUE);
				}else{
					g2.setColor(Color.RED);
				}
				g2.drawRect(gf.getX() - PIECESIZE / 2, gf.getY() - PIECESIZE
						/ 2, PIECESIZE, PIECESIZE);
			} else {
				g2.setColor(Color.LIGHT_GRAY);
				g2.fillRect(gf.getX() - PIECESIZE / 2, gf.getY() - PIECESIZE
						/ 2, PIECESIZE, PIECESIZE);
				g2.drawRect(gf.getX() - PIECESIZE / 2, gf.getY() - PIECESIZE
						/ 2, PIECESIZE, PIECESIZE);
			}
		}

	}

	public GameField getGameField(int x, int y) {
		for (GameField gf : gameFields) {

			if (gf.getX() == x && gf.getY() == y) {
				return gf;
			}
		}
		return null;
	}

	public void moveGamePiece(int xAlt, int yAlt, int xNeu, int yNeu) {
		getGameField(xNeu,yNeu).setPiece(getGameField(xAlt,yAlt).getPiece());
		getGameField(xAlt, yAlt).setPiece(null);	
	}

	public Set<GameField> reachableGameFields(GameField gf, int steps) {
		reachableGameFields.add(gf);
		if (!(gf.getUpperRight() == null)) {
			if (steps >= gf.getUpperRight().getDifficulty()) {
				reachableGameFields(gf.getUpperRight(), steps
						- gf.getUpperRight().getDifficulty());
			}
		}
		if (!(gf.getLowerRight() == null)) {
			if (steps >= gf.getLowerRight().getDifficulty()) {
				reachableGameFields(gf.getLowerRight(), steps
						- gf.getLowerRight().getDifficulty());
			}
		}
		if (!(gf.getLower() == null)) {
			if (steps >= gf.getLower().getDifficulty()) {
				reachableGameFields(gf.getLower(), steps
						- gf.getLower().getDifficulty());
			}
		}
		if (!(gf.getLowerLeft() == null)) {
			if (steps >= gf.getLowerLeft().getDifficulty()) {
				reachableGameFields(gf.getLowerLeft(), steps
						- gf.getLowerLeft().getDifficulty());
			}
		}
		if (!(gf.getUpperLeft() == null)) {
			if (steps >= gf.getUpperLeft().getDifficulty()) {
				reachableGameFields(gf.getUpperLeft(), steps
						- gf.getUpperLeft().getDifficulty());
			}
		}
		if (!(gf.getUp() == null)) {
			if (steps >= gf.getUp().getDifficulty()) {
				reachableGameFields(gf.getUp(), steps
						- gf.getUp().getDifficulty());
			}
		}
		return reachableGameFields;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO
		this.repaint();
	}

	public void mousePressed(MouseEvent event) {
		if (turn) {
			clickedField = nearestField(event);
			if (clickedField.getPiece() != null
					&& clickedField.getPiece().getOwner()
							.equals(player.getName())) {
				reachableGameFields = reachableGameFields(clickedField,
						clickedField.getPiece().getSteps());
			}
		}
	}

	public void mouseReleased(MouseEvent event) {
		if (turn) {
			GameField releasedField = nearestField(event);
			if (!(clickedField.equals(releasedField))
					&& releasedField.getPiece() == null
					&& clickedField.getPiece() != null) {

				for (GameField gf : reachableGameFields) {
					if (gf == releasedField) {
						// TODO output
						output.move(clickedField.getX(), clickedField.getY(),
								releasedField.getX(), releasedField.getY());
						output.turn();
						turn = false;
						moveGamePiece(clickedField.getX(), clickedField.getY(),
								releasedField.getX(), releasedField.getY());
					}
				}
			}
			reachableGameFields.clear();
		}
	}

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
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

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

}
