package game;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class Frame extends JFrame implements Runnable, ActionListener {

	JButton button1;
    JButton button2;
    JButton button3;
    JLabel label;
    JPanel panel;
    Server server;
    Client client;
	
	@Override
	public void run() {
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(1000, 900);
		this.setBackground(Color.DARK_GRAY);
		String opt = JOptionPane.showInputDialog("s= server, c=client:");
		this.setLayout(new BorderLayout());
		
		panel = new JPanel();
		 
        // Leeres JLabel-Objekt wird erzeugt
        label = new JLabel();
 
        //Drei Buttons werden erstellt
        button1 = new JButton("Beenden");
 
        //Buttons werden dem Listener zugeordnet
        button1.addActionListener(this);
 
        //Buttons werden dem JPanel hinzugefügt
        panel.add(button1);
 
        //JLabel wird dem Panel hinzugefügt
        panel.add(label);
		this.add(panel,BorderLayout.WEST);
		this.add(new JLabel("Testrechts"),BorderLayout.EAST);
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

	public void addBoard(Board board) {
		this.add(board,BorderLayout.CENTER);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		if(e.getSource() == this.button1){
//			WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
//            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
//            server.end();
//		}
	}
}
