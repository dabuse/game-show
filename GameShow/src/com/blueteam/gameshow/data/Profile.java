package com.blueteam.gameshow.data;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JOptionPane;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Profile {
	private String servFoldLoc;
	private String clientFoldLoc;
	private static int defVal;
	private static int defTime;
	private static String qFileLoc;
	private static final int DEFAULTVALUE = 10;
	private static final int DEFAULTTIME = 30;
	
	public Profile(){
	
			Document profileSave = null;
			DocumentBuilderFactory profileFactory = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder profileBuilder = profileFactory.newDocumentBuilder();
				profileBuilder.setErrorHandler(new ErrorHandler() {
					@Override
					public void error(SAXParseException arg0) throws SAXException {}
					@Override
					public void fatalError(SAXParseException arg0) throws SAXException {}
					@Override
					public void warning(SAXParseException arg0) throws SAXException {}
				});
				profileSave = profileBuilder.parse("profileSave.xml");
			} catch(FileNotFoundException e) {
			} catch(SAXException e) {
				JOptionPane.showMessageDialog(null, "Corrupt profileSave!", "Profile Load Error", JOptionPane.ERROR_MESSAGE);
			} catch(Exception e) {e.printStackTrace();}
			if (profileSave != null) {
				Element root = profileSave.getDocumentElement();
				servFoldLoc = ((Element)root.getElementsByTagName("serverLoc").item(0)).getTextContent();
				clientFoldLoc = ((Element)root.getElementsByTagName("clientLoc").item(0)).getTextContent();
				qFileLoc = ((Element)root.getElementsByTagName("questionLoc").item(0)).getTextContent();
				defTime = Integer.parseInt(((Element)root.getElementsByTagName("timeDefault").item(0)).getTextContent());
				defVal = Integer.parseInt(((Element)root.getElementsByTagName("pointDefault").item(0)).getTextContent());
			} else {
				servFoldLoc = "";
				clientFoldLoc = "";
				qFileLoc = "";
				defTime = DEFAULTTIME;
				defVal = DEFAULTVALUE;
			}
		
	}

	public void saveProfile(){
		try{
			Document profileDoc;
			DocumentBuilderFactory profileDocFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder saveBuilder = profileDocFactory.newDocumentBuilder();
			profileDoc = saveBuilder.newDocument();
			Element root = profileDoc.createElement("profile");
			profileDoc.appendChild(root);
			Element serverLoc = profileDoc.createElement("serverLoc");
			serverLoc.appendChild(profileDoc.createTextNode(servFoldLoc));
			root.appendChild(serverLoc);
			Element clientLoc = profileDoc.createElement("clientLoc");
			clientLoc.appendChild(profileDoc.createTextNode(clientFoldLoc));
			root.appendChild(clientLoc);
			Element questionLoc = profileDoc.createElement("questionLoc");
			questionLoc.appendChild(profileDoc.createTextNode(qFileLoc));
			root.appendChild(questionLoc);
			Element timeDefault = profileDoc.createElement("timeDefault");
			timeDefault.appendChild(profileDoc.createTextNode(Integer.toString(defTime)));
			root.appendChild(timeDefault);
			Element pointDefault = profileDoc.createElement("pointDefault");
			pointDefault.appendChild(profileDoc.createTextNode(Integer.toString(defVal)));
			root.appendChild(pointDefault);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer docTransformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(profileDoc);
			StreamResult result = new StreamResult(new File("profileSave.xml"));
			docTransformer.transform(source, result);
		}catch(Exception e){e.printStackTrace();}
	}
	
	public String getServerFolderLoc(){
		return servFoldLoc;
	}
	
	public String getClientFolderLoc(){
		return clientFoldLoc;
	}
	
	public static String getQuestionFileLoc(){
		return qFileLoc;
	}
	
	public static int getDefaultValue(){
		return defVal;
	}
	
	public static int getDefaultTime(){
		return defTime;
	}

	public void setServerFolderLoc(String loc){
		servFoldLoc=loc;
	}
	
	public void setClientFolderLoc(String loc){
		clientFoldLoc=loc;
	}
	
	public void setQuestionFileLoc(String loc){
		qFileLoc=loc;
	}
	
	public void setDefaultValue(int v){
		defVal=v;
	}
	
	public void setDefaultTime(int t) {
		defTime=t;
	}
	
	public boolean isComplete(){
		if (servFoldLoc != null && clientFoldLoc != null && 
			!servFoldLoc.equals("") && !clientFoldLoc.equals("") && 
			qFileLoc != null && defVal > 0 && defTime > 0){
			return true;
		}
		return false;
	}
}
