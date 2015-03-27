package com.example.obigrocery.activities;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title = "Obi Grocery - Check List " + shoppingListId;
        this.setTitle(title);

        Button editListButton = (Button) findViewById(R.id.editListButton);
        editListButton.setVisibility(View.GONE);

        Button shopButton = (Button) findViewById(R.id.shopButton);
        shopButton.setVisibility(View.GONE);
        
        itemsView = (ListView) findViewById(R.id.itemView);

        itemsView.setAdapter(adapter);

        populateCategories();
    }
    
    protected void setInstructions() {
        TextView instructionText = (TextView) findViewById(R.id.instructionText);
        instructionText.setText("First, check off the items that have been purchased.");
    }
    
    protected void populateList() {
        // TODO use db to populate, use shoppingListId
        adapter = new ItemListAdapterCheck(this);
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
