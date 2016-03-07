package com.blueteam.gameshow.data;

import java.util.ArrayList;

public class Team {
	private ArrayList<Player> members;
	private double score;
	private double latestPercentage;
	private String name;
	private int numResponses;
	
	public Team(String n){
		name = n;
		members = new ArrayList<Player>();
		score = 0;
		latestPercentage = 0;
	}
	
	public double getPercentage(){
		return latestPercentage;
	}
	
	public double getScore(){
		return score;
	}
	
	public String getName(){
		return name;
	}
	
	public Player getPlayer(int p){
		return members.get(p);
	}
	
	public int numPlayers(){
		return members.size();
	}
	
	boolean hasEveryoneResponded(){
		calculatePercentage();
		if(numResponses == members.size()){
			return true;
		}return false;
	}
	
	public void addMember(Player newPlayer){
		for(int i=0; i<members.size(); i++){
			if(members.get(i).getIP().equals(newPlayer.getIP())){
				members.remove(i);
			}
		}
		members.add(newPlayer);
	}
	
	public void unregisterStudent(Player p){
		members.remove(p);
	}
	
	public void calculatePercentage(){
		int numCorrect = 0;
		numResponses = 0;
		for(int i=0; i<members.size(); i++){
			if(members.get(i).getAnswer()!=null){
				numResponses++;
				if(members.get(i).getAnswer().isCorrect()){
					numCorrect++;
				}
			}
		}
		latestPercentage = ((double)numCorrect)/(members.size());
	}
	
	public void calculateScore(){
		calculatePercentage();
		score += latestPercentage*members.size();
	}
	
	public void zeroPercentage(){
		//clearAnswers DANIEL!!!!!!
		//should erase answers from whatever folder getAnswerChecks or something equivalent
	}
		
}