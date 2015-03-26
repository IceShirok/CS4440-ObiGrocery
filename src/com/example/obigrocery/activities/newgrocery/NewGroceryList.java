package com.example.obigrocery.activities.newgrocery;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.activities.global.EditGroceryListGen;

public class NewGroceryList extends EditGroceryListGen {

    /*
     * Done with all grocery list items, add everything to db, go to
     * confirmation page
     */
    @Override
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
}
