package com.example.obigrocery.activities;

import java.util.List;

import com.example.obigrocery.adapters.ItemListAdapterPurchase;
import com.example.obigrocery.db.ListGroceryDAO;
import com.example.obigrocery.sqlmodel.ListGrocery;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class CheckPurchasedItems extends ListOneListGen {

    // TODO after indicating purchased, prompt if want to input items bought but not in list
    // TODO go to confirmation?

    /******************************************************************
     * Instantiation of stuff into the app
     ******************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_purchased_items);
        
        listGroceryDb = new ListGroceryDAO(this);
        
        setInstructions();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            shoppingListId = extras.getLong("SHOPPING_LIST_ID");
            populateList();
            populateCategories();
            setTitle();
        } else {
            String errorMessage = "List missing from database. Exiting...";

            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setNeutralButton("OK", null)
            .show();
            finish();
        }
    }
    
    protected void setTitle() {
        String title = "Obi Grocery - Purchase with List " + shoppingListId;
        this.setTitle(title);
    }
    
    protected void setInstructions() {
        TextView instructionText = (TextView) findViewById(R.id.instructionText);
        instructionText.setText("Check off the items that have been purchased.");
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
