package com.example.obigrocery.POJO;

import java.math.BigDecimal;

public class ItemPOJO {
    
    private String name;
    private BigDecimal price;
    private int quantity;
    private String category;

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
    
    public String toString() {
        return name + ": " + category + "\n\t" + quantity + " x $" + price;
    }

}
