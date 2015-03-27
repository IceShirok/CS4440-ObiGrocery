package com.example.obigrocery.POJO;

public class ListPOJO implements Comparable<ListPOJO> {
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
    
    @Override
    public String toString() {
        return name + " : ID# " + id;
    }
    
    @Override
    public boolean equals(Object other) {
        if(other == this) {
            return true;
        }
        if(other == null) {
            return false;
        }
        if(!(other instanceof ListPOJO)) {
            return false;
        }
        ListPOJO that = (ListPOJO)other;
        return this.getName().equals(that.getName())
                && this.getId() == that.getId();
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode() + this.getId();
    }

    @Override
    public int compareTo(ListPOJO another) {
        if((this.getId() - another.getId()) != 0) {
            return this.getId() - another.getId();
        }
        return this.getName().compareTo(another.getName());
    }
}
