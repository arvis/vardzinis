package com.bickovskis.vardzinis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper{
private Context mycontext;

//private String DB_PATH = mycontext.getApplicationContext().getPackageName()+"/databases/";
private static String DB_PATH = "/data/data/com.bickovskis.vardzinis/databases/";
private static String DB_NAME = "wordsDb";//the extension may be .sqlite or .db
public SQLiteDatabase myDataBase;
private boolean dbCopyComplete=true;

/*private String DB_PATH = "/data/data/"
                       + mycontext.getApplicationContext().getPackageName()
                       + "/databases/";*/

public DbHelper(Context context) throws IOException  {
	super(context,DB_NAME,null,1);
	this.mycontext=context;
	boolean dbexist = checkdatabase();
	if(dbexist)
	{
		Log.d("dbHelper","Database exists");
	   opendatabase(); 
	}
	else
	{
	   Log.d("dbHelper","Database doesn't exist");
	createdatabase();
	}

}

public void createdatabase() throws IOException{
	
	//TODO: http://stackoverflow.com/questions/2387421/how-to-use-my-own-sqlite-database
	boolean dbexist = checkdatabase();
	if(dbexist)
	{
	   Log.d("dbHelper"," Database exists.");
	}
	else{
	   this.getReadableDatabase();
	   
		try{
		       copydatabase();
		   }
		   catch(IOException e){
		       throw new Error("Error copying database");
		   }
	}
}
private boolean checkdatabase() {
	//SQLiteDatabase checkdb = null;
	boolean checkdb = false;
	try{
	   String myPath = DB_PATH + DB_NAME;
	   File dbfile = new File(myPath);
	   //checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
	   checkdb = dbfile.exists();
	}
	catch(SQLiteException e){
	   Log.d("dbHelper","Database doesn't exist");
	}
	
	return checkdb;
}
private void copydatabase() throws IOException {

	//Open your local db as the input stream
	InputStream myinput = mycontext.getAssets().open(DB_NAME);
	dbCopyComplete=false;
	
	//Path to the just created empty db
	String outfilename = DB_PATH + DB_NAME;
	
	//Open the empty db as the output stream
	OutputStream myoutput = new FileOutputStream(outfilename);
	
	//transfer byte to inputfile to outputfile
	byte[] buffer = new byte[1024];
	int length;
	while ((length = myinput.read(buffer))>0)
	{
	   myoutput.write(buffer,0,length);
	}
	
	//Close the streams
	myoutput.flush();
	myoutput.close();
	myinput.close();
	dbCopyComplete=true;

}

	public void opendatabase() throws SQLException
	{
		//Open the database
		String mypath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
	
	}


//	Cursor getAllWords(){
	ArrayList<WordData> getAllWords(){
		
		ArrayList<WordData> wordsList = new ArrayList<WordData>();
		// TODO: check if data is not corrupt and raise exception
		//if (!dbCopyComplete) return wordsList;
		
		SQLiteDatabase db=this.getReadableDatabase();
		// SELECT * FROM table ORDER BY RANDOM() LIMIT 1;
		Cursor cur=db.rawQuery("SELECT * from words ORDER BY RANDOM() LIMIT 5",new String [] {});
		
		//cur.moveToFirst();
		while (cur.moveToNext()) {
			wordsList.add(new WordData(cur.getInt(0), cur.getString(2), cur.getString(1)));
		}

		cur.close();
		
		//Collections.shuffle(wordsList);
		return wordsList;//cur.getString(1);
	}

	
	
	public synchronized void close(){
		if(myDataBase != null){
		   myDataBase.close();
		}
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
