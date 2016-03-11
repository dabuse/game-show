package com.blueteam.gameshow.server;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ServerWindow {
	
	private JTabbedPane tabs;
	private JPanel content;
	private JFrame frame;


	private ProfileScreen pScreen;
	private RosterScreen rosterScreen;
	private ScoreboardScreen sbScreen;
	private ServerGameScreen sgScreen;
	
	private boolean tabsEnabled;
	private Game game;
	
	public ServerWindow(){
		
		tabsEnabled = false;
		game = new Game();
		
		tabs = new JTabbedPane();
		content = new JPanel(new BorderLayout());
		frame = new JFrame("GameShow Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pScreen = new ProfileScreen(game, this);
		tabs.addTab("Profile", pScreen);
		
		
		content.add(tabs, BorderLayout.CENTER);
		frame.setContentPane(content);
		frame.pack();
		frame.setVisible(true);
		
	}

	public void enableTabs(){
		boolean error = false;

		try{
			game.createQuiz();
		}catch(Exception e){
			error = true;

			JFrame popUp = new JFrame();
			JPanel content = new JPanel();
			content.add(new JLabel("Question file is not valid."));
		}

		if(!tabsEnabled && !error){
			tabsEnabled = true;
			rosterScreen = new RosterScreen(game);
			sbScreen = new ScoreboardScreen(game);
			sgScreen = new ServerGameScreen(game);


			tabs.addTab("Roster",rosterScreen);
			tabs.addTab("Scoreboard", sbScreen);
			tabs.addTab("Game", sgScreen.getCurrentMode());
		}
	}
	
	public static void main(String args[]) {
		new ServerWindow();
	}

}
