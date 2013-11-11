package game;

public class Mainmenu {
	public static void main(String[] args) {
		Thread frame = new Thread(new Frame());
		frame.start();
	}
}
