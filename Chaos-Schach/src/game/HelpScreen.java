package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class HelpScreen extends JFrame {

	
	HelpScreen(){
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(600, 600);
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		JTextArea textArea = new JTextArea();
		textArea.setText("Hier kommt die Help hin");
		textArea.setEditable(false);
		
		JButton close = new JButton("Close");
		close.setPreferredSize(new Dimension(200, 50));
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		
		
		this.add(textArea, BorderLayout.CENTER);
		this.add(close, BorderLayout.SOUTH);
		this.setVisible(true);
		
	}
}
