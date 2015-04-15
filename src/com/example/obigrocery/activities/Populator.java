package com.example.obigrocery.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Populator {
    
    public final static String ALL_CATEGORY = "All";
    
    private static List<String> categoryArray;
    private static List<String> unitArray;
    
    static {
        categoryArray = new ArrayList<String>();
        categoryArray.add("Protein");
        categoryArray.add("Fruit & Vegetable");
        categoryArray.add("Drinks");
        categoryArray.add("Frozen Food");
        categoryArray.add("Houseware");
        categoryArray.add("Baked Goods");
        categoryArray.add("Other");
        Collections.sort(categoryArray);

        unitArray = new ArrayList<String>();
        unitArray.add("unit");
        unitArray.add("oz");
        unitArray.add("lbs");
        unitArray.add("fl oz");
        unitArray.add("pint");
        unitArray.add("quart");
        unitArray.add("gallon");
        Collections.sort(unitArray);
    }
    
    public static List<String> getCategories(boolean needAll) {
        List<String> gimme = new ArrayList<String>();
        if(needAll) {
            gimme.add(ALL_CATEGORY);
        }
        gimme.addAll(categoryArray);
        return gimme;
    }
    
    public static List<String> getCategories() {
        return categoryArray;
    }
    
    public static List<String> getUnits() {
        return unitArray;
    }

}
