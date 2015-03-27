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
        /*
         * TODO Program to add categories from database.
         */
        categoryArray.add("Baked Goods");
        categoryArray.add("Dairy");
        categoryArray.add("Meats");
        categoryArray.add("Vegetables");

        Collections.sort(categoryArray);
        return categoryArray;
    }
    
    public static List<String> getUnits() {
        List<String> unitArray = new ArrayList<String>();
        /*
         * TODO Program to add units from database.
         */
        
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
