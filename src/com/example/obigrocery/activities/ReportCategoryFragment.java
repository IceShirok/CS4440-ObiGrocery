package com.example.obigrocery.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReportCategoryFragment extends ReportFragmentGen {

    public ReportCategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_category, container, false);
        return rootView;
    }

    @Override
    public boolean allowTransition() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public String getErrorMessage() {
        return "Blah";
    }
}
