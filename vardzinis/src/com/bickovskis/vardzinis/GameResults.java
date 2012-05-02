package com.bickovskis.vardzinis;

import java.util.ArrayList;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameResults extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.gameresults);

	    // TODO: check if these values exist
	    Bundle b = getIntent().getExtras();
	    int correct = b.getInt("correct");
	    int incorrect = b.getInt("incorrect");
	    
	    //TODO: what happens if that keyword does not exist?
	    ArrayList<String> answers=b.getStringArrayList("answers_list");  //new ArrayList<String>();
	    
	    TableLayout resultsLayout= (TableLayout) findViewById(R.id.results_table);
	    
	    TableRow resultsRow;// =new TableRow(this);
		
	    TextView wordShown;// = new TextView(this);
	    TextView wordToGuess;// = new TextView(this);
	    
	    // TODO: move to separate function
		for(int i=0;i<answers.size();i++){
			wordShown = new TextView(this);
			wordToGuess = new TextView(this);
			
			String[] restultsArr= answers.get(i).split("\\|");
			// TODO: check how many rows are returned
		
			wordShown.setText(restultsArr[1]);
			wordToGuess.setText(restultsArr[2]);

			wordShown.setTextColor(Color.WHITE);
			wordToGuess.setTextColor(Color.WHITE);
			
			wordToGuess.setGravity(Gravity.RIGHT | Gravity.BOTTOM );
			
			resultsRow=new TableRow(this);
			resultsRow.addView(wordShown);
			resultsRow.addView(wordToGuess);

			int col;
			if (Integer.parseInt(restultsArr[3])==1){
				col= Color.GREEN;
			}
			else{
				col=Color.RED;
			}
			resultsRow.setBackgroundColor(col);
				
			resultsLayout.addView(resultsRow,
					new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			
			 
			//System.out.println(wordList.get(i));
		}

	    
	    
	    TextView correctTextField	=(TextView) findViewById(R.id.correct_answers );
	    TextView incorrectTextField	=(TextView) findViewById(R.id.incorrect_answers);
	    
	    correctTextField.setText(Integer.toString(correct));
	    incorrectTextField.setText(Integer.toString(incorrect));
	    
	}

}
