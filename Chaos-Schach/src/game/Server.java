package game;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Server extends JFrame {

	private ServerSocket serverSocket;
	private Socket socket;
	private DataOutputStream dout;
	private DataInputStream din;
	private TextArea text = new TextArea();
	private Frame frame;

	Server(Frame frame) throws Exception { // hier evtl noch manuelle eingabe
											// des Ports
		setLocation(100, 100);
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		this.setTitle("Server console");
		this.add(text, BorderLayout.CENTER);
		text.append("Starting Server...\n");
		serverSocket = new ServerSocket(7777);
		text.append("Server started...\n");
		this.frame = frame;
		test();
	}

	public void test() {
		try {
			socket = serverSocket.accept();
			text.append("Connection from: " + socket.getInetAddress() + "\n");
			dout = new DataOutputStream(socket.getOutputStream());
			din = new DataInputStream(socket.getInputStream());
			frame.addBoard(new Board(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataOutputStream getDos() {
		return dout;
	}

	public DataInputStream getDis() {
		return din;
	}
	
	public void end(){
		try {
			dout.writeUTF("b");
			dout.close();
			din.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
