package com.example.obigrocery;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

public class NewGroceryList extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_grocery_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_grocery_list, menu);
        listItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
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

    private ArrayList<String> listItems;
    private ArrayAdapter<String> adapter;
    private int clickCounter=0;
    
    /*
     * Dynamic addition of list items
     */
    public void addItems(View view) {
        listItems.add("Clicked : "+clickCounter++);
        adapter.notifyDataSetChanged();
    }
    
    /*
     * Add a grocery item to the list
     */
    public void addGroceryItem(View view) {
        addItems(view);
    }
    
    /*
     * Done with all grocery list items, go to confirmation page
     */
    public void finishGroceryList(View view) {
        
    }

    /*
     * Return to menu
     */
    public void returnToMenu(View view) {
        System.exit(0);
    }
}
