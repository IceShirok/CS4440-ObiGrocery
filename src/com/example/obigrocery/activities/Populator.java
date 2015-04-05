package com.example.obigrocery.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Populator {
    
    public final static String ALL_CATEGORY = "All";
    
    public static List<String> getCategories(boolean needAll) {
        List<String> categoryArray = new ArrayList<String>();
        if(needAll) {
            categoryArray.add(ALL_CATEGORY);
        }

        categoryArray.add("Protein");
        categoryArray.add("Fruit & Vegetable");
        categoryArray.add("Drinks");
        categoryArray.add("Frozen Food");
        categoryArray.add("Houseware");
        categoryArray.add("Baked Goods");

        Collections.sort(categoryArray);
        return categoryArray;
    }
    
    public static List<String> getUnits() {
        List<String> unitArray = new ArrayList<String>();
        
        unitArray.add("unit");
        unitArray.add("oz");
        unitArray.add("lbs");
        unitArray.add("fl oz");
        unitArray.add("pint");
        unitArray.add("quart");
        unitArray.add("gallon");
        
        Collections.sort(unitArray);
        return unitArray;
    }

}
