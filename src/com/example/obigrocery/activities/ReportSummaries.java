package com.example.obigrocery.activities;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class ReportSummaries extends ActionBarActivity {
    
    private int position;

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
    public Fragment getFragment(int position) {
        String title = null;
        Fragment frag = null;
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DateFragment extends Fragment {

        public DateFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_report_dates, container, false);
            return rootView;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class CategoryFragment extends Fragment {

        public CategoryFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_report_category, container, false);
            return rootView;
        }
    }
    
    public void prevSection(View view) {
        if(position-1 >= 0) {
            position--;
        }
        transition(position);
        System.out.println(position);
    }
    
    public void nextSection(View view) {
        if(position+1 < 2) {
            position++;
        }
        transition(position);
        System.out.println(position);
    }
    
    protected void transition(int position) {
        Fragment newFragment = this.getFragment(position);
        Bundle args = new Bundle();
        //args.putInt(CategoryFragment.ARG_POSITION, 1);
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    
    public void onBackPressed() {
        finish();
    }
    
    public void returnToMenu(View view) {
        finish();
    }
}
