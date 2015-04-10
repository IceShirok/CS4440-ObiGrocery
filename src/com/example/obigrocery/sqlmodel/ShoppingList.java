package com.example.obigrocery.sqlmodel;

import java.io.Serializable;

public class ShoppingList implements Serializable, Comparable<ShoppingList> {
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
    
    @Override
    public String toString() {
        return "Shopping List ID# " + id;
    }
    
    @Override
    public boolean equals(Object other) {
        if(other == this) {
            return true;
        }
        if(other == null) {
            return false;
        }
        if(!(other instanceof ShoppingList)) {
            return false;
        }
        ShoppingList that = (ShoppingList)other;
        return this.getId() == that.getId();
    }
    
    @Override
    public int hashCode() {
        return (int) (this.getId());
    }

    @Override
    public int compareTo(ShoppingList another) {
        return (int) (this.getId() - another.getId());
    }

}
