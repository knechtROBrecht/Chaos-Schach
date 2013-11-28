package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class GameFrame extends JFrame{

	private JButton aufgeben;
	private JButton turn;
	private JPanel westPanel;
	private JLabel conn;
	private Board board;
	private JTextArea textArea;
	private JLabel yourTurn;

	GameFrame(String opt) {
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setSize(1280, 720);
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(new BorderLayout());
		this.setResizable(false);

		westPanel = new JPanel();
		westPanel.setLayout(new BorderLayout());
		westPanel.setPreferredSize(new Dimension(200, 720));
		aufgeben = new JButton("Aufgeben");
		aufgeben.setPreferredSize(new Dimension(200, 80));
		aufgeben.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board != null) {
					JOptionPane.showMessageDialog(null,
							"Sie haben das Spiel aufgegeben", "Verloren",
							JOptionPane.WARNING_MESSAGE);
					board.end();
				} else
					System.exit(0);
			}
		});
		turn = new JButton("Turn");
		turn.setPreferredSize(new Dimension(200, 100));
		turn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board != null) {
					if (board.getTurn())
						board.turn();
				}
			}
		});
		textArea = new JTextArea(20, 5);
		textArea.setEditable(false);
		JPanel southButtons = new JPanel();
		southButtons.setLayout(new BorderLayout());
		southButtons.add(turn, BorderLayout.NORTH);
		southButtons.add(aufgeben, BorderLayout.CENTER);
		yourTurn = new JLabel();
		yourTurn.setPreferredSize(new Dimension(200, 200));
		yourTurn.setOpaque(true);
		yourTurn.setHorizontalAlignment(JLabel.CENTER);
		westPanel.add(textArea, BorderLayout.NORTH);
		westPanel.add(yourTurn, BorderLayout.CENTER);
		westPanel.add(southButtons, BorderLayout.SOUTH);
		westPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(westPanel, BorderLayout.WEST);
		conn = new JLabel("waiting for connection");
		this.add(conn, BorderLayout.CENTER);
		this.setVisible(true);
		try {
			if (opt.equals("s")) {
				this.setTitle("Server");
				Thread trd = new Thread(new Server(this));
				trd.start();
			} else if (opt.equals("c")) {
				this.setTitle("Client");
				new Client(this);
				changeTurnStatus();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param board
	 *            Fuegt dem GameFrame das Board hinzu und dem Board das
	 *            GameFrame
	 */
	public void addBoard(Board board) {
		this.board = board;
		this.remove(conn);
		this.add(this.board, BorderLayout.CENTER);
		this.setVisible(true);
		this.board.addGameFrame(this);
		this.board.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	/**
	 * Laeuft bis der Thread interrupted wird und ist fuer das Erzeugen des
	 * gesamten Spielemenues zustaendig (Turn Button, Surrender Button etc.)
	 */
	

	/**
	 * @param str
	 * @param bl
	 * Schreibt in die TextArea ueber welche Spielfigur die Maus gerade hovered
	 */
	public void setTextArea(String str, Boolean bl) {
		textArea.setText("");
		textArea.setText(str);
		if (bl) {
			textArea.append("your figure");
			textArea.setForeground(new Color(0x00, 0xC0, 0x00));
		} else {
			textArea.append("enemys figure");
			textArea.setForeground(Color.red);
		}
	}

	/**
	 * Zeigt an wer an der Reihe ist
	 */
	public void changeTurnStatus() {
		if (board.getTurn()) {
			yourTurn.setText("Sie sind am Zug!");
			yourTurn.setBackground(Color.green);
		} else {
			yourTurn.setText("Ihr Gegner ist am Zug!");
			yourTurn.setBackground(Color.gray);
		}
	}

}
