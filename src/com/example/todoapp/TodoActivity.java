package com.example.todoapp;

import java.util.Calendar;
import java.util.List;

import com.example.todoapp.AddDialog.AddDialogListener;
import com.example.todoapp.EditDialog.EditDialogListener;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TodoActivity extends Activity
	implements EditDialogListener, AddDialogListener {
	
	private final int REQUEST_CODE = 20;
	private List<Item> items;
	private ArrayAdapter<Item> itemsAdapter;
	private ListView lvItems;
	private ItemsDataSource datasource;

    public void onAddedItem(View v) {
	    FragmentTransaction ft = getFragmentManager().beginTransaction();
	    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
	    if (prev != null) {
	        ft.remove(prev);
	    }
	    ft.addToBackStack(null);

	    // Create and show the dialog.
	    DialogFragment newFragment = AddDialog.newInstance();
	    newFragment.show(ft, "dialog");
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
        datasource = new ItemsDataSource(this);
        datasource.open();

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = datasource.getAllItems();
        itemsAdapter = new ArrayAdapter<Item>(this,
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
    		Item newItem = items.get(itemPosition);
    		newItem.setDescription(newItemText);
    		datasource.updateItem(newItem);
    		items.set(itemPosition, newItem);
    		itemsAdapter.notifyDataSetChanged();
    	}
    }

    private void setupDeleteListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,
					int position, long id) {
				Item deleteItem = items.get(position);
				datasource.deleteItem(deleteItem);
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				return true;
			}
    	});
    }

    private void setupEditListener() {
    	lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
			    FragmentTransaction ft = getFragmentManager().beginTransaction();
			    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
			    if (prev != null) {
			        ft.remove(prev);
			    }
			    ft.addToBackStack(null);
			    // Create and show the dialog.
			    DialogFragment newFragment = EditDialog.newInstance(
			    		items.get(position), position);
			    newFragment.show(ft, "dialog");
			}
		});
    }

	@Override
	public void onEditDialogDone(String text, Calendar date, int position) {
 		Item newItem = items.get(position);
		newItem.setDescription(text);
		newItem.setDate(date.getTimeInMillis());
		datasource.updateItem(newItem);
		items.set(position, newItem);
		itemsAdapter.notifyDataSetChanged();
	}

	@Override
	public void onAddDialogDone(String text, Calendar date) {
    	Item item = datasource.createItem(text, date);
    	items.add(item);
    	itemsAdapter.notifyDataSetChanged();
	}
}
