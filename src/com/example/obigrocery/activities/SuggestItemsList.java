package com.example.obigrocery.activities;

import android.widget.ListView;
import android.widget.TextView;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.adapters.ItemListAdapterSelect;

public class SuggestItemsList extends ChooseItemsList {  

    protected void setTitle() {
        /*
         * TODO use database to get the name of the shopping list using shoppingListId
         */
        String title = "Obi Grocery - Suggest Items " + shoppingListId;
        this.setTitle(title);
    }

    protected void setInstructions() {
        TextView instructionText = (TextView) findViewById(R.id.instructionText);
        instructionText.setText("The app has some items to suggest."
                + " Select the items you want to import to the list.");
    }

    protected void populateList() {
        adapter = new ItemListAdapterSelect(this, shoppingListId);
        /*
         * TODO use database to populate
         * Use shoppingListId to retrieve
         * Want to have unique/distinct items here from all history, or part of it
         * 
         * Unlike the choose option, this java file should process what sort of
         * items to suggest
         */
        adapter.add(new ItemPOJO("Bread 1", "oz", shoppingListId, "Baked Goods"));
        adapter.add(new ItemPOJO("Bread 2", "oz", shoppingListId, "Baked Goods"));
        adapter.add(new ItemPOJO("Bread 3", "oz", 1, "Baked Goods"));
        adapter.add(new ItemPOJO("Meat 1", "oz", 1, "Meats"));
        adapter.add(new ItemPOJO("Meat 2", "oz", 1, "Meats"));
        adapter.add(new ItemPOJO("Dairy 1", "oz", 1, "Dairy"));
        adapter.add(new ItemPOJO("Dairy 2", "oz", 1, "Dairy"));
        adapter.add(new ItemPOJO("Dairy 3", "oz", 1, "Dairy"));
        adapter.add(new ItemPOJO("Dairy 4", "oz", 1, "Dairy"));

        itemsView = (ListView) findViewById(R.id.itemView);
        itemsView.setAdapter(adapter);
    }

}
