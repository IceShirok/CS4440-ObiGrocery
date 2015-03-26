package com.example.obigrocery.activities.newgrocery;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.obigrocery.activities.R;
import com.example.obigrocery.activities.global.ListShoppingGen;

public class NewGroceryAll extends ListShoppingGen {
    
    private ListView shoppingListView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void setInstructions() {
        TextView instructionText = (TextView) findViewById(R.id.instructionText);
        instructionText.setText("Instructions: select a list to import all items.");
    }

    @Override
    protected void setupListView() {
        shoppingListView = (ListView) findViewById(R.id.shoppingListView);
        shoppingListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                adapter.getItem(position);
                Intent i = new Intent(getApplicationContext(), NewGroceryOne.class);
                i.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                i.putExtra("SHOPPING_LIST_ID", position);
                startActivity(i);
                setResult(RESULT_OK, getIntent());
                System.out.println("actual intent");
                finish();
            }
        });
    }
}
