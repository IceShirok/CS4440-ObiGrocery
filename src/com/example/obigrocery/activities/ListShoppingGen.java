package com.example.obigrocery.activities;

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

import com.example.obigrocery.db.ShoppingListDAO;
import com.example.obigrocery.sqlmodel.ShoppingList;

public class ListShoppingGen extends ActionBarActivity {
    
    protected ListView shoppingListView;
    protected ArrayAdapter<ShoppingList> adapter;
    private ShoppingListDAO shoppingListDAO;

    /******************************************************************
     * Creating the list of shopping lists
     ******************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);
        
        shoppingListDAO = new ShoppingListDAO(this);
        
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
                System.out.println("the id should be : " + adapter.getItem(position).getId());
                startActivityForResult(i, 0);
            }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("Activity Results in EditGrocery");
        if (resultCode == RESULT_OK) {
            populateList();
        }
    }

    /******************************************************************
     * Populating the list - a database story
     ******************************************************************/
    protected void populateList() {
    	List<ShoppingList> values = shoppingListDAO.getAllShoppingLists();
        adapter = new ArrayAdapter<ShoppingList>(this,
        		android.R.layout.simple_list_item_1, values);
        shoppingListView.setAdapter(adapter);
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
