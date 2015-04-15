package com.example.obigrocery.sqlmodel;

import java.io.Serializable;

public class ListGrocery implements Serializable, Comparable<ListGrocery>{
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
    
    @Override
    public String toString() {
        return products.toString() + "\n" + amount + " " + units;
    }
    
    @Override
    public boolean equals(Object other) {
        if(other == this) {
            return true;
        }
        if(other == null) {
            return false;
        }
        if(!(other instanceof ListGrocery)) {
            return false;
        }
        ListGrocery that = (ListGrocery)other;
        return this.getId() == that.getId();
    }
    
    @Override
    public int hashCode() {
        return (int) (this.getId());
    }

    @Override
    public int compareTo(ListGrocery another) {
        return (int) (this.getId() - another.getId());
    }

}
