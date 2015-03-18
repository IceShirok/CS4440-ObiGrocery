package com.example.obigrocery.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.adapters.ItemListAdapter;

public class NewGroceryList extends ActionBarActivity {

    private EditText listTextbox;
    private EditText itemNameTextbox;
    private EditText quantityTextbox;
    private EditText priceTextbox;
    private ListView itemsView;
    private Spinner categorySpinner;

    private ItemListAdapter<ItemPOJO> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_grocery_list);

        listTextbox = (EditText) findViewById(R.id.listTextbox);
        itemNameTextbox = (EditText) findViewById(R.id.itemNameTextbox);
        quantityTextbox = (EditText) findViewById(R.id.quantityTextbox);
        priceTextbox = (EditText) findViewById(R.id.priceTextbox);

        itemsView = (ListView) findViewById(R.id.itemView);

        adapter = new ItemListAdapter<>(new ArrayList<ItemPOJO>(), this);
        itemsView.setAdapter(adapter);
        itemsView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                System.out.println("printing item " + position);
            }
        });

        populateCategories();
    }

    private void populateCategories() {
        List<String> spinnerArray = new ArrayList<String>();
        // TODO program to add from database
        spinnerArray.add("Baked Goods");
        spinnerArray.add("Dairy");
        spinnerArray.add("Meats");
        spinnerArray.add("Vegetables");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        categorySpinner.setAdapter(adapter);
    }

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

    /*
     * Add a grocery item to the list
     */
    public void addGroceryItem(View view) {
        // hides keyboard
        InputMethodManager imm = (InputMethodManager) getBaseContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(itemNameTextbox.getWindowToken(), 0);

        // adds to list
        String itemName = null;
        int quantity = 0;
        double price = 0;
        String category = null;
        try {
            quantity = Integer.parseInt(quantityTextbox.getText().toString());
            price = Double.parseDouble(priceTextbox.getText().toString());
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
        }
    }

    /*
     * Dynamic addition of list items
     */
    private void addItemsToDisplay(String itemName, int quantity, double price, String category) {
        // TODO change so arraylist hold POJO
        ItemPOJO item = new ItemPOJO(itemName, price, quantity, category);
        adapter.add(item);
        itemsView.setAdapter(adapter);
    }

    /*
     * Done with all grocery list items, add everything to db, go to
     * confirmation page
     */
    public void finishGroceryList(View view) {
        String listName = listTextbox.getText().toString();

        // TODO also check database if list already exists
        if (listName != null && listName.length() > 0) {
            List<ItemPOJO> list = adapter.getList();
            System.out.println("Print out finished list");
            System.out.println("Name: " + listName);
            for (ItemPOJO item : list) {
                System.out.println("\tItem: " + item);
            }
            // TODO put everything to database
            // TODO goto list of shopping lists, or main menu with confirmation

            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_info)
            .setTitle("Confirmation")
            .setMessage("List saved to database.")
            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .show();
        } else {
            String errorMessage = "Not a valid list name.";

            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setNeutralButton("OK", null)
            .show();
        }
    }

    /*
     * Return to menu, cancelling the list
     */
    public void returnToMenu(View view) {
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
}
