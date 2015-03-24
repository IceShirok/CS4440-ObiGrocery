package com.example.obigrocery.activities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.adapters.ItemListAdapterEdit;

public class EditGroceryListGen extends ActionBarActivity {

    protected EditText listTextbox;
    protected EditText itemNameTextbox;
    protected EditText quantityTextbox;
    protected EditText priceTextbox;
    protected ListView itemsView;
    protected Spinner categorySpinner;

    protected Button addGroceryButton;
    protected Button finishGroceryButton;
    protected Button duplicateGroceryButton;

    protected ItemListAdapterEdit adapter;

    /******************************************************************
     * Instantiation of stuff into the app
     ******************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_grocery_list);
        
        addGroceryButton = (Button) findViewById(R.id.addGroceryButton);
        addGroceryButton.setEnabled(false);

        finishGroceryButton = (Button) findViewById(R.id.finishGroceryButton);
        finishGroceryButton.setEnabled(false);

        duplicateGroceryButton = (Button) findViewById(R.id.duplicateGroceryButton);
        duplicateGroceryButton.setEnabled(true);

        listTextbox = (EditText) findViewById(R.id.listTextbox);
        listTextbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                finishGroceryButton.setEnabled(listTextbox.getText().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        itemNameTextbox = (EditText) findViewById(R.id.itemNameTextbox);
        itemNameTextbox.addTextChangedListener(new AddGroceryWatcher());
        
        quantityTextbox = (EditText) findViewById(R.id.quantityTextbox);
        quantityTextbox.addTextChangedListener(new AddGroceryWatcher());
        
        priceTextbox = (EditText) findViewById(R.id.priceTextbox);
        priceTextbox.addTextChangedListener(new AddGroceryWatcher());

        itemsView = (ListView) findViewById(R.id.itemView);

        adapter = new ItemListAdapterEdit(this);
        itemsView.setAdapter(adapter);
        itemsView.setClickable(true);
        itemsView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // TODO can edit item when clicked
                // Fix this so it works
                System.out.println("blah " + position);
                editItemAlert(adapter.getItem(position).toString());
            }
        });

        populateCategories();
    }
    

    /******************************************************************
     * Helper methods for instantiations and listeners
     ******************************************************************/
    
    /*
     * Only allow items to be added if input is not blank and in specific category
     */
    protected boolean enableAdd() {
        return itemNameTextbox.getText().length() > 0
                && quantityTextbox.getText().length() > 0
                && priceTextbox.getText().length() > 0
                && !categorySpinner.getSelectedItem().toString().equalsIgnoreCase("All");
    }

    protected class AddGroceryWatcher implements TextWatcher {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            addGroceryButton.setEnabled(enableAdd());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {
            addGroceryButton.setEnabled(enableAdd());
        }
    };

    protected void populateCategories() {
        List<String> spinnerArray = new ArrayList<String>();
        // TODO program to add categories from database
        spinnerArray.add("All");
        spinnerArray.add("Baked Goods");
        spinnerArray.add("Dairy");
        spinnerArray.add("Meats");
        spinnerArray.add("Vegetables");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        categorySpinner.setAdapter(adapter);
        this.categoryShift("All");
        // TODO may want to make this a constant, since db won't have this option available
        
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
     * Options menu (don't worry about this)
     ******************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_grocery_list, menu);
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
        addGroceryButton.setEnabled(enableAdd());
    }

    /******************************************************************
     * Adding stuff to the list
     ******************************************************************/
    public boolean addGroceryItem(View view) {
        // hides keyboard
        InputMethodManager imm = (InputMethodManager) getBaseContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(itemNameTextbox.getWindowToken(), 0);

        // adds to list
        String itemName = null;
        int quantity = 0;
        BigDecimal price = null;
        String category = null;
        try {
            quantity = Integer.parseInt(quantityTextbox.getText().toString());
            price = new BigDecimal(priceTextbox.getText().toString());
            itemName = itemNameTextbox.getText().toString();
            category = categorySpinner.getSelectedItem().toString();
        } catch (NumberFormatException e) {
            String errorMessage = "Incorrect number format!";

            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setNeutralButton("OK", null)
            .show();
        }
        if (itemName != null) {
            addItemsToDisplay(itemName, quantity, price, category);
            addItemsToDatabase(itemName, quantity, price, category);
            quantityTextbox.setText("");
            priceTextbox.setText("");
            itemNameTextbox.setText("");
            return true;
        }
        return false;
    }

    protected void addItemsToDisplay(String itemName, int quantity, BigDecimal price, String category) {
        ItemPOJO item = new ItemPOJO(itemName, price, quantity, category);
        adapter.add(item);
        itemsView.invalidateViews();
        itemsView.setAdapter(adapter);
    }

    protected void addItemsToDatabase(String itemName, int quantity, BigDecimal price, String category) {
        // TODO implement so item can be added to database
    }
    
    public void duplicateList(View view) {
        // TODO implement the following below
        Intent i = new Intent(getApplicationContext(), NewGroceryAll.class);
        startActivity(i);
        /*
        get the list id from the previous activity
        for (some data structure) {
            addItemsToDisplay(itemName, quantity, price, category);
        }
        categoryShift("All");
         */
    }

    
    /******************************************************************
     * Done, save anything else in db, go to confirmation page
     ******************************************************************/
    public void finishGroceryList(View view) {
        String listName = listTextbox.getText().toString();

        if (listName != null && listName.length() > 0) {
            List<ItemPOJO> list = adapter.getList();
            
            // TODO can remove if needed, for testing purposes
            System.out.println("Print out finished list");
            System.out.println("Name: " + listName);
            for (ItemPOJO item : list) {
                System.out.println("\tItem: " + item);
            }

            // TODO put name to database

            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_info)
            .setTitle("Confirmation")
            .setMessage("List "+listName+" saved to database.")
            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .show();
        } else {
            // TODO make this better
            String errorMessage = "Not a valid list name.";

            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setNeutralButton("OK", null)
            .show();
        }
    }
    
    /******************************************************************
     * Returning to menu
     ******************************************************************/

    public void returnToMenu(View view) {
        cancelListConfirm();
    }
    
    public void onBackPressed() {
        cancelListConfirm();
    }
    
    protected void cancelListConfirm() {
        // TODO change this, since list is saved for every add, delete, update
        if (adapter.getList().size() > 0) {
            new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Returning to Menu")
                .setMessage("Are you sure you want to return? You will lose all changes.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                            int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
        } else {
            finish();
        }
    }
    
    protected void editItemAlert(String info) {
        // TODO finish this implementation
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Title");
        alert.setMessage("Message");

        // Set an EditText view to get user input 
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do something with value!
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }
}
