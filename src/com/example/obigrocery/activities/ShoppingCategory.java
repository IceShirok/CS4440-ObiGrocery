package com.example.obigrocery.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShoppingCategory extends ActionBarActivity {

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_category);
        
        ListView shoppingCategoryView = (ListView) findViewById(R.id.shoppingCategoryView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String shoppingListId = extras.getString("CATEGORY");
            
            List<String> list = new ArrayList<>();
            // TODO use db to populate list
            list.add(shoppingListId + "Bread: Bread\n\t1 x $10");
            list.add(shoppingListId + "Pie: Bread\n\t1 x $10");
            list.add(shoppingListId + "Cake: Bread\n\t1 x $10");
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            shoppingCategoryView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shopping_category, menu);
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
}
