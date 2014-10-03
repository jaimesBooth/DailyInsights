package com.example.dailyinsight;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final int dbVersion = 1;
	private static final String dbName = "quoteDB";
	private static final String quoteTable = "quotes";
	private static final String quoteID = "ID";
	private static final String quoteType = "Type";
	private static final String quoteMSG = "Quote";
	
	/*
	 * The constructor for DatabaseHelper class
	 * @param context the context of the db
	 */
	public DatabaseHelper(Context context) {
		super(context, dbName, null, dbVersion);
	}

	@Override
	/*
	 * Method to create the database
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE "+quoteTable+" ( "+quoteID+" INTEGER PRIMARY KEY, "
				   +quoteType+" category, "+quoteMSG+" quote)");
		addQuotes();
	}

	@Override
	/*
	 * Method to upgrade the database
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	public void onUpgrade(SQLiteDatabase db, int old, int update) {
		db.execSQL("DROP TABLE IF EXISTS "+quoteTable);
		onCreate(db);
	}
	
	/**
	 * This method populates the database with quotes
	 */
	public void addQuotes() {
		ContentValues value = new ContentValues();
		
	}

}
