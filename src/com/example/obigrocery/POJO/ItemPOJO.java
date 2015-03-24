package com.example.obigrocery.POJO;

import java.math.BigDecimal;

public class ItemPOJO {
    
    private String name;
    private BigDecimal price;
    private int quantity;
    private String category;
    private boolean checked;

    public ItemPOJO(String name, BigDecimal price, int quantity, String category) {
        // price should use BigDecimal for money stuff, not double
        // this is due to floating point number's bad precision
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public String getName() {
        return name;
    }
    public BigDecimal getPrice() {
        return price;
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
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    /*
     * Used for display in app
     */
    @Override
    public String toString() {
        return name + ": " + category + "\n\t" + quantity + " x $" + price;
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
                && this.getQuantity() == that.getQuantity()
                && this.getPrice().equals(that.getPrice())
                && this.getCategory().equals(that.getCategory());
    }

}
