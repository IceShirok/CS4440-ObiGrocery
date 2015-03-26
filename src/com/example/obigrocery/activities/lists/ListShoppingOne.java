package com.example.obigrocery.activities.lists;

import android.content.Intent;
import android.view.View;

import com.example.obigrocery.activities.global.ListOneListGen;

public class ListShoppingOne extends ListOneListGen {
    
    // technically nothing else needed
    public void editList(View view) {
        Intent i = new Intent(getApplicationContext(), EditGroceryList.class);
        i.putExtra("SHOPPING_LIST_ID", shoppingListId);
        startActivity(i);
    }
}
