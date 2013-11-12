package game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {

	private Socket socket;
	private DataOutputStream dout;
	private DataInputStream din;
	//private String ip;
	//private String name;

	Client(GameFrame frame) throws Exception {
		// ip = JOptionPane.showInputDialog("Geben sie die ip ein:");
		socket = new Socket("127.0.0.1", 7777); //was passiert wenn kein server erreicht wird?
		dout = new DataOutputStream(socket.getOutputStream());
		din = new DataInputStream(socket.getInputStream());
		frame.addBoard(new Board(this));
	}

	public DataOutputStream getDos() {
		return dout;
	}

	public DataInputStream getDis() {
		return din;
	}

}
