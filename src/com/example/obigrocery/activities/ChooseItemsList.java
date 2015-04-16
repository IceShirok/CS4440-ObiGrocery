package com.example.obigrocery.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.obigrocery.adapters.ItemListAdapterSelect;
import com.example.obigrocery.db.DBTools;
import com.example.obigrocery.db.ProductsDAO;
import com.example.obigrocery.sqlmodel.ListGrocery;
import com.example.obigrocery.sqlmodel.Products;


public class ChooseItemsList extends CheckPurchasedItems {
	
	// The object that allows to manipulate the database
	DBTools dbTools = new DBTools(this);
    
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
        ProductsDAO groceryDB = new ProductsDAO(this.getBaseContext());
        List<Products> items = groceryDB.getAllGroceries();
        for(Products item : items) {
            ListGrocery i = new ListGrocery();
            i.setProducts(item);
            System.out.println(i);
            adapter.add(i);
        }
        itemsView = (ListView) findViewById(R.id.itemView);
        itemsView.setAdapter(adapter);
    }

    @Override
    public void returnToLists(View view) {
        Intent returnIntent = new Intent();
        ArrayList<String> list = new ArrayList<>();
        List<ListGrocery> temp = ((ItemListAdapterSelect) adapter).getCheckedList();
        for(ListGrocery item : temp) {
            list.add(item.getProductID()+"|"+item.getUnits()+"|"
                    +item.getAmount());
        }
        returnIntent.putStringArrayListExtra("result", list);
        returnIntent.putExtra("SHOPPING_LIST_ID", shoppingListId);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

}
