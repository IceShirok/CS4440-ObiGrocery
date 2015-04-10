package com.example.obigrocery.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.adapters.ItemListAdapterSelect;
import com.example.obigrocery.db.DBTools;

public class ChooseItemsList extends CheckPurchasedItems {
	
//	// The object that allows to manipulate the database
//	DBTools dbTools = new DBTools(this);
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        Button editListButton = (Button) findViewById(R.id.editListButton);
//        editListButton.setVisibility(View.GONE);
//
//        Button shopButton = (Button) findViewById(R.id.shopButton);
//        shopButton.setVisibility(View.GONE);
//    }
//    
//    /******************************************************************
//     * Creating the display of a list, read-only
//     ******************************************************************/    
//    protected void setTitle() {
//        /*
//         * TODO use database to get the name of the shopping list using shoppingListId
//         */
//        String title = "Obi Grocery - Choose Items " + shoppingListId;
//        this.setTitle(title);
//    }
//
//    protected void setInstructions() {
//        TextView instructionText = (TextView) findViewById(R.id.instructionText);
//        instructionText.setText("Select the items you want to import to the list.");
//    }
//
//    /******************************************************************
//     * Populating the list
//     ******************************************************************/
//    protected void populateList() {
//        adapter = new ItemListAdapterSelect(this, shoppingListId);
//        /*
//         * TODO use database to populate
//         * Use shoppingListId to retrieve
//         * Want to have unique/distinct items here from all history, or part of it
//         */
//        adapter.add(new ItemPOJO("Bread1", "oz", shoppingListId, "Baked Goods"));
//        adapter.add(new ItemPOJO("Bread2", "oz", shoppingListId, "Baked Goods"));
//        adapter.add(new ItemPOJO("Bread3", "oz", 1, "Baked Goods"));
//        adapter.add(new ItemPOJO("Meat", "oz", 1, "Meats"));
//        adapter.add(new ItemPOJO("Meats", "oz", 1, "Meats"));
//        adapter.add(new ItemPOJO("Dairy", "oz", 1, "Dairy"));
//
//        itemsView = (ListView) findViewById(R.id.itemView);
//        itemsView.setAdapter(adapter);
//    }
//
//    @Override
//    public void returnToLists(View view) {
//        Intent returnIntent = new Intent();
//        ArrayList<String> list = new ArrayList<>();
//        List<ItemPOJO> temp = ((ItemListAdapterSelect) adapter).getCheckedList();
//        for(ItemPOJO item : temp) {
//            list.add(item.getName()+","+item.getUnit()+","+item.getQuantity()+","+item.getCategory());
//        }
//        returnIntent.putStringArrayListExtra("result", list);
//        setResult(RESULT_OK, returnIntent);
//        finish();
//    }

}
