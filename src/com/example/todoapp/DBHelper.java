package com.example.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String ITEMS_TABLE = "todos";
	public static final String ID_COLUMN = "_id";
	public static final String DESCRIPTION_COLUMN = "description";
	public static final String DATE_COLUMN = "date";

	private static final String DB_NAME = "todos.db";
	private static final int DB_VERSION = 1;
	private static final String DATABASE_CREATE = "create table "
			+ ITEMS_TABLE  + "("
			+ ID_COLUMN + " integer primary key autoincrement, "
			+ DESCRIPTION_COLUMN + " text not null,"
			+ DATE_COLUMN + " integer);";
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + ITEMS_TABLE);
		onCreate(db);
	}	
}
