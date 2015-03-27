package com.example.obigrocery.POJO;

public class ListPOJO {
    private String name;
    private int id;
    public ListPOJO(String name, int id) {
        super();
        this.name = name;
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }
}
