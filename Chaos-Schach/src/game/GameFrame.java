package game;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements Runnable {

	private JButton aufgeben;
	private JPanel westPanel;
	private Server server;
	private Client client;
	private JLabel conn;
	private String opt;

	GameFrame(String opt) {
		this.opt = opt;
	}

	public void addBoard(Board board) {
		this.remove(conn);
		this.add(board, BorderLayout.CENTER);
		this.setVisible(true);
	}

	@Override
	public void run() {
		this.setJMenuBar(new Menu());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(1000, 900);
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(new BorderLayout());
		this.setResizable(false);

		westPanel = new JPanel();
		aufgeben = new JButton("Aufgeben");
		aufgeben.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO aufgeben
				System.out.println("aufgeben is pressed");
			}
		});
		westPanel.add(aufgeben);
		this.add(westPanel, BorderLayout.WEST);
		conn = new JLabel("waiting for connection");
		this.add(conn, BorderLayout.CENTER);

		this.setVisible(true);

		try {
			if (opt.equals("s")) {
				this.setTitle("Server");
				server = new Server(this);
			} else if (opt.equals("c")) {
				this.setTitle("Client");
				client = new Client(this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
