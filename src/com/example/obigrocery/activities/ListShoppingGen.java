package com.example.obigrocery.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.obigrocery.POJO.ListPOJO;
import com.example.obigrocery.activities.R;
import com.example.obigrocery.db.*;
import com.example.obigrocery.sqlmodel.*;

public class ListShoppingGen extends ActionBarActivity {
    
    protected ListView shoppingListView;
    protected ArrayAdapter<ListPOJO> adapter;
    private ShoppingListDAO shoppingListDAO;

    /******************************************************************
     * Creating the list of shopping lists
     ******************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);
        
        shoppingListDAO = new ShoppingListDAO(this);
        //shoppingListDAO.open();
        
        setInstructions();
        setupListView();
        populateList();
    }
    
    protected void setInstructions() {
        TextView instructionText = (TextView) findViewById(R.id.instructionText);
        instructionText.setText("Instructions: select a list to view.");
    }
    protected void setupListView() {
        shoppingListView = (ListView) findViewById(R.id.shoppingListView);
        shoppingListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                Intent i = new Intent(getApplicationContext(), ListOneListGen.class);
                i.putExtra("SHOPPING_LIST_ID", adapter.getItem(position).getId());
                startActivity(i);
            }
        });
    }

    /******************************************************************
     * Populating the list - a database story
     ******************************************************************/
    protected void populateList() {
        /*
         * TODO use database to populate the list of shopping lists
         * The ListPOJO takes in a String name of list, and the int ID number of list
         */
    	List<ShoppingList> values = new ArrayList<>();
    	values = shoppingListDAO.getAllShoppingLists();
    	System.out.println("size of list: " + values.size());
    	for(ShoppingList list : values) {
    	    System.out.println(list);
    	}
    	
        ArrayAdapter<ShoppingList> adapter = new ArrayAdapter<ShoppingList>(this,
        		android.R.layout.simple_list_item_1, values);
        shoppingListView.setAdapter(adapter);

        /*
        List<ListPOJO> display = new ArrayList<>();
        display.add(new ListPOJO("Shopping List 5", 5));
        display.add(new ListPOJO("Shopping List 6", 6));
        display.add(new ListPOJO("Shopping List 7", 7));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, display);
        shoppingListView.setAdapter(adapter);
         */
        
    }

    /******************************************************************
     * Options
     ******************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shopping_lists, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /******************************************************************
     * Navigation
     ******************************************************************/
    public void returnToMenu(View view) {
        finish();
    }
}
