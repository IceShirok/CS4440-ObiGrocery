package com.example.obigrocery.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.activities.R;

public class ItemListAdapterEdit extends ItemListAdapterGen {

    public ItemListAdapterEdit(Context context) {
        super(context);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        Button deleteBtn = (Button) view.findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something
                ItemPOJO item = display.remove(position);
                list.remove(item);
                removeFromDatabase(item); // TODO may not need to override
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
