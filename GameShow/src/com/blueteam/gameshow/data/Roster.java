package com.blueteam.gameshow.data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Roster implements ActionListener{
	private ArrayList<Team> teams;

	private Timer scanTime;
	
	public Roster(){
		teams = new ArrayList<Team>();
		scanTime = new Timer(1, this);
	}
	
	public Team getTeam(int t){
		return teams.get(t);
	}
	
	public Team getTeam(String teamName){
		for(int i=0; i<teams.size(); i++){
			if(teams.get(i).getName().equals(teamName)){
				return teams.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Team> getTeams(){
		return teams;
	}
	
	public int numTeams(){
		return teams.size();
	}
	
	public void addPlayer(String teamName, Player p){
		boolean found = false;
		for(int t=0; t<teams.size() && !found; t++){
			if(teams.get(t).getName().equals(teamName)){
				found = true;
				teams.get(t).addMember(p);
			}
		}
		if(!found){
			addTeam(teamName);
			getTeam(teamName).addMember(p);
		}
	}
	
	private void addTeam(String teamName){
		//adds team to correct alphabetical location
		
		boolean found = false;

		for(int i=0; i<teams.size() && !found;i++){
			if(teamName.compareTo(teams.get(i).getName())<0){
				found = true;
				teams.add(i, new Team(teamName));
			}
		}

		if(!found){
			teams.add(new Team(teamName));
		}

	}

	public void startQuestion(){
		scanTime.start();
	}
	
	public void endQuestion(){
		scanTime.stop();
		for(int i=0; i<teams.size(); i++){
			teams.get(i).calculateScore();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		boolean allResponded;
		
		allResponded = true;
		for(int i=0; i<teams.size(); i++){
			if(!teams.get(i).hasEveryoneResponded()){
				allResponded = false;
			}
		}
		if(allResponded){
			endQuestion();
		}
	}
	
	public boolean isFound(String identifier) {
		for (Team team : teams)
			if (team.isFound(identifier))
				return true;
		return false;
	}
	
}
