package com.example.obigrocery.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.activities.R;
import com.example.obigrocery.activities.R.id;
import com.example.obigrocery.activities.R.layout;
import com.example.obigrocery.activities.R.menu;
import com.example.obigrocery.adapters.ItemListAdapter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShoppingLists extends ActionBarActivity {
    
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
                shoppingListView.setSelection(position);
                System.out.println("Selected: " + (String)shoppingListView.getSelectedItem());
            }
        });
        
        List<String> list = new ArrayList<>();
        // TODO use db to populate list
        list.add("Shopping List 1");
        list.add("Shopping list 2");
        list.add("Shopping list 3");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        shoppingListView.setAdapter(adapter);
    }

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
    
    public void editList(View view) {
        String selected = (String)shoppingListView.getSelectedItem();
        System.out.println("Editing: " + selected);
        // TODO transfer to edit list screen, use db to populate
    }
    
    public void showList(View view) {
        // TODO transfer to show list screen, use db to populate
    }

    /*
     * Return to menu
     */
    public void returnToMenu(View view) {
        finish();
    }
}
