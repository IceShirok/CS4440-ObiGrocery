package com.example.obigrocery.activities;

import android.widget.TextView;

public class CheckPurchasedItems extends ListOneListGen {

    // TODO after indicating purchased, prompt if want to input items bought but not in list
    // TODO go to confirmation?

    /******************************************************************
     * Instantiation of stuff into the app
     ******************************************************************/
    
    protected void setTitle() {
        /*
         * TODO use database to get the name of the shopping list using shoppingListId
         */
        String title = "Obi Grocery - Purchase with List " + shoppingListId;
        this.setTitle(title);
    }
    
    protected void setInstructions() {
        TextView instructionText = (TextView) findViewById(R.id.instructionText);
        instructionText.setText("First, check off the items that have been purchased.");
    }

    /******************************************************************
     * Populating stuff to the app
     ******************************************************************/
    protected void populateList() {
//        adapter = new ItemListAdapterPurchase(this, shoppingListId);
//        /*
//         * TODO use database to populate the list using shoppingListId
//         */
//        adapter.add(new ItemPOJO("Bread1", "oz", 1, "Baked Goods"));
//        adapter.add(new ItemPOJO("Bread2", "oz", 0, "Baked Goods"));
//        adapter.add(new ItemPOJO("Bread3", "oz", 1, "Baked Goods"));
//        adapter.add(new ItemPOJO("Meat1", "oz", 0, "Meats"));
//        adapter.add(new ItemPOJO("Meat2", "oz", 0, "Meats"));
//        adapter.add(new ItemPOJO("Dairy", "oz", 1, "Dairy"));
//
//        itemsView = (ListView) findViewById(R.id.itemView);
//        itemsView.setAdapter(adapter);
    }
}
