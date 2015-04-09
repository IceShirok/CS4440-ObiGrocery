package com.example.obigrocery.sqlmodel;

import java.io.Serializable;

public class Products implements Serializable{
	public static final String TAG = "Products";
	private static final long serialVersionUID = -7406082437623008161L;
	
	private long id;
	private String productName;
	private long categoryId;
	
	public Products() {}
	
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

}
