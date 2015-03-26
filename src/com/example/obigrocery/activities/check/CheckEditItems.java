package com.example.obigrocery.activities.check;

import java.math.BigDecimal;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.activities.R;
import com.example.obigrocery.activities.global.EditGroceryListGen;

public class CheckEditItems extends EditGroceryListGen {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        listTextbox.setVisibility(View.GONE);
        itemNameTextbox.setVisibility(View.GONE);
        quantityTextbox.setVisibility(View.GONE);
        priceTextbox.setVisibility(View.GONE);
        addGroceryButton.setVisibility(View.GONE);
        duplicateGroceryButton.setVisibility(View.GONE);
        itemsView.setMinimumHeight(280);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            shoppingListId = extras.getInt("SHOPPING_LIST_ID");

            // display stuff starts
            adapter.add(new ItemPOJO("Bread", new BigDecimal(1), shoppingListId, "Baked Goods"));
            adapter.add(new ItemPOJO("Bread", new BigDecimal(1), shoppingListId, "Baked Goods"));
            adapter.add(new ItemPOJO("Bread", new BigDecimal(1), 1, "Baked Goods"));
            adapter.add(new ItemPOJO("Meat", new BigDecimal(1), 1, "Meats"));
            adapter.add(new ItemPOJO("Meats", new BigDecimal(1), 1, "BMeats"));
            adapter.add(new ItemPOJO("Dairy", new BigDecimal(1), 1, "Dairy"));
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

    protected void editItemAlert(final ItemPOJO item) {
        getEditItemDialog(item, true).show();
    }
}
