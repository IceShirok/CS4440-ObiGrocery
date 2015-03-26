package com.example.obigrocery.activities.report;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.obigrocery.activities.R;

public class ReportDateFragment extends ReportFragmentGen {

    public ReportDateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_dates, container, false);
        return rootView;
    }

    /**
     * Checks whether the input is correct (start date is before end date)
     */
    @Override
    public boolean allowTransition() {
        DatePicker startDate = (DatePicker) getView().findViewById(R.id.startDatepicker);
        DatePicker endDate = (DatePicker) getView().findViewById(R.id.endDatepicker);
        
        //LocalDate date = LocalDate.of(2000, Month.NOVEMBER, 20);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(startDate.getYear(), startDate.getDayOfMonth(), startDate.getMonth() + 1);
        Date sDate = calendar.getTime();
        calendar.set(endDate.getYear(), endDate.getDayOfMonth(), endDate.getMonth() + 1);
        Date eDate = calendar.getTime();
        
        // TODO pass start and end dates to next page
        
        return sDate.compareTo(eDate) < 0;
    }

    @Override
    public String getErrorMessage() {
        return "Not a valid date range!";
    }
}