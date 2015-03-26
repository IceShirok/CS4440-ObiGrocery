package com.example.obigrocery.activities.check;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.obigrocery.activities.R;
import com.example.obigrocery.activities.global.ListShoppingGen;

public class CheckShoppingAll extends ListShoppingGen {
    
    @Override
    protected void setInstructions() {
        TextView instructionText = (TextView) findViewById(R.id.instructionText);
        instructionText.setText("Instructions: select a list to view.");
    }

    @Override
    protected void setupListView() {
        shoppingListView = (ListView) findViewById(R.id.shoppingListView);
        shoppingListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                adapter.getItem(position);
                Intent i = new Intent(getApplicationContext(), CheckPurchasedItems.class);
                i.putExtra("SHOPPING_LIST_ID", position);
                startActivity(i);
            }
        });
    }
}
