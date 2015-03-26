package com.example.obigrocery.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ItemListAdapterShow extends ItemListAdapterGen {

    public ItemListAdapterShow(Context context) {
        super(context);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        /*Button deleteBtn = (Button) view.findViewById(R.id.delete_btn);
        deleteBtn.setVisibility(View.GONE);*/

        return view;
    }
}
