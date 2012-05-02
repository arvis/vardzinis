package com.bickovskis.vardzinis;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelperNative extends SQLiteOpenHelper {

	
	// http://zaman91.wordpress.com/2010/09/22/android-how-to-use-own-sqlite-database/
	static final String dbName="wordsDb";
	static final String wordsTable="words";
	
	public DbHelperNative(Context context) {
		super(context, dbName, null,1); 
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql="create table words (id INTEGER PRIMARY KEY AUTOINCREMENT,word_lv TEXT,word_en TEXT);";
		db.execSQL(sql);		
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	
	ArrayList<String> getAllWords(){
		
		ArrayList<String> wordsList = new ArrayList<String>();
		
		SQLiteDatabase db=this.getReadableDatabase();		
		Cursor cur=db.rawQuery("SELECT * from words;",new String [] {});
		
		//cur.moveToFirst();
		while (cur.moveToNext()) {
			wordsList.add(cur.getString(1));
		    // use cursor
		}

		cur.close();
		
		Collections.shuffle(wordsList);
		return wordsList;//cur.getString(1);
	}

	
	
	/**
	 * returns multiple words from database
	 * @in idArr array of ids that must  
	 * */
	Cursor getMultipleWords(){
		SQLiteDatabase db=this.getReadableDatabase();		
		//SELECT id,name from mytable WHERE id in (1,2,9,.. );
		Cursor cur=db.rawQuery("SELECT * from words;",new String [] {});
		return cur;
		
		
	}
	

}
