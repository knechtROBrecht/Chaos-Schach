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
			dout.writeUTF("b");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void create(String string, int x, int y, String name, int type) {
		try {
			dout.writeUTF(string + "," + x  + "," + y + "," + name + "," + type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void attack(int xAlt,int yAlt,int xNeu,int yNeu){
		try {
			dout.writeUTF("a,"+xAlt+","+yAlt+","+xNeu+","+yNeu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void win(){
		try {
			dout.writeUTF("w");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
