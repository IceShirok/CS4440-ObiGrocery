package com.example.obigrocery.activities;

import java.math.BigDecimal;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.obigrocery.POJO.ItemPOJO;

public class EditGroceryList extends EditGroceryListGen {

    private int shoppingListId;
    private boolean changed;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // TODO use shoppingListId to populate listview with db stuff
            // note that this stuff is for display only!
            shoppingListId = extras.getInt("SHOPPING_LIST_ID");
            adapter.add(new ItemPOJO("Bread", new BigDecimal(1), shoppingListId, "Baked Goods"));
            adapter.add(new ItemPOJO("Bread", new BigDecimal(1), shoppingListId, "Baked Goods"));
            adapter.add(new ItemPOJO("Bread", new BigDecimal(1), 1, "Baked Goods"));
            adapter.add(new ItemPOJO("Meat", new BigDecimal(1), 1, "Meats"));
            adapter.add(new ItemPOJO("Meats", new BigDecimal(1), 1, "BMeats"));
            adapter.add(new ItemPOJO("Dairy", new BigDecimal(1), 1, "Dairy"));
            
            // TODO this should be filled by info from the db
            String title = "Obi Grocery - Edit List " + shoppingListId;
            this.setTitle(title);
            listTextbox.setText(title);

            itemsView = (ListView) findViewById(R.id.itemView);
            itemsView.setAdapter(adapter);
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
    
    @Override
    public boolean addGroceryItem(View view) {
        boolean flag = addGroceryItem(view);
        if(flag) {
            changed = true;
        }
        return flag;
    }
        

    /*
     * Done with all grocery list items, add everything to db, go to
     * confirmation page
     */
    @Override
    public void finishGroceryList(View view) {
        String listName = listTextbox.getText().toString();

        // TODO put all info from adapter to db
        // don't know how much the db will take care of duplicates
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
    
    protected void cancelListConfirm() {
        if (changed) {
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
