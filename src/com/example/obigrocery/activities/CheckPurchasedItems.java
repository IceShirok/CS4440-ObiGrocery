package com.example.obigrocery.activities;

import java.util.List;

import com.example.obigrocery.adapters.ItemListAdapterPurchase;
import com.example.obigrocery.sqlmodel.ListGrocery;

import android.widget.ListView;
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
        adapter = new ItemListAdapterPurchase(this, shoppingListId);
        List<ListGrocery> items = listGroceryDb.getListGeocerybyListId(shoppingListId);
        for(ListGrocery item : items) {
            adapter.add(item);
        }
        itemsView = (ListView) findViewById(R.id.itemView);
        itemsView.setAdapter(adapter);
    }
}
