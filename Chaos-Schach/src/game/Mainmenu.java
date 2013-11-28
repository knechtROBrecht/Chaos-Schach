package game;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class Mainmenu extends JFrame {

	private JPanel panel;
	private JButton host;
	private JButton conn;
	private JButton tut;
	private JButton opt;

	/**
	 * Konstruktor erzeugt die Knoepfe fuer das Startfenster und setzt die
	 * jeweiligen ActionListener
	 */
	Mainmenu() {
		this.setTitle("Chaos-Schach");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(200, 400);
		panel = new JPanel();
		host = new JButton("Host a Game");
		conn = new JButton("Connect to a Game");
		tut = new JButton("Tutorial");
		opt = new JButton("Options");

		host.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new GameFrame("s");
			}
		});

		conn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new GameFrame("c");
			}
		});

		tut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO tutorial
				System.out.println("tutorial is pressed");
			}
		});

		opt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO options
				System.out.println("options is pressed");
			}
		});

		panel.add(host);
		panel.add(conn);
		panel.add(tut);
		panel.add(opt);

		this.add(panel, BorderLayout.CENTER);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Mainmenu();
	}
}
