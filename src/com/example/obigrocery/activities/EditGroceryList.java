package com.example.obigrocery.activities;

import java.math.BigDecimal;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ListView;

import com.example.obigrocery.POJO.ItemPOJO;

public class EditGroceryList extends EditGroceryListGen {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // TODO use shoppingListId to populate listview with db stuff
            shoppingListId = extras.getInt("SHOPPING_LIST_ID");

            // display stuff starts
            adapter.add(new ItemPOJO("Bread", new BigDecimal(1), shoppingListId, "Baked Goods"));
            adapter.add(new ItemPOJO("Bread", new BigDecimal(1), shoppingListId, "Baked Goods"));
            adapter.add(new ItemPOJO("Bread", new BigDecimal(1), 1, "Baked Goods"));
            adapter.add(new ItemPOJO("Meat", new BigDecimal(1), 1, "Meats"));
            adapter.add(new ItemPOJO("Meats", new BigDecimal(1), 1, "BMeats"));
            adapter.add(new ItemPOJO("Dairy", new BigDecimal(1), 1, "Dairy"));
            // display stuff ends
            
            // TODO the grocery list should be filled by info from the db
            String title = "Obi Grocery - Edit List " + shoppingListId;
            this.setTitle(title);
            listTextbox.setText(title);

            itemsView = (ListView) findViewById(R.id.itemView);
            itemsView.setAdapter(adapter);
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
}
