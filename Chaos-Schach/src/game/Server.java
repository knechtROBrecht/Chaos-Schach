package game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

	private ServerSocket serverSocket;
	private Socket socket;
	private DataOutputStream dout;
	private DataInputStream din;
	private GameFrame frame;

	/**
	 * @param frame
	 * @throws Exception
	 *             Konstruktor, der In- und Output Streams initializiert und das
	 *             Board dem Frame hinzufuegt.
	 */
	Server(GameFrame frame) throws Exception {
		serverSocket = new ServerSocket(7777);
		this.frame = frame;
		try {
			socket = serverSocket.accept();
			System.out.println("Connection from: " + socket.getInetAddress()
					+ "\n");
			dout = new DataOutputStream(socket.getOutputStream());
			din = new DataInputStream(socket.getInputStream());
			this.frame.addBoard(new Board(this));
		} catch (SocketException e) {
			e.printStackTrace();
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
