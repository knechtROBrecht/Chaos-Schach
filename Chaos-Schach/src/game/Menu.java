package game;

import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Menu extends JMenuBar{
	public Menu()
	{
		super();
		
		JMenu file = new JMenu("File");
		file.setMnemonic('F');
		JMenuItem aufgItem = new JMenuItem("Aufgeben");
		aufgItem.setMnemonic('A');
		file.add(aufgItem);	
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic('x');
		file.add(exitItem);
		
		aufgItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					//TODO
					System.out.println("Aufgeben is pressed");
				}
			}
		);
		exitItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					//TODO 
					System.out.println("Exit is pressed");
				}
			}
		);		
		this.add(file);
	}
}
