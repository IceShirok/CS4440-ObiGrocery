package com.example.obigrocery.activities.newgrocery;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.activities.R;
import com.example.obigrocery.activities.global.EditGroceryListGen;

public class NewGroceryList extends EditGroceryListGen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        itemNameTextbox = (EditText) findViewById(R.id.itemNameTextbox);
        itemNameTextbox.addTextChangedListener(new AddGroceryWatcher());
        
        quantityTextbox = (EditText) findViewById(R.id.quantityTextbox);
        quantityTextbox.addTextChangedListener(new AddGroceryWatcher());
        quantityTextbox.setVisibility(View.GONE);
        
        priceTextbox = (EditText) findViewById(R.id.priceTextbox);
        priceTextbox.addTextChangedListener(new AddGroceryWatcher());
        priceTextbox.setVisibility(View.GONE);
        
        TextView markup = (TextView) findViewById(R.id.quantityText);
        markup.setVisibility(View.GONE);
    }
    
    @Override
    public boolean addGroceryItem(View view) {
        System.out.println("adding");
        // hides keyboard
        InputMethodManager imm = (InputMethodManager) getBaseContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(itemNameTextbox.getWindowToken(), 0);

        ItemPOJO formedItem = isValidItem(itemNameTextbox.getText().toString(),
                categorySpinner.getSelectedItem().toString());
        if (formedItem != null) {
            System.out.println(formedItem);
            addItemsToDisplay(formedItem);
            addItemsToDatabase(formedItem);
            itemNameTextbox.setText("");
            return true;
        } else {
            String errorMessage = "Incorrect number format!";

            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setNeutralButton("OK", null)
            .show();
            return false;
        }
    }

    @Override
    protected void addItemsToDatabase(ItemPOJO item) {
        // TODO implement so item can be added to database
    }

    @Override
    protected boolean enableAdd() {
        return itemNameTextbox.getText().length() > 0
                && !categorySpinner.getSelectedItem().toString().equalsIgnoreCase("All");
    }

    protected void editItemAlert(final ItemPOJO item) {
        getEditItemDialog(item, false).show();
    }

    /*
     * Done with all grocery list items, add everything to db, go to
     * confirmation page
     */
    @Override
    public void finishGroceryList(View view) {
        String listName = listTextbox.getText().toString();

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
}
