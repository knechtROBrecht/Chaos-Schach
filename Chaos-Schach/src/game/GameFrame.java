package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements Runnable {

	private JButton aufgeben;
	private JButton turn; 
	private JPanel westPanel;
	private JLabel conn;
	private String opt;
	private Board board;
	private JTextArea textArea;

	GameFrame(String opt) {
		this.opt = opt;
	}

	public void addBoard(Board board) {
		this.board = board;
		this.remove(conn);
		this.add(this.board, BorderLayout.CENTER);
		this.setVisible(true);
		this.board.addGameFrame(this);
		this.board.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	@Override
	public void run() {
//		this.setJMenuBar(new Menu());
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setSize(1000, 900);
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(new BorderLayout());
		this.setResizable(false);

		westPanel = new JPanel();
		westPanel.setLayout(new BorderLayout());
		westPanel.setPreferredSize(new Dimension(200,900));
		aufgeben = new JButton("Aufgeben");
		aufgeben.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(board != null){
					JOptionPane.showMessageDialog(null,"Sie haben das Spiel aufgegeben","Verloren",JOptionPane.WARNING_MESSAGE);
					board.end();
				}else System.exit(0);
			}
		});
		turn = new JButton("Turn");
		turn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(board != null) board.turn();
			}
		});
		textArea = new JTextArea(20, 5);
		textArea.setEditable(false);
		westPanel.add(textArea, BorderLayout.NORTH);
		westPanel.add(turn, BorderLayout.CENTER);
		westPanel.add(aufgeben, BorderLayout.PAGE_END);
		westPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(westPanel, BorderLayout.WEST);
		conn = new JLabel("waiting for connection");
		this.add(conn, BorderLayout.CENTER);

		this.setVisible(true);

		try {
			if (opt.equals("s")) {
				this.setTitle("Server");
				new Server(this);
			} else if (opt.equals("c")) {
				this.setTitle("Client");
				new Client(this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	

	}
	
	public void setTextArea(String str, Boolean bl){
		textArea.setText("");
		textArea.setText(str);		
		if(bl){ 
			textArea.append("your figure");
			textArea.setForeground(new Color(0x00, 0xC0, 0x00));
		}else{
			textArea.append("enemys figure");
			textArea.setForeground(Color.red);
		}
	}

}
