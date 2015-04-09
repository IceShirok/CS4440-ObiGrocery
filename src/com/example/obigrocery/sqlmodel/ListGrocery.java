package com.example.obigrocery.sqlmodel;

import java.io.Serializable;

public class ListGrocery implements Serializable{
	public static final String TAG = "ListGrocery";
	private static final long serialVersionUID = -7406082437623008161L;
	
	
	private long id;
	private long listId;
	private long productId;
	private float amount;
	private String units;
	private int isPurchased;
	private Products products;
	private ShoppingList shoppingList;
	 
	public ListGrocery() {}
	 
	public ListGrocery(long listId, long productId, float amount, 
			String units, int isPurchased) {
		 this.listId = listId;
		 this.productId = productId;
		 this.amount = amount;
		 this.units = units;
		 this.isPurchased = isPurchased;
		 }
	 
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		 this.id = id;
	}	
	
	public long getListId() {
		 return listId;
	}
	
	public void setListId(long listId) {
		 this.listId = listId;
	}
	
	public long getProductID() {
		 return productId;
	}
	
	public void setProductID(long productId) {
		 this.productId = productId;
	}
	
	public float getAmount() {
		 return amount;
	}
	
	public void setAmount(float amount) {
		 this.amount = amount;
	}
	
	public String getUnits() {
		 return units;
	}
	
	public void setUnits(String units) {
		 this.units = units;
	}
	
	public int getIsPurchased() {
		 return isPurchased;
	}
	
	public void setIsPurchased(int isPurchased) {
		 this.isPurchased = isPurchased;
	}
	
	public Products getProducts(){
		return products;
	}
	
	public void setProducts(Products products){
		this.products = products;
	}
	
	public ShoppingList getShoppingList(){
		return shoppingList;
	}
	
	public void setShoppingList(ShoppingList shoppingList){
		this.shoppingList = shoppingList;
	}

}
