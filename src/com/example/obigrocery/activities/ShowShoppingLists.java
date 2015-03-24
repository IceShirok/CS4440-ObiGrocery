package com.example.obigrocery.activities;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ShowShoppingLists extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shopping_lists);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int shoppingListId = extras.getInt("SHOPPING_LIST_ID");
            // TODO use db to fill stuff in
            TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
            LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
            mLocalActivityManager.dispatchCreate(savedInstanceState);
            // TODO fix something with savedInstanceState - does not like it when press Back key
            tabHost.setup(mLocalActivityManager);
            for(int i=0; i<5; i++) {
                TabSpec tabThing = tabHost.newTabSpec(shoppingListId+"");
                tabThing.setIndicator("Bread"+i);
                tabThing.setContent(new Intent(this, ShoppingCategory.class).putExtra("CATEGORY", "Bread"+i));
                tabHost.addTab(tabThing);
            }
        } else {
            // TODO put a warning about this if list not found for some reason
        }
    }
    
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_shopping_lists, menu);
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
}
