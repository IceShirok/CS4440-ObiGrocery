package com.example.obigrocery.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.obigrocery.R;
import com.example.obigrocery.R.id;
import com.example.obigrocery.R.layout;
import com.example.obigrocery.R.menu;
import com.example.obigrocery.adapters.ItemListAdapter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class NewGroceryList extends ActionBarActivity {
    
    private EditText listTextbox;
    private EditText itemNameTextbox;
    private EditText quantityTextbox;
    private EditText priceTextbox;
    private ListView itemsView;

    private ItemListAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_grocery_list);
        
        listTextbox = (EditText)findViewById(R.id.listTextbox);
        itemNameTextbox = (EditText)findViewById(R.id.itemNameTextbox);
        quantityTextbox = (EditText)findViewById(R.id.quantityTextbox);
        priceTextbox = (EditText)findViewById(R.id.priceTextbox);

        itemsView = (ListView)findViewById(R.id.itemView);

        adapter = new ItemListAdapter<>(new ArrayList<String>(), this);
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
     * Add a grocery item to the list
     */
    public void addGroceryItem(View view) {
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
            addItemsToDisplay(itemName, quantity, price);
        }
    }
    
    /*
     * Dynamic addition of list items
     */
    private void addItemsToDisplay(String itemName, int quantity, double price) {
        //TODO change so arraylist hold POJO
        adapter.add(itemName + "," + price + "," + quantity);
        itemsView.setAdapter(adapter);
    }
    
    /*
     * Done with all grocery list items, add everything to db, go to confirmation page
     */
    public void finishGroceryList(View view) {
        String listName = listTextbox.getText().toString();
        if(listName != null) {
            List<String> list = adapter.getList();
            for(String s : list) {
                System.out.println(s);
            }
            //TODO put everything to database
            //TODO goto list of shopping lists, or main menu with confirmation
        } else {
            //TODO put some error message
        }
    }

    /*
     * Return to menu, cancelling the list
     */
    public void returnToMenu(View view) {
        if(adapter.getList().size() > 0) {
            //TODO have a confirmation of some sort
        }
        finish();
    }
}
