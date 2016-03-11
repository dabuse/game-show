package com.blueteam.gameshow.server;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public abstract class PopUp{

	protected JFrame frame;
	private JPanel panel;
	private JPanel bp;
	private JLabel label;
	private JButton yes; 
	private JButton no;
	public boolean yn = true;
	
	public PopUp() {
		
		frame = new JFrame();
		panel = new JPanel();
		panel.setLayout(new GridLayout(2,1,5,5));
		
		bp = new JPanel();
		bp.setLayout(new GridLayout(1,2,5,5));
		
		label = new JLabel("Are you sure?", SwingConstants.CENTER);
		
		yes = new JButton("yes");
		yes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				yes();
			}
		});
		
		no = new JButton("no");
		no.setActionCommand("no");
		no.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				no();
			}
		});
		
		panel.add(label);
		
		bp.add(yes);
		bp.add(no);
		
		panel.add(bp);
		
		frame.setContentPane(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public abstract void yes();
	public abstract void no();


}
