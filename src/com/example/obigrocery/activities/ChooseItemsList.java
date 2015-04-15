package com.example.obigrocery.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.obigrocery.adapters.ItemListAdapterSelect;
import com.example.obigrocery.db.DBTools;
import com.example.obigrocery.sqlmodel.ListGrocery;


public class ChooseItemsList extends CheckPurchasedItems {
	
	// The object that allows to manipulate the database
	DBTools dbTools = new DBTools(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_purchased_items);
    }
    
    /******************************************************************
     * Creating the display of a list, read-only
     ******************************************************************/    
    protected void setTitle() {
        String title = "Obi Grocery - Choose Items For List " + shoppingListId;
        this.setTitle(title);
    }

    protected void setInstructions() {
        TextView instructionText = (TextView) findViewById(R.id.instructionText);
        instructionText.setText("Select the items you want to import to the list.");
    }

    /******************************************************************
     * Populating the list
     ******************************************************************/
    protected void populateList() {
        adapter = new ItemListAdapterSelect(this, shoppingListId);
        /*
         * TODO use database to populate
         * Use shoppingListId to retrieve
         * Want to have unique/distinct items here from all history, or part of it
         */

        itemsView = (ListView) findViewById(R.id.itemView);
        itemsView.setAdapter(adapter);
    }

    @Override
    public void returnToLists(View view) {
        Intent returnIntent = new Intent();
        ArrayList<String> list = new ArrayList<>();
        List<ListGrocery> temp = ((ItemListAdapterSelect) adapter).getCheckedList();
        for(ListGrocery item : temp) {
            list.add(item.getProducts().getProductName()+","+item.getUnits()+","
                    +item.getAmount()+","+item.getProducts().getCategory());
        }
        returnIntent.putStringArrayListExtra("result", list);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

}
