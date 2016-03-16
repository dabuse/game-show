package com.blueteam.gameshow.server;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.*;
import javax.swing.table.*;

import com.blueteam.gameshow.data.Player;
import com.blueteam.gameshow.data.Roster;



public class RosterTableModel extends AbstractTableModel implements ActionListener {
	private static final long serialVersionUID = -1561458490688644986L;
	private Roster rost;
	private String pathToFolder;
	private Timer scanTime;
	
	public RosterTableModel(Game game){
		rost = game.getRoster();
		pathToFolder = game.getProfile().getClientFolderLoc();
		scanTime = new Timer(1, this);
	}
	
	public String getColumnName(int col){
		if(col ==0){
			return "Player Name";
		}else{
			return "Team Name";
		}
	}
	
	public int getRowCount() {
		int numPlayers = 0;
		for(int t=0; t<rost.numTeams(); t++){
			numPlayers+=rost.getTeam(t).numPlayers();
		}
		return numPlayers;
	}

	public int getColumnCount() {
		return 2;
	}

	public String getValueAt(int rowIndex, int columnIndex) {
		for(int t=0; t<rost.numTeams(); t++){
			for(int p = 0; p<rost.getTeam(t).numPlayers(); p++){
				if(rowIndex==0){
					if(columnIndex == 0){
						return rost.getTeam(t).getPlayer(p).getName();
					}else{
						return rost.getTeam(t).getName();
					}
				}else{
					rowIndex--;
				}
			}
		}
		int t = rost.numTeams()-1;
		if(columnIndex == 0){
			return rost.getTeam(t).getPlayer(rost.getTeam(t).numPlayers()-1).getName();
		}else{
			return rost.getTeam(t).getName();
		}
	}
	
	public Player getStudentAt(int rowIndex){
		for(int t=0; t<rost.numTeams(); t++){
			for(int p = 0; p<rost.getTeam(t).numPlayers(); p++){
				if(rowIndex==0){
					return rost.getTeam(t).getPlayer(p);
				}else{
					rowIndex--;
				}
			}
		}
		int t = rost.numTeams()-1;
		return rost.getTeam(t).getPlayer(rost.getTeam(t).numPlayers()-1);
	}
	
	public void openRegistration(){
		scanTime.start();
	}
	
	public void closeRegistration(){
		scanTime.stop();
	}
	
	public void addMember(Player p, String teamName){
		System.out.println(p.getName() + " " + teamName);
		rost.addPlayer(teamName, p);
		fireTableDataChanged();
	}


	public void actionPerformed(ActionEvent arg0) {
		File folder = new File(pathToFolder);
		File[] ansFiles = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(final File dir, final String name) {
				return name.matches(".profile_*");
			}
		});
		for (File file : ansFiles) {
			file.delete();	
		}
	}
}
