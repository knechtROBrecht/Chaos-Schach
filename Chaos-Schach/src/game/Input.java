package game;

import java.io.DataInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Input implements Runnable {
	private DataInputStream din;
	private Board board;
	private Boolean end = false;

	@Override
	public void run() {
		while (!end) {
			try {
				interprete(din.readUTF());

			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"Verbindung zum Gegner unterbrochen", "",
						JOptionPane.WARNING_MESSAGE);
				System.exit(0);
			}
		}
	}

	public void interprete(String str) {
		if (str.matches("t")) {
			JOptionPane.showMessageDialog(null, "Sie sind nun am Zug", "",
					JOptionPane.WARNING_MESSAGE);
			board.spawn();
			board.setTurn(true);
		}
		if (str.matches("m.*")) {
			board.moveGamePiece(Integer.parseInt(str.split(",")[1]),
					Integer.parseInt(str.split(",")[2]),
					Integer.parseInt(str.split(",")[3]),
					Integer.parseInt(str.split(",")[4]));
		}
		if (str.matches("c.*")) {
			JOptionPane.showMessageDialog(null,
					"Ihr Gegner hat das Spiel aufgegeben", "Gewonnen",
					JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
	}

	Input(Server server, Board board) {
		din = server.getDis();
		this.board = board;

	}

	Input(Client client, Board board) {
		din = client.getDis();
		this.board = board;
	}

}
