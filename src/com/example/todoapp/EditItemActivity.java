package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

	private EditText etEditItem;
	private int item_position;

    public void onSave(View v) {
    	String itemText = etEditItem.getText().toString();
    	Intent editInfo = new Intent();
    	editInfo.putExtra("position", item_position);
    	editInfo.putExtra("newItem", itemText);
    	setResult(RESULT_OK, editInfo);
    	finish();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
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
		setContentView(R.layout.activity_edit_item);
		item_position = getIntent().getIntExtra("position", 0);
		String item_text = getIntent().getStringExtra("itemText");
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(item_text);
        etEditItem.setSelection(item_text.length());
	}
}
