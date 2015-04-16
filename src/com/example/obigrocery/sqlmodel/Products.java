package com.example.obigrocery.sqlmodel;

import java.io.Serializable;

public class Products implements Serializable, Comparable<Products>{
	public static final String TAG = "Products";
	private static final long serialVersionUID = -7406082437623008161L;
	
	private long id;
	private String productName;
	private String category;
	
	public Products() {}
	
	public Products(long id, String productName, String category) {
	    this.id = id;
	    this.productName = productName;
	    this.category = category;
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
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		 this.category = category;
	}
    
    @Override
    public String toString() {
        return productName + " (" + category + ")";
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
        return this.getProductName().equals(that.getProductName())
                && this.getCategory().equals(that.getCategory());
    }
    
    @Override
    public int hashCode() {
        return (int) (this.getProductName().hashCode()
                + this.getCategory().hashCode());
    }

    @Override
    public int compareTo(Products another) {
        if(this.getProductName().compareTo(another.getProductName()) != 0) {
            return this.getProductName().compareTo(another.getProductName());
        }
        return this.getCategory().compareTo(another.getCategory());
    }

}
