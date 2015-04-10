package com.example.obigrocery.activities;

import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.obigrocery.sqlmodel.ListGrocery;

public class EditGroceryList extends EditGroceryListGen {

    /******************************************************************
     * Instantiation of stuff into the app
     ******************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listTextbox.setVisibility(View.GONE);
        Button menuButton = (Button) findViewById(R.id.menuButton);
        menuButton.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            shoppingListId = extras.getLong("SHOPPING_LIST_ID");

            populateList();
            setTitle();
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

    /******************************************************************
     * Populate stuff to the app
     ******************************************************************/
    protected void setTitle() {
        String title = "Obi Grocery - Edit List " + shoppingListId;
        this.setTitle(title);
        listTextbox.setText(title);
    }

    protected void populateList() {
        List<ListGrocery> items = listGroceryDb.getListGeocerybyListId(shoppingListId);
        for(ListGrocery item : items) {
            adapter.add(item);
        }

        itemsView = (ListView) findViewById(R.id.itemView);
        itemsView.setAdapter(adapter);
    }

    protected long generateListId() {
        return shoppingListId;
    }

    /******************************************************************
     * Transitions
     ******************************************************************/
    public void finishGroceryList(View view) {
        setResult(RESULT_OK, getIntent());
        finish();
    }

    public void returnToMenu(View view) {
        setResult(RESULT_OK, getIntent());
        finish();
    }
    
    public void onBackPressed() {
        setResult(RESULT_OK, getIntent());
        finish();
    }
}
