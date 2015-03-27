package com.example.obigrocery.activities;

import android.content.Intent;
import android.view.View;

public class ListShoppingOne extends ListOneListGen {
    
    // technically nothing else needed
    public void editList(View view) {
        Intent i = new Intent(getApplicationContext(), EditGroceryList.class);
        i.putExtra("SHOPPING_LIST_ID", shoppingListId);
        startActivity(i);
    }
}
