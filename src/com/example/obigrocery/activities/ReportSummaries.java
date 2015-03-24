package com.example.obigrocery.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ReportSummaries extends ActionBarActivity {
    
    private int position;
    private final static int NUM_FRAGMENTS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_summaries);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, this.getFragment(position)).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.report_summaries, menu);
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
     * Fragment factory
     */
    public ReportFragment getFragment(int position) {
        String title = null;
        ReportFragment frag = null;
        switch(position){
        case 0:
            title = "Obi Grocery - Pick a Date";
            this.setTitle(title);
            frag = new DateFragment();
            break;
        case 1:
            title = "Obi Grocery - Pick a Category";
            this.setTitle(title);
            frag = new CategoryFragment();
            break;
        default:
            title = "Obi Grocery - Pick a Date";
            this.setTitle(title);
            frag = new DateFragment();
            break;
        }
        return frag;
    }
    
    public void prevSection(View view) {
        if(position-1 >= 0) {
            position--;
            transition(position);
        } else {
            // do nothing
        }
    }
    
    public void nextSection(View view) {
        if(position+1 < NUM_FRAGMENTS) {
            position++;
            transition(position);
        } else {
            this.generateReport();
        }
    }
    
    protected void generateReport() {
        // TODO get info
    }
    
    protected void transition(int position) {
        ReportFragment newFragment = this.getFragment(position);
        ReportFragment currentFragment = ((ReportFragment)getSupportFragmentManager().findFragmentById(R.id.container));
        if(currentFragment.allowTransition()) {
            Bundle args = new Bundle();
            //args.putInt(CategoryFragment.ARG_POSITION, 1);
            newFragment.setArguments(args);
    
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Error")
            .setMessage(currentFragment.getErrorMessage())
            .setNeutralButton("OK", null)
            .show();
        }
    }
    
    public void onBackPressed() {
        finish();
    }
    
    public void returnToMenu(View view) {
        finish();
    }
}
