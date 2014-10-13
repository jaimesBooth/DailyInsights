package com.example.dailyinsight;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author lukeharper
 * @modified 13/10/14 Removed quote type, changed quoteID, removed addQuote method
 * 					  Edited onCreate with exception, and changed attribute names	
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "quoteDB";
	private static final String TABLE_NAME = "QUOTES";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_QUOTE = "Quote";
	private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ( "+COLUMN_ID+" INTEGER PRIMARY"
			                                        + " KEY AUTOINCREMENT, " +COLUMN_QUOTE+" VARCHAR(255));";
	private static final String DROP_TABLE = "DROP TABLE IF EXISTS"+TABLE_NAME;
	
	/**
	 * The constructor for DatabaseHelper class
	 * @param context the context of the db
	 */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	/**
	 * Method to create the database
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	public void onCreate(SQLiteDatabase db) {
		
		try {
			db.execSQL(CREATE_TABLE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			MainActivity.setInsightText(""+e);
		}
	}

	@Override
	/**
	 * Method to upgrade the database
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	public void onUpgrade(SQLiteDatabase db, int old, int update) {
		try {
			db.execSQL(DROP_TABLE);
			onCreate(db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			MainActivity.setInsightText(""+e);
		}
	}
}
