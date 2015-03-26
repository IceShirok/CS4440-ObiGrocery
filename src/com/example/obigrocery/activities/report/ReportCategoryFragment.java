package com.example.obigrocery.activities.report;

import com.example.obigrocery.activities.R;
import com.example.obigrocery.activities.R.id;
import com.example.obigrocery.activities.R.layout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

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
        Spinner categorySpinner = (Spinner) getView().findViewById(R.id.categorySpinner);
        String category = categorySpinner.getSelectedItem().toString();
        System.out.println(category);
        // TODO pass category to next page
        return true;
    }

    @Override
    public String getErrorMessage() {
        return "Blah";
    }
}
