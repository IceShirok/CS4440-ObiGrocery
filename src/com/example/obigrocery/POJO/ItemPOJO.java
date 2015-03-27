package com.example.obigrocery.POJO;


public class ItemPOJO implements Comparable<ItemPOJO>{
    
    private String name;
    private String unit;
    private int quantity;
    private String category;
    private boolean purchased;

    public ItemPOJO(String name, String unit, int quantity, String category) {
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.category = category;
        this.purchased = false;
    }
    
    public ItemPOJO(String name, String unit, String category) {
        this(name, unit, 0, category);
    }

    public String getName() {
        return name;
    }
    public String getUnit() {
        return unit;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getCategory() {
        return category;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public boolean isPurchased() {
        return purchased;
    }
    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
    
    @Override
    public String toString() {
        return name + ": " + category + "\n\t" + quantity + " units of label " + unit;
    }
    
    @Override
    public boolean equals(Object other) {
        if(other == this) {
            return true;
        }
        if(other == null) {
            return false;
        }
        if(!(other instanceof ItemPOJO)) {
            return false;
        }
        ItemPOJO that = (ItemPOJO)other;
        return this.getName().equals(that.getName())
                && this.getCategory().equals(that.getCategory());
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode() + this.getCategory().hashCode();
    }

    @Override
    public int compareTo(ItemPOJO another) {
        if(this.getCategory().compareTo(another.getCategory()) != 0) {
            return this.getCategory().compareTo(another.getCategory());
        }
        return this.getName().compareTo(another.getName());
    }

}
