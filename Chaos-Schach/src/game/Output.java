package game;

import java.io.DataOutputStream;
import java.io.IOException;

public class Output {
	private DataOutputStream dout;

	/**
	 * @param server
	 *            Konstruktor fuer den Server
	 */
	Output(Server server) {
		dout = server.getDos();

	}

	/**
	 * @param client
	 *            Konstruktor fuer den Client
	 */
	Output(Client client) {
		dout = client.getDos();
	}

	/**
	 * Schreibt das Symbol fuer den Rundenwechsel in den Output Stream
	 */
	public void turn() {
		try {
			dout.writeUTF("t");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param xAlt
	 * @param yAlt
	 * @param xNeu
	 * @param yNeu
	 *            Schreibt den String fuer das Bewegen einer Spielfigur in den
	 *            Output Stream
	 */
	public void move(int xAlt, int yAlt, int xNeu, int yNeu) {
		try {
			dout.writeUTF("m," + xAlt + "," + yAlt + "," + xNeu + "," + yNeu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Schreibt das Symbol zum Schliessen des Spiels in den Output Stream
	 */
	public void close() {
		try {
			dout.writeUTF("b");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param string
	 * @param x
	 * @param y
	 * @param name
	 * @param type
	 *            Schreibt den String fuer das Erstellen einer neuen Spielfigur
	 *            durch die Basis in den Output Stream
	 */
	public void create(String string, int x, int y, String name, int type) {
		try {
			dout.writeUTF(string + "," + x + "," + y + "," + name + "," + type);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param xAlt
	 * @param yAlt
	 * @param xNeu
	 * @param yNeu
	 *            Schreibt den String fuer den Angriff einer Spielfigur auf eine
	 *            Andere in den Output Stream
	 */
	public void attack(int xAlt, int yAlt, int xNeu, int yNeu) {
		try {
			dout.writeUTF("a," + xAlt + "," + yAlt + "," + xNeu + "," + yNeu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Schreibt das Symbol fuer ein gewonnenes Spiel in den Output Stream
	 */
	public void win() {
		try {
			dout.writeUTF("w");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
