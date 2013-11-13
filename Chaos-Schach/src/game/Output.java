package game;

import java.io.DataOutputStream;
import java.io.IOException;

public class Output {
	private DataOutputStream dout;

	Output(Server server) {
		dout = server.getDos();
		
	}

	Output(Client client) {
		dout = client.getDos();
	}
	
	public void turn(){
		try {
			dout.writeUTF("t");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void move(int xAlt,int yAlt,int xNeu,int yNeu){
		try {
			dout.writeUTF("m,"+xAlt+","+yAlt+","+xNeu+","+yNeu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			dout.writeUTF("c");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
