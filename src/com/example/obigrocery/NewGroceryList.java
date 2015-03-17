package com.example.obigrocery;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class NewGroceryList extends ActionBarActivity {
    
    private EditText itemNameTextbox;
    private EditText quantityTextbox;
    private EditText priceTextbox;
    private ListView itemsView;

    private ArrayList<String> listItems = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_grocery_list);
        
        itemNameTextbox = (EditText)findViewById(R.id.itemNameTextbox);
        quantityTextbox = (EditText)findViewById(R.id.quantityTextbox);
        priceTextbox = (EditText)findViewById(R.id.priceTextbox);

        itemsView = (ListView)findViewById(R.id.itemView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        itemsView.setAdapter(adapter);
        itemsView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                System.out.println("printing item " + position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_grocery_list, menu);
        listItems = new ArrayList<String>();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /*
     * Dynamic addition of list items
     */
    public void addItems(View view) {
        String itemName = null;
        int quantity = 0;
        double price = 0;
        try {
            quantity = Integer.parseInt(quantityTextbox.getText().toString());
            price = Double.parseDouble(priceTextbox.getText().toString());
            itemName = itemNameTextbox.getText().toString();
        } catch(NumberFormatException e) {
            System.out.println("Incorrect number format!");
        }
        if(itemName != null) {
            listItems.add(itemName + "," + price + "," + quantity);
        }
        System.out.println(itemName + "," + price + "," + quantity);
        adapter.clear();
        for(String s : listItems) {
            adapter.add(s);
        }
        itemsView.setAdapter(adapter);
        System.out.println(itemsView.getAdapter().getCount());
    }
    
    /*
     * Add a grocery item to the list
     */
    public void addGroceryItem(View view) {
        addItems(view);
    }
    
    /*
     * Done with all grocery list items, go to confirmation page
     */
    public void finishGroceryList(View view) {
        
    }

    /*
     * Return to menu
     */
    public void returnToMenu(View view) {
        finish();
    }
}
