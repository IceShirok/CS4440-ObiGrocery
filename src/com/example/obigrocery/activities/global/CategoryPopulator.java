package com.example.obigrocery.activities.global;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryPopulator {
    
    public static List<String> getCategories(boolean needAll) {
        List<String> spinnerArray = new ArrayList<String>();
        // TODO program to add categories from database
        if(needAll) {
            spinnerArray.add("All");
        }
        spinnerArray.add("Baked Goods");
        spinnerArray.add("Dairy");
        spinnerArray.add("Meats");
        spinnerArray.add("Vegetables");
        Collections.sort(spinnerArray);
        return spinnerArray;
    }

}
