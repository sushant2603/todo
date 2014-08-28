package com.example.todoapp;

import java.util.Calendar;

public class Item {
	
	private long id;
	private String description;
	private String priority;
	private long date;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(this.date);
		return description + "\n" + date.getTime().toString();
	}
}
