package com.example.todoapp;
import java.util.Calendar;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


public class EditDialog extends DialogFragment {

	private EditText textField;
	private Button saveButton;
	private static int itemPosition;
	private DatePicker datePicker;
	private static Item editItem;

	public interface EditDialogListener {
		void onEditDialogDone(String text, Calendar date, int position);
	}
	
	public EditDialogListener listener;

    static EditDialog newInstance(Item item, int position) {

    	itemPosition = position;
    	editItem = item;
        EditDialog dialog = new EditDialog();
        Bundle args = new Bundle();
        args.putString("description", item.getDescription());
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_dialog, container, false);
        textField = (EditText) view.findViewById(R.id.editTextFragment);
        textField.setText(editItem.getDescription());
        textField.setSelection(editItem.getDescription().length());

        datePicker = (DatePicker) view.findViewById(R.id.editDatePicker);

        // Watch for button clicks.
        saveButton = (Button)view.findViewById(R.id.editbtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
                // When button is clicked, call up to owning activity.
        		String text = textField.getText().toString();
				Calendar newDate = Calendar.getInstance();
				newDate.set(datePicker.getYear(),
					datePicker.getMonth(), datePicker.getDayOfMonth());
        		listener.onEditDialogDone(text, newDate, itemPosition);
        		getDialog().dismiss();
            }
        });

        textField.requestFocus();
        getDialog().getWindow().setSoftInputMode(
        		WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }
    
    @Override
    public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
		    listener = (EditDialogListener) activity;
		} catch (ClassCastException e) {
		    throw new ClassCastException(activity.toString()
		            + " must implement NoticeDialogListener");
		}
    }

}
