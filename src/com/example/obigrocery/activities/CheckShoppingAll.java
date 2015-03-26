package com.example.obigrocery.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CheckShoppingAll extends ListShoppingGen {
    
    private ListView shoppingListView;
    private ArrayAdapter<String> adapter;
    
    @Override
    protected void setInstructions() {
        TextView instructionText = (TextView) findViewById(R.id.instructionText);
        instructionText.setText("Instructions: select a list to view.");
    }

    @Override
    protected void setupListView() {
        shoppingListView = (ListView) findViewById(R.id.shoppingListView);
        shoppingListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                // TODO edit list to new screen for adding purchased items
                adapter.getItem(position);
                Intent i = new Intent(getApplicationContext(), CheckPurchasedItems.class);
                i.putExtra("SHOPPING_LIST_ID", position);
                startActivity(i);
            }
        });
    }

    @Override
    protected void populateList() {
        List<String> list = new ArrayList<>();
        // TODO use db to populate list
        list.add("Shopping List 1\n\tNumber of items: 5");
        list.add("Shopping list 2\n\tNumber of items: 5");
        list.add("Shopping list 3\n\tNumber of items: 5");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        shoppingListView.setAdapter(adapter);
    }
}
