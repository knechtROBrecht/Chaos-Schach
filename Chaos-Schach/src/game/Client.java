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
	//private String ip;
	//private String name;

	Client(GameFrame frame) {
		// ip = JOptionPane.showInputDialog("Geben sie die ip ein:");
		try{
			socket = new Socket("127.0.0.1", 7777);
			dout = new DataOutputStream(socket.getOutputStream());
			din = new DataInputStream(socket.getInputStream());
			frame.addBoard(new Board(this));
		}catch(ConnectException e){
			JOptionPane.showMessageDialog(null,"Keinen möglichen Server gefunden","Verbindungsfehler",JOptionPane.WARNING_MESSAGE);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
