package com.example.obigrocery.sqlmodel;

import java.io.Serializable;

import com.example.obigrocery.activities.Populator;

public class Products implements Serializable, Comparable<Products>{
	public static final String TAG = "Products";
	private static final long serialVersionUID = -7406082437623008161L;
	
	private long id;
	private String productName;
	private long categoryId;
	
	public Products() {}
	
	public Products(long id, String productName, long categoryId) {
	    this.id = id;
	    this.productName = productName;
	    this.categoryId = categoryId;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		 this.id = id;
	}
	
	public String getProductName() {
		 return productName;
	}
	
	public void setProductName(String productName) {
		 this.productName = productName;
	}
	
	public long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(long categoryId) {
		 this.categoryId = categoryId;
	}
    
    @Override
    public String toString() {
        return productName + " (" + Populator.getCategories().get((int)categoryId) + ")";
    }
    
    @Override
    public boolean equals(Object other) {
        if(other == this) {
            return true;
        }
        if(other == null) {
            return false;
        }
        if(!(other instanceof Products)) {
            return false;
        }
        Products that = (Products)other;
        return this.getId() == that.getId();
    }
    
    @Override
    public int hashCode() {
        return (int) (this.getId());
    }

    @Override
    public int compareTo(Products another) {
        return (int) (this.getId() - another.getId());
    }

}
