package com.example.obigrocery.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.obigrocery.activities.R;

public class ItemListAdapterCheck extends ItemListAdapterGen {

    public ItemListAdapterCheck(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        CheckBox purchasedCheckbox = (CheckBox) view.findViewById(R.id.check);
        purchasedCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO need to figure out how to select which item is selected
            }
        });

        return view;
    }
}
