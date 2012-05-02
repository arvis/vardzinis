package com.bickovskis.vardzinis;


// TODO: think if its better to parcel or leave other way //implements Parcelable 
public class WordData  {

	public int id;
	public String wordToShow="";
	public String wordToGuess="";
	public boolean correctAnswer;
	public boolean answered=false;
	
	
	public WordData(int id, String wordToShow,String wordToGuess){
		this.id=id;
		this.wordToShow=wordToShow;
		this.wordToGuess=wordToGuess;
	}


	
	
}
