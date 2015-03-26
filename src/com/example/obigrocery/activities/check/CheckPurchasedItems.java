package com.example.obigrocery.activities.check;

import java.math.BigDecimal;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.activities.R;
import com.example.obigrocery.adapters.ItemListAdapterShow;

public class CheckPurchasedItems extends ActionBarActivity {
    
    private Button finishCheckButton;
    private ItemListAdapterShow adapter;
    private ListView itemsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_purchased_items);

        finishCheckButton = (Button) findViewById(R.id.finishCheckButton);
        finishCheckButton.setEnabled(false);
        
        itemsView = (ListView) findViewById(R.id.itemView);

        adapter = new ItemListAdapterShow(this);
        adapter.add(new ItemPOJO("Bread", new BigDecimal(1), 1, "Baked Goods"));
        adapter.add(new ItemPOJO("Bread", new BigDecimal(1), 1, "Baked Goods"));
        adapter.add(new ItemPOJO("Bread", new BigDecimal(1), 1, "Baked Goods"));
        adapter.add(new ItemPOJO("Meat", new BigDecimal(1), 1, "Meats"));
        adapter.add(new ItemPOJO("Meats", new BigDecimal(1), 1, "BMeats"));
        adapter.add(new ItemPOJO("Dairy", new BigDecimal(1), 1, "Dairy"));

        itemsView.setAdapter(adapter);
        itemsView.setClickable(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.check_purchased_items, menu);
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
    
    public void finishGroceryList(View view) {
        // TODO save results to database, go to impulse checker
        
    }
    
    public void returnToMenu(View view) {
        // TODO probably put warning
        finish();
    }
}
