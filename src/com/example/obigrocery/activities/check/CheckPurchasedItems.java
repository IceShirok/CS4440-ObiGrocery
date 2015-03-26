package com.example.obigrocery.activities.check;

import java.math.BigDecimal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.activities.R;
import com.example.obigrocery.activities.global.ListOneListGen;
import com.example.obigrocery.adapters.ItemListAdapterCheck;

public class CheckPurchasedItems extends ListOneListGen {
    
    private Button editListButton;

    // TODO after indicating purchased, prompt if want to input items bought but not in list
    // TODO go to confirmation?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title = "Obi Grocery - Check List " + shoppingListId;
        this.setTitle(title);

        editListButton = (Button) findViewById(R.id.editListButton);
        editListButton.setText("Next");
        
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
        adapter.add(new ItemPOJO("Bread1", new BigDecimal(1), 1, "Baked Goods"));
        adapter.add(new ItemPOJO("Bread2", new BigDecimal(0), 0, "Baked Goods"));
        adapter.add(new ItemPOJO("Bread3", new BigDecimal(1), 1, "Baked Goods"));
        adapter.add(new ItemPOJO("Meat1", new BigDecimal(0), 0, "Meats"));
        adapter.add(new ItemPOJO("Meat2", new BigDecimal(0), 0, "Meats"));
        adapter.add(new ItemPOJO("Dairy", new BigDecimal(1), 1, "Dairy"));
        itemsView = (ListView) findViewById(R.id.itemView);
        itemsView.setAdapter(adapter);
    }
    
    public void editList(View view) {
        Intent i = new Intent(getApplicationContext(), CheckEditItems.class);
        i.putExtra("SHOPPING_LIST_ID", shoppingListId);
        startActivity(i);
    }
}
