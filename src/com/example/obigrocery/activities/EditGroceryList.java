package com.example.obigrocery.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ListView;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.activities.R;

public class EditGroceryList extends EditGroceryListGen {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            shoppingListId = extras.getInt("SHOPPING_LIST_ID");

            // display stuff starts
            adapter.add(new ItemPOJO("Bread1", "oz", 1, "Baked Goods"));
            adapter.add(new ItemPOJO("Bread2", "oz", 0, "Baked Goods"));
            adapter.add(new ItemPOJO("Bread3", "oz", 1, "Baked Goods"));
            adapter.add(new ItemPOJO("Meat1", "oz", 0, "Meats"));
            adapter.add(new ItemPOJO("Meat2", "oz", 0, "Meats"));
            adapter.add(new ItemPOJO("Dairy", "oz", 1, "Dairy"));
            // display stuff ends

            // TODO use shoppingListId to populate listview with db stuff
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
