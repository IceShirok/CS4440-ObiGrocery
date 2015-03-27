package com.example.obigrocery.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.adapters.ItemListAdapterCheck;

public class CheckPurchasedItems extends ListOneListGen {

    // TODO after indicating purchased, prompt if want to input items bought but not in list
    // TODO go to confirmation?

    /******************************************************************
     * Instantiation of stuff into the app
     ******************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button editListButton = (Button) findViewById(R.id.editListButton);
        editListButton.setVisibility(View.GONE);

        Button shopButton = (Button) findViewById(R.id.shopButton);
        shopButton.setVisibility(View.GONE);
        
        itemsView = (ListView) findViewById(R.id.itemView);
        itemsView.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            shoppingListId = extras.getInt("SHOPPING_LIST_ID");

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
        adapter = new ItemListAdapterCheck(this);
        /*
         * TODO use database to populate the list using shoppingListId
         */
        adapter.add(new ItemPOJO("Bread1", "oz", 1, "Baked Goods"));
        adapter.add(new ItemPOJO("Bread2", "oz", 0, "Baked Goods"));
        adapter.add(new ItemPOJO("Bread3", "oz", 1, "Baked Goods"));
        adapter.add(new ItemPOJO("Meat1", "oz", 0, "Meats"));
        adapter.add(new ItemPOJO("Meat2", "oz", 0, "Meats"));
        adapter.add(new ItemPOJO("Dairy", "oz", 1, "Dairy"));

        itemsView = (ListView) findViewById(R.id.itemView);
        itemsView.setAdapter(adapter);
    }
}
