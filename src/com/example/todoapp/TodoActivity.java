package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.*;


public class TodoActivity extends Activity {
	
	private final int REQUEST_CODE = 20;
	private ArrayList<String> items;
	private ArrayAdapter<String> itemsAdapter;
	private ListView lvItems;
	private EditText etNewItem;

    public void onAddedItem(View v) {
    	String itemText = etNewItem.getText().toString();
    	itemsAdapter.add(itemText);
    	etNewItem.setText("");
    	writeItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupDeleteListener();
        setupEditListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    		String newItemText = data.getExtras().getString("newItem");
    		int itemPosition = data.getExtras().getInt("position");
    		items.set(itemPosition, newItemText);
    		itemsAdapter.notifyDataSetChanged();
    		writeItems();
    	}
    }

    private void setupDeleteListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,
					int position, long id) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}
    	});
    }

    private void setupEditListener() {
    	lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				Intent editIntent = new Intent(TodoActivity.this, EditItemActivity.class);
				editIntent.putExtra("position", position);
				editIntent.putExtra("itemText", items.get(position));
				startActivityForResult(editIntent, REQUEST_CODE);
			}
		});
    }

    private void readItems() {
    	File fileDir = getFilesDir();
    	File itemsFile = new File(fileDir, "items.txt");
    	try {
    		items = new ArrayList<String>(FileUtils.readLines(itemsFile));
    	} catch (IOException e) {
    		items = new ArrayList<String>();
    	}
    }

    private void writeItems() {
    	File fileDir = getFilesDir();
    	File itemsFile = new File(fileDir, "items.txt");
    	try {
    		FileUtils.writeLines(itemsFile, items);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}
