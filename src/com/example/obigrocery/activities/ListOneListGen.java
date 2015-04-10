package com.example.obigrocery.activities;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.example.obigrocery.adapters.ItemListAdapterGen;
import com.example.obigrocery.db.ListGroceryDAO;
import com.example.obigrocery.db.ShoppingListDAO;
import com.example.obigrocery.sqlmodel.ListGrocery;

public class ListOneListGen extends ActionBarActivity {
    
    protected ItemListAdapterGen adapter;
    protected ListView itemsView;
    protected Spinner categorySpinner;
    protected long shoppingListId;
    protected ListGroceryDAO listGroceryDb;

    /******************************************************************
     * Creating the display of a list, read-only
     ******************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shopping_lists);
        
        listGroceryDb = new ListGroceryDAO(this);
        
        setInstructions();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            shoppingListId = extras.getLong("SHOPPING_LIST_ID");
            populateList();
            populateCategories();
            setTitle();
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
    
    protected void setTitle() {
        String title = "Obi Grocery - Show List " + shoppingListId;
        this.setTitle(title);
    }

    /******************************************************************
     * Populating the list
     ******************************************************************/
    protected void populateList() {
        adapter = new ItemListAdapterGen(this, shoppingListId);
        List<ListGrocery> groceryItems = listGroceryDb.getListGeocerybyListId(shoppingListId);
        for(ListGrocery item : groceryItems) {
            adapter.add(item);
        }

        itemsView = (ListView) findViewById(R.id.itemView);
        itemsView.setAdapter(adapter);
    }

    /******************************************************************
     * Selecting between categories
     ******************************************************************/
    protected void populateCategories() {
        List<String> spinnerArray = Populator.getCategories(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        categorySpinner.setAdapter(adapter);
        this.categoryShift(Populator.ALL_CATEGORY);
        
        categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                categoryShift(categorySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
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
     * Editing a list
     ******************************************************************/
    public void editList(View view) {
        Intent i = new Intent(getApplicationContext(), EditGroceryList.class);
        i.putExtra("SHOPPING_LIST_ID", shoppingListId);
        startActivityForResult(i, EDIT_ITEM_LIST);
    }
    
    public final static int EDIT_ITEM_LIST = 1;
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("Activity Results in listing one list");
        if (requestCode == EDIT_ITEM_LIST) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if(extras != null) {
                    int shoppingListId = extras.getInt("SHOPPING_LIST_ID");
                    System.out.println("List selected ID: " + shoppingListId);
                    categoryShift(Populator.ALL_CATEGORY);
                    populateList();
                } else {
                    System.out.println("error occurred when importing list...");
                }
            }
        }
    }

    /******************************************************************
     * Let's go shopping!
     ******************************************************************/
    public void letsGoShopping(View view) {
        Intent i = new Intent(getApplicationContext(), CheckPurchasedItems.class);
        i.putExtra("SHOPPING_LIST_ID", shoppingListId);
        startActivityForResult(i, 0);
    }
    
    public void deleteList(final View view) {
        new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Delete List Confirmation")
        .setMessage("Are you sure you want to delete? This cannot be undone!")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShoppingListDAO shoppingListDb = new ShoppingListDAO(view.getContext());
                shoppingListDb.deleteShoppingList(shoppingListDb.getShoppingListById(shoppingListId));
                setResult(RESULT_OK, getIntent());
                finish();
            }
        })
        .setNegativeButton("No", null)
        .show();
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
     * Returning to menu
     ******************************************************************/
    public void onBackPressed() {
        finish();
    }
    
    public void returnToLists(View view) {
        finish();
    }
}
