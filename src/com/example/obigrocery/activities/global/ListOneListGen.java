package com.example.obigrocery.activities.global;

import java.math.BigDecimal;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.activities.R;
import com.example.obigrocery.activities.lists.EditGroceryList;
import com.example.obigrocery.adapters.ItemListAdapterGen;

public class ListOneListGen extends ActionBarActivity {
    
    protected ItemListAdapterGen adapter;
    protected ListView itemsView;
    protected Spinner categorySpinner;
    protected int shoppingListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shopping_lists);

        String title = "Obi Grocery - Show List " + shoppingListId;
        this.setTitle(title);
        
        setInstructions();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // TODO use shoppingListId to populate listview with db stuff
            // note that this stuff is for display only!
            shoppingListId = extras.getInt("SHOPPING_LIST_ID");
            populateList();
            populateCategories();
            
        } else {
            String errorMessage = "List missing from database. Exiting...";

            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setNeutralButton("OK", null)
            .show();
            finish();
        }
    }
    
    protected void setInstructions() {
        TextView instructionText = (TextView) findViewById(R.id.instructionText);
        instructionText.setText("View of list, read-only");
    }
    
    protected void populateList() {
        adapter = new ItemListAdapterGen(this);
        // TODO use db to populate, use shoppingListId
        adapter.add(new ItemPOJO("Bread", new BigDecimal(1), shoppingListId, "Baked Goods"));
        adapter.add(new ItemPOJO("Bread", new BigDecimal(1), shoppingListId, "Baked Goods"));
        adapter.add(new ItemPOJO("Bread", new BigDecimal(1), 1, "Baked Goods"));
        adapter.add(new ItemPOJO("Meat", new BigDecimal(1), 1, "Meats"));
        adapter.add(new ItemPOJO("Meats", new BigDecimal(1), 1, "BMeats"));
        adapter.add(new ItemPOJO("Dairy", new BigDecimal(1), 1, "Dairy"));
        itemsView = (ListView) findViewById(R.id.itemView);
        itemsView.setAdapter(adapter);
    }

    /******************************************************************
     * Selecting between categories
     ******************************************************************/
    protected void populateCategories() {
        List<String> spinnerArray = CategoryPopulator.getCategories(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        categorySpinner.setAdapter(adapter);
        this.categoryShift("All");
        
        categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                categoryShift(categorySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

    /******************************************************************
     * options
     ******************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_shopping_lists, menu);
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
     * Selecting between categories
     ******************************************************************/
    protected void categoryShift(String category) {
        adapter.displayCategory(category);
        itemsView.invalidateViews();
        itemsView.setAdapter(adapter);
    }


    /******************************************************************
     * next action
     ******************************************************************/
    public void editList(View view) {
        Intent i = new Intent(getApplicationContext(), EditGroceryList.class);
        i.putExtra("SHOPPING_LIST_ID", shoppingListId);
        startActivity(i);
    }

    /******************************************************************
     * Returning to menu
     ******************************************************************/
    public void onBackPressed() {
        finish();
    }
    
    public void returnToLists(View view) {
        finish();
    }
}
