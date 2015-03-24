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

public class ListShoppingGen extends ActionBarActivity {
    
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
                // TODO transfer information to next screen
                adapter.getItem(position);
                Intent i = new Intent(getApplicationContext(), ListShoppingOne.class);
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

    /*
     * Return to menu
     */
    public void returnToMenu(View view) {
        finish();
    }
}
