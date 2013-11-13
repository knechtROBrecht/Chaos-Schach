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
	private final int DISTANCE = 4;
	private final int PIECESIZE = (FIELDHEIGHT - DISTANCE);
	private final int FIELDCOUNTX = 23;
	private final int FIELDCOUNTY = 11;

	private int basePos;
	private Player player;
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

	Board(Server server) {
		player = new Player("Server");
		input = new Input(server, this);
		output = new Output(server);
		basePos = FIELDCOUNTX * FIELDCOUNTY - 1;
		turn = true;
		init();
	}

	Board(Client client) {
		player = new Player("Client");
		input = new Input(client, this);
		output = new Output(client);
		basePos = 0;
		turn = false;
		init();
	}

	private void init() {
		(new Thread(input)).start();
		addMouseListener(this);
		addMouseMotionListener(this);
		this.requestFocus();
		setGameFields();
		setGamePieces();

		time.start();
	}

	public boolean getTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}
	
	public void changeTurnStatus(){
		gameFrame.changeTurnStatus();
	}

	public void setGamePieces() {
		GamePiece base1 = new GamePiece("Client", 0);
		gameFields.get(0).setPiece(base1);
		GamePiece soldier1 = new GamePiece("Client", 1);
		gameFields.get(1).setPiece(soldier1);

		GamePiece base2 = new GamePiece("Server", 0);
		gameFields.get(FIELDCOUNTX * FIELDCOUNTY - 1).setPiece(base2);
		GamePiece soldier2 = new GamePiece("Server", 1);
		gameFields.get(FIELDCOUNTX * FIELDCOUNTY - 2).setPiece(soldier2);
	}

	public void spawn() {
		if (gameFields.get(basePos).getLower() != null
				&& gameFields.get(basePos).getLower().getPiece() == null) {
			GamePiece tmp = randomNewPiece();
			gameFields.get(basePos).getLower().setPiece(tmp);
			output.create("c", gameFields.get(basePos).getLower().getX(),
					gameFields.get(basePos).getLower().getY(),
					player.getName(), tmp.getType());
		}else if (gameFields.get(basePos).getUp() != null
				&& gameFields.get(basePos).getUp().getPiece() == null) {
			GamePiece tmp = randomNewPiece();
			gameFields.get(basePos).getUp().setPiece(tmp);
			output.create("c", gameFields.get(basePos).getUp().getX(),
					gameFields.get(basePos).getUp().getY(),
					player.getName(), tmp.getType());
		}else if (gameFields.get(basePos).getLowerLeft() != null
				&& gameFields.get(basePos).getLowerLeft().getPiece() == null) {
			GamePiece tmp = randomNewPiece();
			gameFields.get(basePos).getLowerLeft().setPiece(tmp);
			output.create("c", gameFields.get(basePos).getLowerLeft().getX(),
					gameFields.get(basePos).getLowerLeft().getY(),
					player.getName(), tmp.getType());
		}else if (gameFields.get(basePos).getLowerRight() != null
				&& gameFields.get(basePos).getLowerRight().getPiece() == null) {
			GamePiece tmp = randomNewPiece();
			gameFields.get(basePos).getLowerRight().setPiece(tmp);
			output.create("c", gameFields.get(basePos).getLowerRight().getX(),
					gameFields.get(basePos).getLowerRight().getY(),
					player.getName(), tmp.getType());
		}else if (gameFields.get(basePos).getUpperLeft() != null
				&& gameFields.get(basePos).getUpperLeft().getPiece() == null) {
			GamePiece tmp = randomNewPiece();
			gameFields.get(basePos).getUpperLeft().setPiece(tmp);
			output.create("c", gameFields.get(basePos).getUpperLeft().getX(),
					gameFields.get(basePos).getUpperLeft().getY(),
					player.getName(), tmp.getType());
		}else if (gameFields.get(basePos).getUpperRight() != null
				&& gameFields.get(basePos).getUpperRight().getPiece() == null) {
			GamePiece tmp = randomNewPiece();
			gameFields.get(basePos).getUpperRight().setPiece(tmp);
			output.create("c", gameFields.get(basePos).getUpperRight().getX(),
					gameFields.get(basePos).getUpperRight().getY(),
					player.getName(), tmp.getType());
		}
	}

	public GamePiece randomNewPiece() {
		return new GamePiece(player.getName(), rand.nextInt(2) + 1);
	}

	public void setGameFields() {
		int height = FIELDHEIGHT;
		int width = FIELDWIDTH;
		int mx = width;
		int my = height;
		int flag = 0;
		for (int j = 0; j < FIELDCOUNTX; j++) {
			for (int i = 0; i < FIELDCOUNTY; i++) {
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
			if (this.attackableGameFields.contains(gf)){
				g2.setColor(Color.red);
				g2.drawPolygon(polygon);
			}

		}

		for (GameField gf : gameFields) {
			if (gf.getPiece() != null) {

				g2.setColor(gf.getPiece().getColor());
				g2.fillRect(gf.getX() - PIECESIZE / 2, gf.getY() - PIECESIZE
						/ 2, PIECESIZE, PIECESIZE);

				if (gf.getPiece().getOwner().equals(player.getName())) {
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

	public GameField getGameField(int x, int y) {
		for (GameField gf : gameFields) {

			if (gf.getX() == x && gf.getY() == y) {
				return gf;
			}
		}
		return null;
	}

	public void moveGamePiece(int xAlt, int yAlt, int xNeu, int yNeu) {
		getGameField(xNeu, yNeu).setPiece(getGameField(xAlt, yAlt).getPiece());
		getGameField(xAlt, yAlt).setPiece(null);
		getGameField(xNeu, yNeu).getPiece().setStepsLeft(0);
	}

	public Set<GameField> reachableGameFields(GameField gf, int steps) {
		reachableGameFields.add(gf);
		if (!(gf.getUpperRight() == null || gf.getUpperRight().getPiece() != null)) {
			if (steps >= gf.getUpperRight().getDifficulty()) {
				reachableGameFields(gf.getUpperRight(), steps
						- gf.getUpperRight().getDifficulty());
			}
		}
		if (!(gf.getLowerRight() == null || gf.getLowerRight().getPiece() != null)) {
			if (steps >= gf.getLowerRight().getDifficulty()) {
				reachableGameFields(gf.getLowerRight(), steps
						- gf.getLowerRight().getDifficulty());
			}
		}
		if (!(gf.getLower() == null || gf.getLower().getPiece() != null)) {
			if (steps >= gf.getLower().getDifficulty()) {
				reachableGameFields(gf.getLower(), steps
						- gf.getLower().getDifficulty());
			}
		}
		if (!(gf.getLowerLeft() == null || gf.getLowerLeft().getPiece() != null)) {
			if (steps >= gf.getLowerLeft().getDifficulty()) {
				reachableGameFields(gf.getLowerLeft(), steps
						- gf.getLowerLeft().getDifficulty());
			}
		}
		if (!(gf.getUpperLeft() == null || gf.getUpperLeft().getPiece() != null)) {
			if (steps >= gf.getUpperLeft().getDifficulty()) {
				reachableGameFields(gf.getUpperLeft(), steps
						- gf.getUpperLeft().getDifficulty());
			}
		}
		if (!(gf.getUp() == null || gf.getUp().getPiece() != null)) {
			if (steps >= gf.getUp().getDifficulty()) {
				reachableGameFields(gf.getUp(), steps
						- gf.getUp().getDifficulty());
			}
		}
		return reachableGameFields;
	}
	public Set<GameField> attackableGameFields(GameField gf, int reach){
		if ((gf.getUpperRight() != null && gf.getUpperRight().getPiece() == null)) {		
				if(reach > 0) attackableGameFields(gf.getUpperRight(), reach-1);
		}else if(gf.getUpperRight() != null && gf.getUpperRight().getPiece() != null && gf.getUpperRight().getPiece().getOwner() != player.getName()){
			if(reach > 0){ attackableGameFields.add(gf.getUpperRight());
			attackableGameFields(gf.getUpperRight(), reach-1);}
		}
		if ((gf.getLowerRight() != null && gf.getLowerRight().getPiece() == null)) {
			if(reach > 0) attackableGameFields(gf.getLowerRight(), reach-1);
		}else if(gf.getLowerRight() != null && gf.getLowerRight().getPiece() != null && gf.getLowerRight().getPiece().getOwner() != player.getName()){
			if(reach > 0){ attackableGameFields.add(gf.getLowerRight());
			attackableGameFields(gf.getLowerRight(), reach-1);}
		}
		if ((gf.getLower() != null && gf.getLower().getPiece() == null)) {
			if(reach > 0) attackableGameFields(gf.getLower(), reach-1);
		}else if(gf.getLower() != null && gf.getLower().getPiece() != null && gf.getLower().getPiece().getOwner() != player.getName()){
			if(reach > 0){ attackableGameFields.add(gf.getLower());
			attackableGameFields(gf.getLower(), reach-1);}
		}
		if ((gf.getLowerLeft() != null && gf.getLowerLeft().getPiece() == null)) {
			if(reach > 0) attackableGameFields(gf.getLowerLeft(), reach-1);
		}else if(gf.getLowerLeft() != null && gf.getLowerLeft().getPiece() != null && gf.getLowerLeft().getPiece().getOwner() != player.getName()){
			if(reach > 0){ attackableGameFields.add(gf.getLowerLeft());
			attackableGameFields(gf.getLowerLeft(), reach-1);}
		}
		if ((gf.getUpperLeft() != null && gf.getUpperLeft().getPiece() == null)) {
			if(reach > 0) attackableGameFields(gf.getUpperLeft(), reach-1);
		}else if(gf.getUpperLeft() != null && gf.getUpperLeft().getPiece() != null && gf.getUpperLeft().getPiece().getOwner() != player.getName()){
			if(reach > 0){ attackableGameFields.add(gf.getUpperLeft());
			attackableGameFields(gf.getUpperLeft(), reach-1);}
		}
		if ((gf.getUp() != null && gf.getUp().getPiece() == null)) {
			if(reach > 0) attackableGameFields(gf.getUp(), reach-1);
		}else if(gf.getUp() != null && gf.getUp().getPiece() != null && gf.getUp().getPiece().getOwner() != player.getName()){
			if(reach > 0){ attackableGameFields.add(gf.getUp());
			attackableGameFields(gf.getUp(), reach-1);}
		}
		return attackableGameFields;
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
						clickedField.getPiece().getStepsLeft());
				attackableGameFields = attackableGameFields(clickedField,clickedField.getPiece().getReach());
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
						moveGamePiece(clickedField.getX(), clickedField.getY(),
								releasedField.getX(), releasedField.getY());
						gf.getPiece().setStepsLeft(0);
					}
				}
			}else if(!(clickedField.equals(releasedField)) && releasedField.getPiece() != null && clickedField.getPiece() != null){
				for(GameField gf : attackableGameFields){
					
				}
			}
			attackableGameFields.clear();
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
	public void mouseMoved(MouseEvent event) {
		GameField ngf = nearestField(event);
		if (ngf.getPiece() != null) {
			gameFrame.setTextArea(ngf.getPiece().toString(), ngf.getPiece()
					.getOwner().equals(player.getName()));
		}
	}

	public void addGameFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}

	public void end() {
		output.close();
		System.exit(0);
	}

	public void turn() {
		output.turn();
		turn = false;
		gameFrame.changeTurnStatus();
		resetSteps();
	}

	public void resetSteps() {
		for (GameField gf : gameFields) {
			if ((gf.getPiece() != null)
					&& (gf.getPiece().getOwner().equals(player.getName()))) {

				gf.getPiece().setStepsLeft(gf.getPiece().getSteps());
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

	public void create(int x, int y, String player, int type) {
		getGameField(x,y).setPiece(new GamePiece(player,type));
	}

}
