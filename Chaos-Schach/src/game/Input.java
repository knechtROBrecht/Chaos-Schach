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
				try {
					din.close();
					end = true;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void interprete(String str) {
		if (str.matches("t")) {
			board.setTurn(true);
		}
		if (str.matches("m.*")) {
			board.moveGamePiece(Integer.parseInt(str.split(",")[1]),
					Integer.parseInt(str.split(",")[2]),
					Integer.parseInt(str.split(",")[3]),
					Integer.parseInt(str.split(",")[4]));
		}
		if (str.matches("c.*")){
			JOptionPane.showMessageDialog(null,"Ihr Gegner hat das Spiel verlassen","Gewonnen",JOptionPane.WARNING_MESSAGE);
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
