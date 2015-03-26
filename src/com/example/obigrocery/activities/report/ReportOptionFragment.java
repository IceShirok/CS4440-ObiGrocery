package com.example.obigrocery.activities.report;

import com.example.obigrocery.activities.R;
import com.example.obigrocery.activities.R.layout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReportOptionFragment extends ReportFragmentGen {

    public ReportOptionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_option, container, false);
        return rootView;
    }

    /**
     * Checks whether the input is correct (start date is before end date)
     */
    @Override
    public boolean allowTransition() {
        // TODO implement
        return true;
    }

    @Override
    public String getErrorMessage() {
        return "Not a valid date range!";
    }
}
