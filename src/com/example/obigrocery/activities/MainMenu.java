package com.example.obigrocery.activities;

import com.example.obigrocery.activities.R;
import com.example.obigrocery.activities.R.id;
import com.example.obigrocery.activities.R.layout;
import com.example.obigrocery.activities.R.menu;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainMenu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        
        Button genReportButton = (Button) findViewById(R.id.genReportButton);
        genReportButton.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
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
    
    public void gotoNew(View view) {
        Intent i = new Intent(getApplicationContext(), NewGroceryList.class);
        startActivity(i);
    }
    
    public void gotoLoad(View view) {
        Intent i = new Intent(getApplicationContext(), ListShoppingLists.class);
        startActivity(i);
    }
    
    public void gotoPurchased(View view) {
        Intent i = new Intent(getApplicationContext(), ListShoppingListsBuy.class);
        startActivity(i);
    }
    
    public void gotoReports(View view) {
        Intent i = new Intent(getApplicationContext(), ReportSummaryActivity.class);
        startActivity(i);
    }
    
    public void gotoHelp(View view) {
        Intent i = new Intent(getApplicationContext(), HelpScreen.class);
        startActivity(i);
    }

    public void exitApp(View view) {
        System.exit(0);
    }
}
