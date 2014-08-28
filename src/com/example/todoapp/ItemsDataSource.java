package com.example.todoapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ItemsDataSource {

	private SQLiteDatabase db;
	private DBHelper dbHelper;
	private String[] allColumns = { DBHelper.ID_COLUMN,
		DBHelper.DESCRIPTION_COLUMN, DBHelper.DATE_COLUMN };

	public ItemsDataSource(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Item createItem(String description, Calendar date) {
		ContentValues values =  new ContentValues();
		values.put(DBHelper.DESCRIPTION_COLUMN, description);
		values.put(DBHelper.DATE_COLUMN, date.getTimeInMillis());
		long id = db.insert(DBHelper.ITEMS_TABLE, null, values);
		Cursor cursor = db.query(DBHelper.ITEMS_TABLE, allColumns,
			DBHelper.ID_COLUMN + "=" + id, null, null, null, null);
		cursor.moveToFirst();
		Item newItem = cursorToItem(cursor);
		cursor.close();
		return newItem;
	}

	public void deleteItem(Item item) {
		long id = item.getId();
		db.delete(DBHelper.ITEMS_TABLE, DBHelper.ID_COLUMN + "=" + id, null);
	}

	public void updateItem(Item item) {
		ContentValues values =  new ContentValues();
		values.put(DBHelper.DESCRIPTION_COLUMN, item.getDescription());
		values.put(DBHelper.ID_COLUMN, item.getId());
		values.put(DBHelper.DATE_COLUMN, item.getDate());
		db.update(DBHelper.ITEMS_TABLE, values,
			DBHelper.ID_COLUMN + "=" + item.getId(), null);
	}

	public List<Item> getAllItems() {
		List<Item> items = new ArrayList<Item>();
		Cursor cursor = db.query(DBHelper.ITEMS_TABLE, allColumns,
			null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			Item item  = cursorToItem(cursor);
			items.add(item);
			cursor.moveToNext();
		}
		cursor.close();
		return items;
	}

	private Item cursorToItem(Cursor cursor) {
		Item item = new Item();
		item.setId(cursor.getLong(0));
		item.setDescription(cursor.getString(1));
		item.setDate(cursor.getLong(2));
		return item;
	}
}
