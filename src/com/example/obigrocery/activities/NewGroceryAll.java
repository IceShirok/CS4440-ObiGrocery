package com.example.obigrocery.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class NewGroceryAll extends ListShoppingGen {
    
    private ListView shoppingListView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);
        
        shoppingListView = (ListView) findViewById(R.id.shoppingListView);
        shoppingListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                adapter.getItem(position);
                Intent i = new Intent(getApplicationContext(), NewGroceryOne.class);
                i.putExtra("SHOPPING_LIST_ID", position);
                startActivity(i);
            }
        });
        
        List<String> list = new ArrayList<>();
        // TODO use db to populate list
        list.add("Shopping List 1\n\tNumber of items: 10");
        list.add("Shopping list 2\n\tNumber of items: 10");
        list.add("Shopping list 3\n\tNumber of items: 10");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        shoppingListView.setAdapter(adapter);
    }
}
