package com.bickovskis.vardzinis;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WordGuess extends Activity {

	// TODO: getters and setters for unit testing
	public int goodAnswers=0;
	public int badAnswers=0;
	int totalAnswers=0; 
	
	int answerCount=5;
	
	TextView wordToGuess ;
	Button  knowAnswer;
	Button  dontKnowAnswer;
	
	DbHelper db;
	ArrayList<WordData> wordList;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.wordguess);

		goodAnswers=0;
		badAnswers=0;
		totalAnswers=0; 
	
		try {
			db=new DbHelper(getBaseContext());
			wordList= db.getAllWords();
			db.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	    addButtonListeners();
	    initGameField();
	    
	}

	/**
	 * sets listeners to buttons
	 * */
	
	private void addButtonListeners(){
		
		
		knowAnswer = (Button) findViewById(R.id.know_button);
		 
		knowAnswer.setOnClickListener(new OnClickListener() {
			//@Override
			public void onClick(View arg0) {
				setNewWord(true);
			}
 
		});
		
		dontKnowAnswer = (Button) findViewById(R.id.dont_know_button );
		 
		dontKnowAnswer.setOnClickListener(new OnClickListener() {
			//@Override
			public void onClick(View arg0) {
				setNewWord(false);
			}
		});

		wordToGuess	=(TextView) findViewById(R.id.word_to_guess);
		
	}
	
	
	
	/**
	 * setting new word and counting right and wrong answers
	 * */
	public void setNewWord(Boolean correctAnswer){
		
		if (correctAnswer==true){
			goodAnswers++;
		}
		else {
			badAnswers++;
		}
		wordList.get(totalAnswers).correctAnswer=correctAnswer;
		wordList.get(totalAnswers).answered =true;
		totalAnswers++;
		initGameField();
	}
	
	
	/**
	 * sets new word and timer. Happens on new game and when next word is choses 
	 * */
	
	private void initGameField(){
		
		if (totalAnswers<answerCount){
			wordToGuess.setText(getNewWord());
		}
		else {
			Intent intent = new Intent(WordGuess.this, GameResults.class);
			Bundle b = new Bundle();
			
			ArrayList<String> answersToPass=new ArrayList<String>();
			
			int correct=0;
			for(int i=0;i<wordList.size();i++){
				correct=wordList.get(i).correctAnswer? 1 : 0;
				
				answersToPass.add(""+wordList.get(i).id+"|"+wordList.get(i).wordToShow+"|"+
					wordList.get(i).wordToGuess+"|"+correct );
				//System.out.println(wordList.get(i));
			}
			
			b.putStringArrayList("answers_list", answersToPass);
			
			b.putInt("correct", goodAnswers); //Your id
			b.putInt("incorrect", badAnswers); //Your id
			intent.putExtras(b); //Put your id to your next Intent
			startActivity(intent);
			finish();
			
			
			
//			wordToGuess.setText("Thats all folks!");
		}
	}
	
	private String getNewWord(){

		//TODO: implement better data getting method
		// http://onjava.com/pub/a/onjava/2003/03/12/java_comp.html
		
		if (totalAnswers>wordList.size()){
			// TODO: raise error or something
			return "";
		}
		
		return ""+wordList.get(totalAnswers).wordToShow ;
		
		
	}
	
	

}
