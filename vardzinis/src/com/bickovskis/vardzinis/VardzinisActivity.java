package com.bickovskis.vardzinis;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class VardzinisActivity extends Activity {
    /** Called when the activity is first created. */
	
	Button startguess;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        DbHelper db;
		try {
			db=new DbHelper(getBaseContext());
			db.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        addWordGuessListener();
        
/*        
        TextView tv = new TextView(this);
        tv.setText("Hello, Android");
        setContentView(tv);
*/        
        
    }
    

	public void addWordGuessListener() {
		 
		startguess = (Button) findViewById(R.id.wordguess);
 
		startguess.setOnClickListener(new OnClickListener() {
			//@Override
			public void onClick(View arg0) {
				Intent wordGuessScreen = new Intent(VardzinisActivity.this, WordGuess.class);
				startActivity(wordGuessScreen);			    
			}
 
		});
 
	}
     
    
    
    
    
}