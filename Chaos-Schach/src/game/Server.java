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

	Server(GameFrame frame) throws Exception { 
		serverSocket = new ServerSocket(7777);
		this.frame = frame;
		try {
			socket = serverSocket.accept();
			System.out.println("Connection from: " + socket.getInetAddress() + "\n");
			dout = new DataOutputStream(socket.getOutputStream());
			din = new DataInputStream(socket.getInputStream());
			this.frame.addBoard(new Board(this));
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public DataOutputStream getDos() {
		return dout;
	}

	public DataInputStream getDis() {
		return din;
	}
	
}
