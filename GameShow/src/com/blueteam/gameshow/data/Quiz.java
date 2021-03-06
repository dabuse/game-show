package com.blueteam.gameshow.data;

import javax.xml.parsers.*;

import org.w3c.dom.*;

public class Quiz {
		
	private Question[] questions;
	private Answer[] answers;
	private int questionNum;
	
	public Quiz(String filePath) throws Exception
	{
		Document quizFile;
		DocumentBuilderFactory quizBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder quizBuilder = quizBuilderFactory.newDocumentBuilder();
		if(!filePath.substring(filePath.length() - 4).equals(".xml"))
			throw new Exception();
		quizFile = quizBuilder.parse(filePath);
		quizFile.getDocumentElement().normalize();
		Element root = quizFile.getDocumentElement();
		if(!root.getNodeName().equals("quiz"))
			throw new Exception();
		NodeList questionList = root.getElementsByTagName("item");
		if(questionList.getLength() == 0)
			throw new Exception();
		questions = new Question[questionList.getLength()];
		for(int index = 0; index < questionList.getLength(); index++)
		{
			Node currentQuestion = questionList.item(index);
			if(currentQuestion.getNodeType() == Node.ELEMENT_NODE)
			{
				Element questionElement = (Element) currentQuestion;
				NodeList answerList = questionElement.getElementsByTagName("answer");
				answers = new Answer[answerList.getLength()];
				for(int answerIndex=0;answerIndex<answerList.getLength();answerIndex++)
				{
					Node currentAnswer = answerList.item(answerIndex);
					if(currentAnswer.getNodeType() == Node.ELEMENT_NODE)
					{
						Element answerElement = (Element)currentAnswer;
						answers[answerIndex]=new Answer(answerElement.getTextContent(),answerElement.getAttribute("correct").equals("true"));
					}
				}
				String questionString =questionElement.getElementsByTagName("question").item(0).getTextContent();
				String explanation;
				try{
					explanation = questionElement.getElementsByTagName("explanation").item(0).getTextContent();
				}catch(Exception e){explanation = null;}
				int pointValue;
				int time;
				try{
					pointValue = Integer.parseInt(questionElement.getElementsByTagName("point_value").item(0).getTextContent());
				}catch(Exception e){pointValue = Profile.getDefaultValue();}
				try{
					time = Integer.parseInt(questionElement.getElementsByTagName("time").item(0).getTextContent());
				}catch(Exception e){time = Profile.getDefaultTime();}
				//Fine for now, sets time t=0 to default time
				if(time <= 0)
					time = Profile.getDefaultTime();
				questions[index] = new Question(questionString,answers,explanation,pointValue,time);
			}
		}
		questionNum = 0;
	}
	
	public Question getCurrentQuestion()
	{
		return questions[questionNum];
	}
	
	public Question getLastQuestion()
	{
		if(questionNum > 0){
			questionNum--;
			return questions[questionNum];
		}else
			return null;
	}
	
	public boolean isFirstQuestion(){
		if(questionNum == 0){
			return true;
		}
		return false;
	}
	
	
	public boolean isLastQuestion()
	{
		if(questionNum == questions.length - 1)
			return true;
		return false;
	}
	
	public Question nextQuestion()
	{
		questionNum++;
		return questions[questionNum];
	}
}
