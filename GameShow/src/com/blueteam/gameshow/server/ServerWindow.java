package com.blueteam.gameshow.server;

import javax.swing.JFrame;
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
		content = new JPanel();
		frame = new JFrame("GameShow Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pScreen = new ProfileScreen(game, this);
		tabs.addTab("Profile", pScreen);
		
		content.add(tabs);
		frame.setContentPane(content);
		frame.pack();
		frame.setVisible(true);
		
	}

	public void enableTabs(){
		if(!tabsEnabled){
			tabsEnabled = true;
			game.createQuiz();
			
			rosterScreen = new RosterScreen(game);
			sbScreen = new ScoreboardScreen(game);
			sgScreen = new ServerGameScreen(game);
		
			tabs.addTab("Roster",rosterScreen);
			tabs.addTab("Scoreboard", sbScreen);
			tabs.addTab("Game", sgScreen);
		}
	}
	
	public static void main(String args[]) {
		new ServerWindow();
	}

}
