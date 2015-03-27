package com.example.obigrocery.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.obigrocery.POJO.ItemPOJO;

public class EditGroceryList extends EditGroceryListGen {

    /******************************************************************
     * Instantiation of stuff into the app
     ******************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listTextbox.setVisibility(View.GONE);
        Button menuButton = (Button) findViewById(R.id.menuButton);
        menuButton.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            shoppingListId = extras.getInt("SHOPPING_LIST_ID");

            populateList();
            setTitle();
            populateCategories();
            
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

    /******************************************************************
     * Populate stuff to the app
     ******************************************************************/
    protected void setTitle() {
        /*
         * TODO use database to get the name of the shopping list using shoppingListId
         */
        String title = "Obi Grocery - Edit List " + shoppingListId;
        this.setTitle(title);
        listTextbox.setText(title);
    }

    protected void populateList() {
        /*
         * TODO use database to populate list items from list
         * use shoppingListId to retrieve from database
         * nothing else is needed because it's taken care of by its parent class
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

    /******************************************************************
     * Transitions
     ******************************************************************/
    public void finishGroceryList(View view) {
        setResult(RESULT_OK, getIntent());
        finish();
    }

    public void returnToMenu(View view) {
        setResult(RESULT_OK, getIntent());
        finish();
    }
    
    public void onBackPressed() {
        setResult(RESULT_OK, getIntent());
        finish();
    }
}
