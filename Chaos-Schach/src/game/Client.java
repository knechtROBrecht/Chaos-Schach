package game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Client {

	private Socket socket;
	private DataOutputStream dout;
	private DataInputStream din;
	private String ip;
	private int port;

	/**
	 * @param frame
	 *            Konstruktor, der In- und Output Streams initializiert und das
	 *            Board dem Frame hinzufuegt.
	 */
	Client(GameFrame frame) {
		ip = JOptionPane.showInputDialog("Geben sie die ip ein:");
		String str = JOptionPane.showInputDialog("Geben sie den port ein:");
		try {
			port = Integer.parseInt(str);
			socket = new Socket(ip, port);
			dout = new DataOutputStream(socket.getOutputStream());
			din = new DataInputStream(socket.getInputStream());
			frame.addBoard(new Board(this));
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null,
					"Keinen möglichen Server gefunden", "Verbindungsfehler",
					JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Ungueltige Eingabe!\nConnect zum Spiel abgebrochen!",
					"Verbindungsfehler", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null,
					"Ungueltige Eingabe!\nConnect zum Spiel abgebrochen!",
					"Verbindungsfehler", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}

	}

	/**
	 * @return Gibt den Output Stream zurueck
	 */
	public DataOutputStream getDos() {
		return dout;
	}

	/**
	 * @return Gibt den Input Stream zurueck
	 */
	public DataInputStream getDis() {
		return din;
	}

}
