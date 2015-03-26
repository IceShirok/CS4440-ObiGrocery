package com.example.obigrocery.activities.report;

import com.example.obigrocery.activities.R;
import com.example.obigrocery.activities.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class ReportFragmentGen extends Fragment {

    public ReportFragmentGen() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_category, container, false);
        return rootView;
    }
    
    public abstract boolean allowTransition();
    
    public abstract String getErrorMessage();
}
