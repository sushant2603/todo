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


public class AddDialog extends DialogFragment {

	private EditText textField;
	//private EditText priorityField;
	private DatePicker datePicker;
	private Button addButton;


	public interface AddDialogListener {
		void onAddDialogDone(String text, Calendar date);
	}
	
	public AddDialogListener listener;

    static AddDialog newInstance() {
        AddDialog dialog = new AddDialog();
        Bundle args = new Bundle();
        dialog.setArguments(args);
        return dialog;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_dialog, container, false);
        textField = (EditText) view.findViewById(R.id.addText);
        //priorityField = (EditText) view.findViewById(R.id.priorityText);
        datePicker = (DatePicker) view.findViewById(R.id.editDatePicker);

        addButton = (Button)view.findViewById(R.id.addBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
                // When button is clicked, call up to owning activity.
        		String text = textField.getText().toString();
        		//String priority = priorityField.getText().toString();
				Calendar newDate = Calendar.getInstance();
				newDate.set(datePicker.getYear(),
					datePicker.getMonth(), datePicker.getDayOfMonth());
        		listener.onAddDialogDone(text, newDate);
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
		    listener = (AddDialogListener) activity;
		} catch (ClassCastException e) {
		    throw new ClassCastException(activity.toString()
		            + " must implement NoticeDialogListener");
		}
    }
}
