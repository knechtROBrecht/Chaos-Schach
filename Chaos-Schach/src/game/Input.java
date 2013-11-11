package game;

import java.io.DataInputStream;
import java.io.IOException;

public class Input implements Runnable {
	DataInputStream din;
	Board board;

	@Override
	public void run() {
		while (true) {
			try {
				interprete(din.readUTF());

			} catch (IOException e) {
				e.printStackTrace();
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
		if (str.matches("b.*")){
			try {
				System.out.println("ahahah");
				din.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
