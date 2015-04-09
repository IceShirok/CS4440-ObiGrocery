package com.example.obigrocery.sqlmodel;

import java.io.Serializable;

public class ShoppingList implements Serializable{
	public static final String TAG = "ShoppingList";
	private static final long serialVersionUID = -7406082437623008161L;
	
	private long id;
	private String dateTime;
	 
	public ShoppingList() {}
	 
	public ShoppingList(String dateTime) {
		this.dateTime = dateTime;
	}
	 
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		 this.id = id;
	}
	
	public String getDateTime() {
		 return dateTime;
	}
	
	public void setDateTime(String dateTime) {
		 this.dateTime = dateTime;
	}

}
